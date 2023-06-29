package com.rodri.bolaofacil.dto;

import java.io.Serializable;

import com.rodri.bolaofacil.enitities.Bet;
import com.rodri.bolaofacil.enitities.ExternalBet;
import com.rodri.bolaofacil.enitities.Match;

public class BetDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	private MatchDTO match;
	private Integer homeTeamScore;
	private Integer awayTeamScore;
	
	public BetDTO() {}

	public BetDTO(Bet entity)
	{
		userId = entity.getUser().getId();
		match = new MatchDTO(entity.getMatch());
		homeTeamScore = entity.getHomeTeamScore();
		awayTeamScore = entity.getAwayTeamScore();
	}
	
	public BetDTO(ExternalBet bet, MatchDTO match)
	{
		userId = bet.getUser().getId();
		this.match = match;
		homeTeamScore = bet.getHomeTeamScore();
		awayTeamScore = bet.getAwayTeamScore();
	}
	
	public BetDTO(Match match, Integer homeTeamScore, Integer awayTeamScore)
	{
		this.match = new MatchDTO(match);
		this.homeTeamScore = homeTeamScore;
		this.awayTeamScore = awayTeamScore;
	}
	
	public BetDTO(MatchDTO match, Integer homeTeamScore, Integer awayTeamScore)
	{
		this.match = match;
		this.homeTeamScore = homeTeamScore;
		this.awayTeamScore = awayTeamScore;
	}
	
	public Long getUserId() {
		return userId;
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
