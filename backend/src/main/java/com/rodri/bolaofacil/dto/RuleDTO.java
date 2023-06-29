package com.rodri.bolaofacil.dto;

import com.rodri.bolaofacil.enitities.Rule;


public class RuleDTO extends RuleSampleDTO {
	private static final long serialVersionUID = 1L;
	
	private Integer exactScore;
	private Integer winnerScore;
	private Integer scoreDifference;
	private Integer loserScore;
	private Integer winner;
	private Long sweepstakeId;
	
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
	
	public Long getSweepstakeId() {
		return sweepstakeId;
	}
}
