package com.andersen.orange.user.service;

import com.andersen.orange.user.dto.UserCreateDto;
import com.andersen.orange.user.dto.UserDto;
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
    public List<UserDto> getAll() {
        List<UserDto> users = userRepository.findByIsDeletedFalse().stream()
                .map(userMapper::mapToDto)
                .toList();
        return users.isEmpty() ? new ArrayList<>() : users;
    }

    @Override
    public UserDto getById(Long id) {
        return userMapper.mapToDto(userRepository.findByIdAndIsDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id '%s' not found", id))));
    }

    @Override
    public UserDto create(UserCreateDto user) {
        User newUser = userRepository.save(userMapper.mapToEntity(user));
        return userMapper.mapToDto(newUser);
    }

    @Override
    public UserDto update(UserDto user) {
        User requestUser = userMapper.mapToEntity(user);
        User userForUpdate = userRepository.findById(requestUser.getId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id '%s' not found", requestUser.getId())));
        userForUpdate.setTeam(requestUser.getTeam());
        return userMapper.mapToDto(userRepository.save(userForUpdate));
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
