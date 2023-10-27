package com.andersen.orange.pair.service;

import com.andersen.orange.pair.model.Pair;
import com.andersen.orange.pair.repository.PairRepository;
import com.andersen.orange.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PairService {
    private final PairRepository repository;

    public PairService(PairRepository repository) {
        this.repository = repository;
    }

    public void createPair(Pair pair) {
        repository.save(pair);
    }

    public List<Pair> getAllUserPairs(User user) {
        return repository.findByUser_Id(user.getId());
    }
}
