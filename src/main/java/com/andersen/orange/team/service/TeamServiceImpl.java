package com.andersen.orange.team.service;

import com.andersen.orange.exception.TeamHasAlreadyExistException;
import com.andersen.orange.team.dto.TeamCreateDto;
import com.andersen.orange.team.model.Team;
import com.andersen.orange.team.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    @Override
    public void create(TeamCreateDto team) {
        Optional<Team> existTeam = teamRepository.findByNameIgnoreCase(team.getName());
        if (existTeam.isPresent()) {
            throw new TeamHasAlreadyExistException("Team with that name has already exists");
        } else {
            teamRepository.save(teamMapper.mapToEntity(team));
        }
    }
}
