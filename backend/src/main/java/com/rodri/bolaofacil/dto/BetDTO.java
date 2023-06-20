package com.rodri.bolaofacil.dto;

import com.rodri.bolaofacil.enitities.Bet;

public class BetDTO {
	
	private Long id;
	private MatchDTO match;
	private Integer homeTeamScore;
	private Integer awayTeamScore;
	
	public BetDTO() {}

	public BetDTO(Bet entity)
	{
		id = entity.getUser().getId();
		match = new MatchDTO(entity.getMatch());
		homeTeamScore = entity.getHomeTeamScore();
		awayTeamScore = entity.getAwayTeamScore();
	}
	
	
	public Long getId() {return id;}
	
	public MatchDTO getMatch() {
		return match;
	}

	public Integer getHomeTeamScore() {
		return homeTeamScore;
	}

	public Integer getAwayTeamScore() {
		return awayTeamScore;
	}
	
}
