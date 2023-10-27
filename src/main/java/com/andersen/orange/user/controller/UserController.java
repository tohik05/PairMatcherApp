package com.andersen.orange.user.controller;

import com.andersen.orange.user.dto.UserRequestDto;
import com.andersen.orange.user.dto.UserResponseDto;
import com.andersen.orange.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<UserResponseDto>> getAll(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDto> read(@PathVariable(name = "id") long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getById(id));
    }

    @PostMapping(value = {"", "/"})
    public ResponseEntity<?> create(@RequestBody UserRequestDto user){
        userService.create(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User successfully created");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> softDelete(@PathVariable(name = "id") long id){
        userService.softDeleteById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("User successfully deleted");
    }

    @DeleteMapping(value = "/hard/{id}")
    public ResponseEntity<?> hardDelete(@PathVariable(name = "id") long id){
        userService.hardDeleteById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("User successfully deleted");
    }
}
