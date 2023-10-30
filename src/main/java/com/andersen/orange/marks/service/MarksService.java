package com.andersen.orange.marks.service;

import com.andersen.orange.pair.model.Pair;
import com.andersen.orange.user.model.User;

public interface MarksService {
    void saveMark(User user, Pair pair, Double mark);
}
