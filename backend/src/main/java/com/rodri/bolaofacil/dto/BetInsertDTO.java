package com.rodri.bolaofacil.dto;

import java.io.Serializable;

import com.rodri.bolaofacil.enitities.Bet;
import com.rodri.bolaofacil.enitities.ExternalBet;

public class BetInsertDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long matchId;
	private Integer homeTeamScore;
	private Integer awayTeamScore;
	
	public BetInsertDTO() {}
	
	public BetInsertDTO(Bet entity)
	{
		matchId = entity.getMatch().getId();
		homeTeamScore = entity.getHomeTeamScore();
		awayTeamScore = entity.getAwayTeamScore();
	}
	
	public BetInsertDTO(ExternalBet entity)
	{
		matchId = entity.getExternalMatchId();
		homeTeamScore = entity.getHomeTeamScore();
		awayTeamScore = entity.getAwayTeamScore();
	}

	public Long getMatchId() {
		return matchId;
	}

	public Integer getHomeTeamScore() {
		return homeTeamScore;
	}

	public Integer getAwayTeamScore() {
		return awayTeamScore;
	}
	
	

}
