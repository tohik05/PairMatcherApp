package com.andersen.orange.user.dto;

import com.andersen.orange.team.model.Team;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserRequestDto {
    private Long id;
    private String name;
    private String lastname;
    private String team;
}
