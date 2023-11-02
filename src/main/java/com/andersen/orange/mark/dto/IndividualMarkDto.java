package com.andersen.orange.mark.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class IndividualMarkDto {
    @NotNull(message = "Date must be present")
    private String date;
    @NotNull(message = "Mark must be present")
    private Double mark;
}
