package com.andersen.orange.mark.service;

import com.andersen.orange.mark.dto.IndividualMarkDto;
import com.andersen.orange.mark.model.IndividualMark;
import com.andersen.orange.mark.repository.IndividualMarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndividualMarkServiceImpl implements IndividualMarkService {
    private final IndividualMarkRepository individualMarkRepository;
    private final MarkMapper markMapper;

    @Autowired
    public IndividualMarkServiceImpl(IndividualMarkRepository individualMarkRepository, MarkMapper markMapper) {
        this.individualMarkRepository = individualMarkRepository;
        this.markMapper = markMapper;
    }

    @Override
    public IndividualMarkDto addIndividualMark(IndividualMarkDto individualMarkDto) {
        IndividualMark saved = individualMarkRepository.save(markMapper.mapToEntity(individualMarkDto));
        return markMapper.mapToDto(saved);
    }
}
