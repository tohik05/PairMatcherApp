package com.andersen.orange.marks.service;

import com.andersen.orange.marks.model.Marks;
import com.andersen.orange.marks.repository.MarksRepository;
import com.andersen.orange.pair.model.Pair;
import com.andersen.orange.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarksServiceImpl implements MarksService {

    private final MarksRepository marksRepository;

    @Autowired
    public MarksServiceImpl(MarksRepository marksRepository) {
        this.marksRepository = marksRepository;
    }

    @Override
    public void saveMark(User user, Pair pair, Double mark) {
        marksRepository.save(Marks.builder()
                .user(user)
                .pair(pair)
                .mark(mark)
                .build());
    }
}
