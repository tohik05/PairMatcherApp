package com.andersen.orange.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserRequestDto {
    private String name;
    private String lastname;
    private String team;
}
