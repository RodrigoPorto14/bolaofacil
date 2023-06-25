package com.rodri.bolaofacil.dto;

import java.io.Serializable;

import com.rodri.bolaofacil.enitities.Bet;
import com.rodri.bolaofacil.enitities.Match;

public class BetDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private MatchDTO match;
	private Integer homeTeamScore;
	private Integer awayTeamScore;
	
	public BetDTO() {}

	public BetDTO(Bet entity)
	{
		match = new MatchDTO(entity.getMatch());
		homeTeamScore = entity.getHomeTeamScore();
		awayTeamScore = entity.getAwayTeamScore();
	}
	
	public BetDTO(Match match, Integer homeTeamScore, Integer awayTeamScore)
	{
		this.match = new MatchDTO(match);
		this.homeTeamScore = homeTeamScore;
		this.awayTeamScore = awayTeamScore;
	}
	

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
