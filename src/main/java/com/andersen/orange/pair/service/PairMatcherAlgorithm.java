package com.andersen.orange.pair.service;

import com.andersen.orange.pair.model.Pair;
import com.andersen.orange.user.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PairMatcherAlgorithm {
    private final PairService pairService;

    public PairMatcherAlgorithm(PairService service) {
        this.pairService = service;
    }

    public List<Pair> createPairs(List<User> users) {
        ArrayList<Pair> pairs = new ArrayList<>();
        for (User user : users) {
            user.setPairs(pairService.getAllUserPairs(user));

        }
        return pairs;
    }
}
