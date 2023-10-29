package com.andersen.orange.pair.dto;

import com.andersen.orange.user.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PairDto {

    private UserResponseDto mainUser;
    private UserResponseDto opponentUser;
}
