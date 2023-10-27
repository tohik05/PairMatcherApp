package com.andersen.orange.user.service;

import com.andersen.orange.user.dto.UserRequestDto;
import com.andersen.orange.user.dto.UserResponseDto;
import com.andersen.orange.user.model.User;
import com.andersen.orange.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    @Override
    public List<UserResponseDto> getAll(){
        List<UserResponseDto> users = userRepository.findAll().stream()
                .map(userMapper::mapToResponseDto)
                .toList();
        return users.isEmpty() ? new ArrayList<>() : users;
    }
    @Override
    public UserResponseDto getById(Long id){
        return userMapper.mapToResponseDto(userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id '%s' not found", id))));
    }
    @Override
    public UserResponseDto create(UserRequestDto user){
        User newUser = userRepository.save(userMapper.mapToEntity(user));
        return userMapper.mapToResponseDto(newUser);
    }

    @Override
    public void softDeleteById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            userRepository.softDelete(id);
        } else {
            throw new EntityNotFoundException(String.format("User with id '%s' not found", id));
        }
    }

    @Override
    public void hardDeleteById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(String.format("User with id '%s' not found", id));
        }
    }
}
