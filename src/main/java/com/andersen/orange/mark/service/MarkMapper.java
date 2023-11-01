package com.andersen.orange.mark.service;

import com.andersen.orange.exception.DateFormatException;
import com.andersen.orange.mark.dto.IndividualMarkDto;
import com.andersen.orange.mark.model.IndividualMark;
import com.andersen.orange.user.model.User;
import com.andersen.orange.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MarkMapper {
    private final UserRepository userRepository;

    @Autowired
    public MarkMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public IndividualMark mapToEntity(IndividualMarkDto individualMarkDto) {
        User user = userRepository.findById(individualMarkDto.getId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id '%s' not found", individualMarkDto.getId())));
        String dateString = individualMarkDto.getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new DateFormatException("Your date should have pattern 'yyyy-MM-dd'");
        }
        return IndividualMark.builder()
                .user(user)
                .markDate(date)
                .individualMark(individualMarkDto.getMark())
                .build();
    }

    public IndividualMarkDto mapToDto(IndividualMark individualMark) {
        return IndividualMarkDto.builder()
                .id(individualMark.getId())
                .date(individualMark.getMarkDate().toString())
                .mark(individualMark.getIndividualMark())
                .build();
    }
}
