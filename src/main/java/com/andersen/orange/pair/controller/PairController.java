package com.andersen.orange.pair.controller;

import com.andersen.orange.pair.dto.PairDto;
import com.andersen.orange.pair.service.PairService;
import com.andersen.orange.user.dto.UserRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/orange/pairs")
public class PairController {
    private final PairService pairService;

    @Autowired
    public PairController(PairService pairService) {
        this.pairService = pairService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PairDto getPairs(@RequestBody List<UserRequestDto> users) {
        return pairService.createPair(users);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveMeetingResultInDB(@RequestBody PairDto pairDto) {
        pairService.savePairInDB(pairDto);
    }
}
