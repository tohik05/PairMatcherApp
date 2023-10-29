package com.andersen.orange.user.service;

import com.andersen.orange.user.dto.UserCreateDto;
import com.andersen.orange.user.dto.UserRequestDto;
import com.andersen.orange.user.dto.UserResponseDto;
import com.andersen.orange.user.model.User;
import com.andersen.orange.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserResponseDto> getAll() {
        List<UserResponseDto> users = userRepository.findByIsDeletedFalse().stream()
                .map(userMapper::mapToResponseDto)
                .toList();
        return users.isEmpty() ? new ArrayList<>() : users;
    }

    @Override
    public UserResponseDto getById(Long id) {
        return userMapper.mapToResponseDto(userRepository.findByIdAndIsDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id '%s' not found", id))));
    }

    @Override
    public UserResponseDto create(UserCreateDto user) {
        User newUser = userRepository.save(userMapper.mapToEntity(user));
        return userMapper.mapToResponseDto(newUser);
    }

    @Override
    public UserResponseDto update(UserRequestDto user) {
        User requestUser = userMapper.mapToEntity(user);
        User userForUpdate = userRepository.findById(requestUser.getId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id '%s' not found", requestUser.getId())));
        userForUpdate.setTeam(requestUser.getTeam());
        return userMapper.mapToResponseDto(userRepository.save(userForUpdate));
    }

    @Transactional
    @Override
    public void softDeleteById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.softDelete(id);
        } else {
            throw new EntityNotFoundException(String.format("User with id '%s' not found", id));
        }
    }
}
