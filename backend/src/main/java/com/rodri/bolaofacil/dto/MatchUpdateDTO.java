package com.rodri.bolaofacil.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;

import com.rodri.bolaofacil.entities.Match;

public class MatchUpdateDTO extends MatchInsertDTO{
	private static final long serialVersionUID = 1L;
	
	@Max(value = 99) @PositiveOrZero
	private Integer homeTeamScore;
	
	@Max(value = 99) @PositiveOrZero
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
