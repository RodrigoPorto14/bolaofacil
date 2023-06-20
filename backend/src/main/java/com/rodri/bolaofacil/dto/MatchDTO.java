package com.rodri.bolaofacil.dto;

import java.io.Serializable;
import java.time.Instant;

import com.rodri.bolaofacil.enitities.Match;
import com.rodri.bolaofacil.enitities.enums.MatchType;

public class MatchDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	//private RuleDTO rule;
	private MatchType type;
	private Instant startMoment;
	private TeamDTO homeTeam;
	private TeamDTO awayTeam;
	private Integer homeTeamScore;
	private Integer awayTeamScore;
	
	public MatchDTO() {}

	public MatchDTO(Match entity)
	{
		id = entity.getId();
		type = entity.getType();
		startMoment = entity.getStartMoment();
		homeTeam = new TeamDTO(entity.getHomeTeam());
		awayTeam = new TeamDTO(entity.getAwayTeam());
		homeTeamScore = entity.getHomeTeamScore();
		awayTeamScore = entity.getAwayTeamScore();
	}
	
	public Long getId() {
		return id;
	}

	//public Long getRuleId() {
	//	return ruleId;
	//}

	public MatchType getType() {
		return type;
	}

	public Instant getStartMoment() {
		return startMoment;
	}

	public TeamDTO getHomeTeam() {
		return homeTeam;
	}

	public TeamDTO getAwayTeam() {
		return awayTeam;
	}

	public Integer getHomeTeamScore() {
		return homeTeamScore;
	}

	public Integer getAwayTeamScore() {
		return awayTeamScore;
	}
}
