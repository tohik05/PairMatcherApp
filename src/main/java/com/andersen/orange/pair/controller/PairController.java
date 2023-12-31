package com.andersen.orange.pair.controller;

import com.andersen.orange.pair.dto.PairRequestDto;
import com.andersen.orange.pair.dto.PairResponseDto;
import com.andersen.orange.pair.service.PairService;
import com.andersen.orange.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
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

    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.OK)
    public PairResponseDto getPairs(@RequestBody List<UserDto> users) {
        return pairService.createPair(users);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveMeetingResultInDB(@RequestBody PairRequestDto pairDto) {
        pairService.savePairInDB(pairDto);
    }
}
