package com.rodri.bolaofacil.dto;

import java.io.Serializable;
import java.time.Instant;

import com.rodri.bolaofacil.cblol.CblolMatch;
import com.rodri.bolaofacil.cblol.CblolTeam;
import com.rodri.bolaofacil.enitities.Match;
import com.rodri.bolaofacil.enitities.enums.MatchType;

public class MatchDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private MatchType type;
	private Instant startMoment;
	private RuleDTO rule;
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
		rule = new RuleDTO(entity.getRule());
		homeTeam = new TeamDTO(entity.getHomeTeam());
		awayTeam = new TeamDTO(entity.getAwayTeam());
		homeTeamScore = entity.getHomeTeamScore();
		awayTeamScore = entity.getAwayTeamScore();
	}
	
	public MatchDTO(CblolMatch cblol)
	{
		CblolTeam homeTeam = cblol.getTeams().get(0);
		CblolTeam awayTeam = cblol.getTeams().get(1);
		boolean matchCompleted = cblol.getState().equals("completed");
		id = cblol.getId()%1000000;
		rule = new RuleDTO(1,0,0,0,0);
		type = MatchType.MD1;
		this.homeTeam = new TeamDTO(homeTeam.getId(), homeTeam.getName(), homeTeam.getImage());
		this.awayTeam = new TeamDTO(awayTeam.getId(), awayTeam.getName(), awayTeam.getImage());
		homeTeamScore = matchCompleted ? homeTeam.getResult().getGameWins() : null;
		awayTeamScore = matchCompleted ? awayTeam.getResult().getGameWins() : null;
	}

	public Long getId() {
		return id;
	}

	public MatchType getType() {
		return type;
	}

	public Instant getStartMoment() {
		return startMoment;
	}
	
	public RuleDTO getRule() {
		return rule;
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
