package com.andersen.orange.pair.service;

import com.andersen.orange.pair.dto.PairDto;
import com.andersen.orange.user.dto.UserDto;

import java.util.List;

public interface PairService {
    PairDto createPair(List<UserDto> usersDto);

    void savePairInDB(PairDto pairDto);
}
