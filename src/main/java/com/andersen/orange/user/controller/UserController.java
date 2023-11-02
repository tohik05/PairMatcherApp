package com.andersen.orange.user.controller;

import com.andersen.orange.mark.dto.IndividualMarkDto;
import com.andersen.orange.mark.service.IndividualMarkService;
import com.andersen.orange.user.dto.UserCreateDto;
import com.andersen.orange.user.dto.UserDto;
import com.andersen.orange.user.dto.UserMarksDto;
import com.andersen.orange.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS},
        allowedHeaders = "*",
        maxAge = 3600)
@RestController
@RequestMapping("/orange/users")
public class UserController {

    private final UserService userService;
    private final IndividualMarkService individualMarkService;

    @Autowired
    public UserController(UserService userService, IndividualMarkService individualMarkService) {
        this.userService = userService;
        this.individualMarkService = individualMarkService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto read(@PathVariable(name = "id") long id) {
        return userService.getById(id);
    }

    @GetMapping(value = "/{id}/allMarks")
    @ResponseStatus(HttpStatus.OK)
    public UserMarksDto getMarks(@PathVariable(name = "id") long id) {
        return userService.getAllMarks(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserCreateDto user) {
        return userService.create(user);
    }

    @PostMapping("/{id}/individualMark")
    @ResponseStatus(HttpStatus.CREATED)
    public IndividualMarkDto addIndividualMark(@PathVariable(name = "id") Long id,
                                               @RequestBody IndividualMarkDto individualMarkDto) {
        return individualMarkService.addIndividualMark(id, individualMarkDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateTeam(@RequestBody @PathVariable(name = "id") UserDto user) {
        return userService.update(user);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDelete(@PathVariable(name = "id") long id) {
        userService.softDeleteById(id);
    }
}
