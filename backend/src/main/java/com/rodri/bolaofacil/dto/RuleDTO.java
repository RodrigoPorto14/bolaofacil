package com.rodri.bolaofacil.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.rodri.bolaofacil.enitities.Rule;


public class RuleDTO extends RuleSampleDTO {
	private static final long serialVersionUID = 1L;
	
	@NotNull @Max(value = 99) @PositiveOrZero
	private Integer exactScore;
	@NotNull @Max(value = 99) @PositiveOrZero
	private Integer winnerScore;
	@NotNull @Max(value = 99) @PositiveOrZero
	private Integer scoreDifference;
	@NotNull @Max(value = 99) @PositiveOrZero
	private Integer loserScore;
	@NotNull @Max(value = 99) @PositiveOrZero
	private Integer winner;
	
	public RuleDTO() {}

	public RuleDTO(Rule entity) {
		super(entity);
		exactScore = entity.getExactScore();
		winnerScore = entity.getWinnerScore();
		scoreDifference = entity.getScoreDifference();
		loserScore = entity.getLoserScore();
		winner = entity.getWinner();
		
	}
	
	public RuleDTO(Integer exactScore, Integer winnerScore, Integer scoreDifference, Integer loserScore, Integer winner) 
	{
		this.exactScore = exactScore;
		this.winnerScore = winnerScore;
		this.scoreDifference = scoreDifference;
		this.loserScore = loserScore;
		this.winner = winner;
	}

	public Integer getExactScore() {
		return exactScore;
	}

	public Integer getWinnerScore() {
		return winnerScore;
	}

	public Integer getScoreDifference() {
		return scoreDifference;
	}

	public Integer getLoserScore() {
		return loserScore;
	}

	public Integer getWinner() {
		return winner;
	}
}
