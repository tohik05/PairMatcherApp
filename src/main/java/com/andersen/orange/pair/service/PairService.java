package com.andersen.orange.pair.service;

import com.andersen.orange.pair.dto.PairDto;
import com.andersen.orange.pair.model.Pair;
import com.andersen.orange.pair.repository.PairRepository;
import com.andersen.orange.user.dto.UserRequestDto;
import com.andersen.orange.user.model.User;
import com.andersen.orange.user.repository.UserRepository;
import com.andersen.orange.user.service.UserMapper;
import com.andersen.orange.util.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PairService {
    private final PairRepository pairRepository;
    private final UserMapper userMapper;
    private final PairMapper pairMapper;
    private final Algorithm algorithm;
    private final UserRepository userRepository;

    @Autowired
    public PairService(PairRepository repository, UserMapper userMapper, PairMapper pairMapper, Algorithm algorithm,
                       UserRepository userRepository) {
        this.pairRepository = repository;
        this.userMapper = userMapper;
        this.pairMapper = pairMapper;
        this.algorithm = algorithm;
        this.userRepository = userRepository;
    }

//    public void createPair(Pair pair) {
//        pairRepository.save(pair);
//    }

    public PairDto createPair(List<UserRequestDto> usersDto) {
        List<User> users = usersDto.stream().map(userMapper::mapToEntity).toList();
        for (User user : users) {
            user.setPairs(getAllUserPairs(user));
        }
        return pairMapper.mapToPairDto(algorithm.findPair(users));
    }

    public void savePairInDB(PairDto pairDto) {
        pairRepository.save(pairMapper.mapToEntity(pairDto));
        pairRepository.save(pairMapper.mapToEntityOppositeUser(pairDto));
//        saveOpponentPair(pairMapper.mapToEntityOppositeUser(pairDto));
//        savePair(pairMapper.mapToEntity(pairDto));
    }


    public List<Pair> getAllUserPairs(User user) {
        return pairRepository.findPairsByUserId(user.getId());
    }

//    private void saveOpponentPair(Pair pair){
//        Optional<User> oppositeUser = userRepository.findById(pair.getOpponent().getId());
//        if (oppositeUser.isPresent()){
//            User user = oppositeUser.get();
//            List<Pair> pairs = user.getPairs();
//            pairs.add(pair);
//            userRepository.save(user);
//        }
//    }

    private void savePair(Pair pair) {
        User opponent = pair.getOpponent();

        List<Pair> opPairs = opponent.getPairs();
//        Pair opPair = Pair.builder()
//                .date(new Date())
//                .user(pair.getOpponent())
//                .opponent(pair.getUser())
//                .build();


        System.out.println("================OPPONENT PAIR==============");
        for (Pair pairr : opPairs) {
            System.out.println("MY USER IS " + pairr.getUser());
            System.out.println("MY OPP IS " + pairr.getOpponent());
        }

        Pair saved = pairRepository.save(pair);
        opPairs.add(saved);
        opponent.setPairs(opPairs);
        userRepository.save(opponent);
    }
}
