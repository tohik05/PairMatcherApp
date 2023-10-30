package com.andersen.orange.pair.service;

import com.andersen.orange.pair.dto.PairRequestDto;
import com.andersen.orange.pair.dto.PairResponseDto;
import com.andersen.orange.user.dto.UserDto;

import java.util.List;

public interface PairService {
    PairResponseDto createPair(List<UserDto> usersDto);

    void savePairInDB(PairRequestDto pairDto);
}
