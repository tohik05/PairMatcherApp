package com.andersen.orange.user.service;

import com.andersen.orange.team.repository.TeamRepository;
import com.andersen.orange.user.dto.UserCreateDto;
import com.andersen.orange.user.dto.UserRequestDto;
import com.andersen.orange.user.dto.UserResponseDto;
import com.andersen.orange.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class UserMapper {
    private final TeamRepository teamRepository;

    @Autowired
    public UserMapper(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public User mapToEntity(UserRequestDto user) {
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .lastname(user.getLastname())
                .team(teamRepository.findByNameIgnoreCase(user.getTeam())
                        .orElseThrow(() -> new EntityNotFoundException("Team with that name doesn't exist")))
                .build();
    }

    public User mapToEntity(UserCreateDto user) {
        return User.builder()
                .name(user.getName())
                .lastname(user.getLastname())
                .team(teamRepository.findByNameIgnoreCase(user.getTeam())
                        .orElseThrow(() -> new EntityNotFoundException("Team with that name doesn't exist")))
                .build();
    }

    public UserResponseDto mapToResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .lastname(user.getLastname())
                .team(user.getTeam().getName())
                .build();
    }
}
