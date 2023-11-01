package com.andersen.orange.user.service;

import com.andersen.orange.mark.model.IndividualMark;
import com.andersen.orange.mark.model.Mark;
import com.andersen.orange.mark.repository.IndividualMarkRepository;
import com.andersen.orange.user.dto.UserCreateDto;
import com.andersen.orange.user.dto.UserDto;
import com.andersen.orange.user.dto.UserMarksDto;
import com.andersen.orange.user.model.User;
import com.andersen.orange.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final IndividualMarkRepository individualMarkRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, IndividualMarkRepository individualMarkRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.individualMarkRepository = individualMarkRepository;
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

    @Override
    public UserMarksDto getAllMarks(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id '%s' not found", userId)));
        Map<String, List<Double>> marksByDate = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Mark mark : user.getMarks()) {
            String formattedDate = dateFormat.format(mark.getPair().getDate());
            marksByDate.computeIfAbsent(formattedDate, k -> new ArrayList<>()).add(mark.getMark());
        }
        List<IndividualMark> individualMarks = individualMarkRepository.findByUser(user);
        for (IndividualMark individualMark : individualMarks) {
            String formattedDate = dateFormat.format(individualMark.getMarkDate());
            marksByDate.computeIfAbsent(formattedDate, k -> new ArrayList<>()).add(individualMark.getIndividualMark());
        }
        return UserMarksDto.builder()
                .id(userId)
                .name(user.getName())
                .lastname(user.getLastname())
                .team(user.getTeam().getName())
                .marksByDate(marksByDate)
                .build();
    }
}
