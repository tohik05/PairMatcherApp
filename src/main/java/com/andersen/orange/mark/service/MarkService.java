package com.andersen.orange.mark.service;

import com.andersen.orange.pair.model.Pair;
import com.andersen.orange.user.model.User;

public interface MarkService {
    void saveMark(User user, Pair pair, Double mark);
}
