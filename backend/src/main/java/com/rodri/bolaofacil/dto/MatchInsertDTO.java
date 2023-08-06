package com.rodri.bolaofacil.dto;

import java.io.Serializable;
import java.time.Instant;

import javax.validation.constraints.NotNull;

import com.rodri.bolaofacil.enitities.Match;
import com.rodri.bolaofacil.enitities.enums.MatchType;

public class MatchInsertDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotNull
	private Long ruleId;
	@NotNull
	private MatchType type;
	@NotNull
	private Instant startMoment;
	@NotNull
	private Long homeTeamId;
	@NotNull
	private Long awayTeamId;
	
	public MatchInsertDTO() {}

	public MatchInsertDTO(Match entity)
	{
		id = entity.getId();
		ruleId = entity.getRule().getId();
		type = entity.getType();
		startMoment = entity.getStartMoment();
		homeTeamId = entity.getHomeTeam().getId();
		awayTeamId = entity.getAwayTeam().getId();
	}

	public Long getId() {
		return id;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public MatchType getType() {
		return type;
	}

	public Instant getStartMoment() {
		return startMoment;
	}

	public Long getHomeTeamId() {
		return homeTeamId;
	}

	public Long getAwayTeamId() {
		return awayTeamId;
	}
}
