package com.andersen.orange.pair.service;

import com.andersen.orange.marks.service.MarksService;
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

import java.util.List;

@Service
public class PairServiceImpl implements PairService {
    private final PairRepository pairRepository;
    private final UserMapper userMapper;
    private final PairMapper pairMapper;
    private final Algorithm algorithm;
    private final UserRepository userRepository;
    private final MarksService marksService;

    @Autowired
    public PairServiceImpl(PairRepository repository, UserMapper userMapper, PairMapper pairMapper, Algorithm algorithm,
                           UserRepository userRepository, MarksService marksService) {
        this.pairRepository = repository;
        this.userMapper = userMapper;
        this.pairMapper = pairMapper;
        this.algorithm = algorithm;
        this.userRepository = userRepository;
        this.marksService = marksService;
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
        Pair pair = pairMapper.mapToEntity(pairDto);
        pairRepository.save(pair);

        User main = pair.getUsers().get(0);
        List<Pair> mainPairs = main.getPairs();
        mainPairs.add(pair);
        main.setPairs(mainPairs);
        userRepository.save(main);
        marksService.saveMark(main, pair, pairDto.getMainUser().getMark());

        User opponent = pair.getUsers().get(1);
        List<Pair> opponentPairs = opponent.getPairs();
        opponentPairs.add(pair);
        opponent.setPairs(opponentPairs);
        userRepository.save(opponent);
        marksService.saveMark(opponent,pair, pairDto.getOpponentUser().getMark());
    }

}
