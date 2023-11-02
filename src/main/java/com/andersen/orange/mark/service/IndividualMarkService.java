package com.andersen.orange.mark.service;

import com.andersen.orange.mark.dto.IndividualMarkDto;

public interface IndividualMarkService {
    IndividualMarkDto addIndividualMark(Long userId, IndividualMarkDto individualMarkDto);
}
