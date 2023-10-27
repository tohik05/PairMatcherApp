package com.andersen.orange.user.service;

import com.andersen.orange.user.dto.UserCreateDto;
import com.andersen.orange.user.dto.UserRequestDto;
import com.andersen.orange.user.dto.UserResponseDto;
import com.andersen.orange.user.model.Teams;
import com.andersen.orange.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToEntity(UserRequestDto user){
        return User.builder()
                .name(user.getName())
                .lastname(user.getLastname())
                .build();
    }

    public User mapToEntity(UserCreateDto user){
        return User.builder()
                .name(user.getName())
                .lastname(user.getLastname())
                .team(Teams.valueOf(user.getTeam()))
                .build();
    }

    public UserResponseDto mapToResponseDto(User user) {
        return UserResponseDto.builder()
                .name(user.getName())
                .lastname(user.getLastname())
                .team(String.valueOf(user.getTeam()))
                .build();
    }
}
