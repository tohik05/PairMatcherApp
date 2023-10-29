package com.andersen.orange.pair.service;

import com.andersen.orange.pair.dto.PairDto;
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

    @Autowired
    public PairServiceImpl(PairRepository repository, UserMapper userMapper, PairMapper pairMapper, Algorithm algorithm,
                           UserRepository userRepository) {
        this.pairRepository = repository;
        this.userMapper = userMapper;
        this.pairMapper = pairMapper;
        this.algorithm = algorithm;
        this.userRepository = userRepository;
    }

    @Override
    public PairDto createPair(List<UserDto> usersDto) {
        List<User> users = usersDto.stream().map(userMapper::mapToEntity).toList();
        for (User user : users) {
            user.setPairs(pairRepository.findPairsByUser(user));
        }
        return pairMapper.mapToDto(algorithm.findPair(users));
    }

    @Override
    public void savePairInDB(PairDto pairDto) {
        savePair(pairMapper.mapToEntity(pairDto));
    }

    private void savePair(Pair pair) {
        Pair saved = pairRepository.save(pair);

        User main = saved.getUsers().get(0);
        List<Pair> mainPairs = main.getPairs();
        mainPairs.add(saved);
        main.setPairs(mainPairs);
        userRepository.save(main);

        User opponent = saved.getUsers().get(1);
        List<Pair> opponentPairs = opponent.getPairs();
        opponentPairs.add(saved);
        opponent.setPairs(opponentPairs);
        userRepository.save(opponent);
    }
}
