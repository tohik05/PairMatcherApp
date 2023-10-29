package com.andersen.orange.user.service;

import com.andersen.orange.user.dto.UserCreateDto;
import com.andersen.orange.user.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAll();

    UserDto getById(Long id);

    UserDto create(UserCreateDto user);

    UserDto update(UserDto user);

    void softDeleteById(Long id);
}
