package com.andersen.orange.user.service;

import com.andersen.orange.user.dto.UserCreateDto;
import com.andersen.orange.user.dto.UserRequestDto;
import com.andersen.orange.user.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAll();

    UserResponseDto getById(Long id);

    UserResponseDto create(UserCreateDto user);

    UserResponseDto update(UserRequestDto user);

    void softDeleteById(Long id);
}
