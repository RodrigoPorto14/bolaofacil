package com.rodri.bolaofacil.dto;

import java.io.Serializable;
import java.time.Instant;

import javax.validation.constraints.NotNull;

import com.rodri.bolaofacil.entities.Match;
import com.rodri.bolaofacil.entities.enums.MatchType;

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
	private Long sweepstakeId;
	
	public MatchInsertDTO() {}

	public MatchInsertDTO(Match entity)
	{
		id = entity.getId();
		ruleId = entity.getRule().getId();
		type = entity.getType();
		startMoment = entity.getStartMoment();
		homeTeamId = entity.getHomeTeam().getId();
		awayTeamId = entity.getAwayTeam().getId();
		sweepstakeId = entity.getSweepstake().getId();
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

	public Long getSweepstakeId() {
		return sweepstakeId;
	}
	
}
