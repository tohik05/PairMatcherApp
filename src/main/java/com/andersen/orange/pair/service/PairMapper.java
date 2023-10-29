package com.andersen.orange.pair.service;

import com.andersen.orange.pair.dto.PairDto;
import com.andersen.orange.pair.model.Pair;
import com.andersen.orange.user.model.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class PairMapper {
    public PairDto mapToPairDto(Pair pair) {
        List<User> users = pair.getUsers();
        return PairDto.builder()
                .mainUser(users.get(0))
                .opponentUser(users.get(1))
                .build();
    }

    public Pair mapToEntity(PairDto pairDto) {
        User mainUser = pairDto.getMainUser();
        User opponentUser = pairDto.getOpponentUser();
        return Pair.builder()
                .date(new Date())
                .users(List.of(mainUser, opponentUser))
                .build();
    }
}
