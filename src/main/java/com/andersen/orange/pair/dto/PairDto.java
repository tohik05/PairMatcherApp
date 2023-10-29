package com.andersen.orange.pair.dto;

import com.andersen.orange.user.dto.UserDto;
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

    private UserDto mainUser;
    private UserDto opponentUser;
}
