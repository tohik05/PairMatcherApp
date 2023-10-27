package com.andersen.orange.user.controller;

import com.andersen.orange.user.dto.UserCreateDto;
import com.andersen.orange.user.dto.UserResponseDto;
import com.andersen.orange.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orange/users")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"", "/"})
    public List<UserResponseDto> getAll(){
        return userService.getAll();
    }

    @GetMapping(value = "/{id}")
    public UserResponseDto read(@PathVariable(name = "id") long id){
        return userService.getById(id);
    }

    @PostMapping(value = {"", "/"})
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto create(@RequestBody UserCreateDto user){
        return userService.create(user);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void softDelete(@PathVariable(name = "id") long id){
        userService.softDeleteById(id);
    }
}
