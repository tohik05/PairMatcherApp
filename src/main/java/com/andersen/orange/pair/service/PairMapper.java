package com.andersen.orange.pair.service;

import com.andersen.orange.pair.dto.PairDto;
import com.andersen.orange.pair.model.Pair;
import com.andersen.orange.user.model.User;
import com.andersen.orange.user.service.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class PairMapper {
    private final UserMapper userMapper;

    @Autowired
    public PairMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public PairDto mapToDto(Pair pair) {
        List<User> users = pair.getUsers();
        return PairDto.builder()
                .mainUser(userMapper.mapToDto(users.get(0)))
                .opponentUser(userMapper.mapToDto(users.get(1)))
                .build();
    }

    public Pair mapToEntity(PairDto pairDto) {
        User mainUser = userMapper.mapToEntity(pairDto.getMainUser());
        User opponentUser = userMapper.mapToEntity(pairDto.getOpponentUser());
        return Pair.builder()
                .date(new Date())
                .users(List.of(mainUser, opponentUser))
                .build();
    }
}
