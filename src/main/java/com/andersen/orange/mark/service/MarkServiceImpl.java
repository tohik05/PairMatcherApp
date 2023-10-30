package com.andersen.orange.mark.service;

import com.andersen.orange.mark.model.Mark;
import com.andersen.orange.mark.repository.MarkRepository;
import com.andersen.orange.pair.model.Pair;
import com.andersen.orange.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarkServiceImpl implements MarkService {

    private final MarkRepository marksRepository;

    @Autowired
    public MarkServiceImpl(MarkRepository marksRepository) {
        this.marksRepository = marksRepository;
    }

    @Override
    public void saveMark(User user, Pair pair, Double mark) {
        marksRepository.save(Mark.builder()
                .user(user)
                .pair(pair)
                .mark(mark)
                .build());
    }
}
