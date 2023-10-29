package com.andersen.orange.pair.dto;

import com.andersen.orange.user.model.User;
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

    private User mainUser;
    private User opponentUser;
}
