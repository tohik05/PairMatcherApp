package com.andersen.orange.pair.controller;

import com.andersen.orange.pair.dto.PairDto;
import com.andersen.orange.pair.service.PairService;
import com.andersen.orange.user.dto.UserRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orange/pairs")
public class PairController {
//    private final PairMatcherAlgorithm pairMatchers;
//
//    public PairController(PairMatcherAlgorithm pairMatchers) {
//        this.pairMatchers = pairMatchers;
//    }
    private final PairService pairService;

    @Autowired
    public PairController(PairService pairService) {
        this.pairService = pairService;
    }

/*    @GetMapping
    public List<PairDto> createPairs(@RequestBody List<UserRequestDto> users) {
       return pairMatchers.pairMatcher(users);
    }*/

    @GetMapping
    public PairDto createPairs(@RequestBody List<UserRequestDto> users) {
        return pairService.createPair(users);
    }

    @PostMapping
    public void saveMeetingResultInDB(@RequestBody PairDto pairDto) {
        pairService.savePairInDB(pairDto);
    }
}
