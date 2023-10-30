package com.andersen.orange.pair.service;

import com.andersen.orange.pair.dto.PairRequestDto;
import com.andersen.orange.pair.dto.PairResponseDto;
import com.andersen.orange.pair.model.Pair;
import com.andersen.orange.user.model.User;
import com.andersen.orange.user.repository.UserRepository;
import com.andersen.orange.user.service.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

@Component
public class PairMapper {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Autowired
    public PairMapper(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public PairResponseDto mapToDto(Pair pair) {
        List<User> users = pair.getUsers();
        return PairResponseDto.builder()
                .mainUser(userMapper.mapToDto(users.get(0)))
                .opponentUser(userMapper.mapToDto(users.get(1)))
                .build();
    }

    public Pair mapToEntity(PairRequestDto pairDto) {
        User mainUser = userRepository.findById(pairDto.getMainUser().getId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id '%s' not found", pairDto.getMainUser().getId())));
        User opponentUser = userRepository.findById(pairDto.getOpponentUser().getId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id '%s' not found", pairDto.getOpponentUser().getId())));
        return Pair.builder()
                .date(new Date())
                .users(List.of(mainUser, opponentUser))
                .build();
    }
}
