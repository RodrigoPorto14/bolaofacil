package com.rodri.bolaofacil.dto;

import com.rodri.bolaofacil.enitities.Match;

public class MatchUpdateDTO extends MatchInsertDTO{
	private static final long serialVersionUID = 1L;
	
	private Integer homeTeamScore;
	private Integer awayTeamScore;
	
	public MatchUpdateDTO() {}

	public MatchUpdateDTO(Match entity)
	{
		super(entity);
		homeTeamScore = entity.getHomeTeamScore();
		awayTeamScore = entity.getAwayTeamScore();
	}

	public Integer getHomeTeamScore() {
		return homeTeamScore;
	}

	public Integer getAwayTeamScore() {
		return awayTeamScore;
	}
}
