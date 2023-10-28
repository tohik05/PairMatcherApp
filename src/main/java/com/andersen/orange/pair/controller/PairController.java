package com.andersen.orange.pair.controller;

import com.andersen.orange.pair.model.Pair;
import com.andersen.orange.pair.service.PairMatcherAlgorithm;
import com.andersen.orange.user.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orange/pairs")
public class PairController {
    private final PairMatcherAlgorithm pairMatchers;

    public PairController(PairMatcherAlgorithm pairMatchers) {
        this.pairMatchers = pairMatchers;
    }
    @GetMapping
    public List<Pair> createPairs(@RequestBody List<User> users) {
       return pairMatchers.pairMatcher(users);
    }
}
