package com.andersen.orange.pair.service;

import com.andersen.orange.pair.dto.PairDto;
import com.andersen.orange.pair.model.Pair;
import com.andersen.orange.user.model.User;
import com.andersen.orange.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Date;

@Component
public class PairMapper {

    private final UserRepository userRepository;

    @Autowired
    public PairMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PairDto mapToPairDto(Pair pair){
        User opponent = userRepository.findById(pair.getOpponent().getId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id '%s' not found", pair.getOpponent().getId())));
        return PairDto.builder()
                .mainUser(pair.getUser().getName() + " " + pair.getUser().getLastname())
                .opponentUser(opponent.getName() + " " + opponent.getLastname())
                .build();
    }

    public Pair mapToEntity(PairDto pairDto){
        String[] mainUserSplit = pairDto.getMainUser().split(" ");
        String[] opponentUserSplit = pairDto.getOpponentUser().split(" ");
        return Pair.builder()
                .date(new Date())
                .user(userRepository.findByNameAndLastname(mainUserSplit[0], mainUserSplit[1]).orElseThrow(
                        () -> new EntityNotFoundException("User with that name not found")
                ))
                .opponent(userRepository.findByNameAndLastname(opponentUserSplit[0], opponentUserSplit[1]).orElseThrow(
                        () -> new EntityNotFoundException("User with that name not found")))
                .build();
    }

    public Pair mapToEntityOppositeUser(PairDto pairDto){
        String[] mainUserSplit = pairDto.getOpponentUser().split(" ");
        String[] opponentUserSplit = pairDto.getMainUser().split(" ");
        return Pair.builder()
                .date(new Date())
                .user(userRepository.findByNameAndLastname(mainUserSplit[0], mainUserSplit[1]).orElseThrow(
                        () -> new EntityNotFoundException("User with that name not found")
                ))
                .opponent(userRepository.findByNameAndLastname(opponentUserSplit[0], opponentUserSplit[1]).orElseThrow(
                        () -> new EntityNotFoundException("User with that name not found")))
                .build();
    }
}
