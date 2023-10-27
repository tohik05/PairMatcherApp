package com.andersen.orange.user.service;

import com.andersen.orange.team.repository.TeamRepository;
import com.andersen.orange.user.dto.UserCreateDto;
import com.andersen.orange.user.dto.UserRequestDto;
import com.andersen.orange.user.dto.UserResponseDto;
import com.andersen.orange.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final TeamRepository teamRepository;

    @Autowired
    public UserMapper(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

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
                .team(teamRepository.findByNameIgnoreCase(user.getTeam()).get())
                .build();
    }

    public UserResponseDto mapToResponseDto(User user) {
        return UserResponseDto.builder()
                .name(user.getName())
                .lastname(user.getLastname())
                .team(user.getTeam().getName())
                .build();
    }
}
