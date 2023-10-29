package com.andersen.orange.team.service;

import com.andersen.orange.team.dto.TeamCreateDto;
import com.andersen.orange.team.model.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {
    public Team mapToEntity(TeamCreateDto team) {
        return Team.builder()
                .name(team.getName())
                .build();
    }
}
