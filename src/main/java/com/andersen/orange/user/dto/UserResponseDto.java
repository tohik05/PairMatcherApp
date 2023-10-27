package com.andersen.orange.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class UserResponseDto {
    private Long id;
    private String name;
    private String lastname;
    private String team;
}
