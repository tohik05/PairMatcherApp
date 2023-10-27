package com.andersen.orange.pair.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PairDto {
    private String firstUserName;
    private String firstUserLastname;
    private String firstUserTeam;
    private String secondUserName;
    private String secondUserLastname;
    private String secondUserTeam;

}
