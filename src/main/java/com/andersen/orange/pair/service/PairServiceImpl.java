package com.andersen.orange.pair.service;

import com.andersen.orange.exception.RepeatCouplePerformanceException;
import com.andersen.orange.mark.service.MarkService;
import com.andersen.orange.pair.dto.PairRequestDto;
import com.andersen.orange.pair.dto.PairResponseDto;
import com.andersen.orange.pair.model.Pair;
import com.andersen.orange.pair.repository.PairRepository;
import com.andersen.orange.user.dto.UserDto;
import com.andersen.orange.user.model.User;
import com.andersen.orange.user.repository.UserRepository;
import com.andersen.orange.user.service.UserMapper;
import com.andersen.orange.util.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PairServiceImpl implements PairService {
    private final PairRepository pairRepository;
    private final UserMapper userMapper;
    private final PairMapper pairMapper;
    private final Algorithm algorithm;
    private final UserRepository userRepository;
    private final MarkService markService;

    @Autowired
    public PairServiceImpl(PairRepository repository, UserMapper userMapper, PairMapper pairMapper, Algorithm algorithm,
                           UserRepository userRepository, MarkService markService) {
        this.pairRepository = repository;
        this.userMapper = userMapper;
        this.pairMapper = pairMapper;
        this.algorithm = algorithm;
        this.userRepository = userRepository;
        this.markService = markService;
    }

    @Override
    public PairResponseDto createPair(List<UserDto> usersDto) {
        List<User> users = usersDto.stream().map(userMapper::mapToEntity).toList();
        for (User user : users) {
            user.setPairs(pairRepository.findPairsByUser(user));
        }
        return pairMapper.mapToDto(algorithm.findPair(users));
    }

    @Override
    public void savePairInDB(PairRequestDto pairDto) {
        savePair(pairDto);
    }

    private void savePair(PairRequestDto pairDto) {
        if (hasUserRecordToday(pairDto)) {
            Pair pair = pairMapper.mapToEntity(pairDto);

            pairRepository.save(pair);

            User main = pair.getUsers().get(0);
            List<Pair> mainPairs = main.getPairs();
            mainPairs.add(pair);
            main.setPairs(mainPairs);
            userRepository.save(main);
            markService.saveMark(main, pair, pairDto.getMainUser().getMark());

            User opponent = pair.getUsers().get(1);
            List<Pair> opponentPairs = opponent.getPairs();
            opponentPairs.add(pair);
            opponent.setPairs(opponentPairs);
            userRepository.save(opponent);
            markService.saveMark(opponent, pair, pairDto.getOpponentUser().getMark());
        }
    }

    private boolean hasUserRecordToday(PairRequestDto pairDto) {
        Optional<User> user = userRepository.findById(pairDto.getMainUser().getId());
        if (user.isPresent()) {
            User checkUser = user.get();
            checkUser.setPairs(pairRepository.findPairsByUser(checkUser));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date());

            List<Pair> userPairsToday = checkUser.getPairs().stream()
                    .filter(pair -> pair.getDate().toString().equals(date))
                    .toList();

            if (userPairsToday.size() > 0) {
                throw new RepeatCouplePerformanceException("This couple performed today");
            }
        }
        return true;
    }
}
