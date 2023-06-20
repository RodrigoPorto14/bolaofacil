package com.rodri.bolaofacil.dto;

import java.io.Serializable;

public class CompetitorDTO implements Serializable, Comparable<CompetitorDTO> {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Integer exactScore = 0;
	private Integer winnerScore = 0;
	private Integer scoreDifference = 0;
	private Integer loserScore = 0;
	private Integer winner = 0;
	private Integer points = 0;
	
	public CompetitorDTO() {}

	public CompetitorDTO(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
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

	public Integer getPoints() {
		return points;
	}

	public void upExactScore(Integer weight) {
		this.exactScore = exactScore + 1;
		points += weight;
	}

	public void upWinnerScore(Integer weight) {
		this.winnerScore = winnerScore + 1;
		points += weight;
	}

	public void upScoreDifference(Integer weight) {
		this.scoreDifference = scoreDifference + 1;
		points += weight;
	}

	public void upLoserScore(Integer weight) {
		this.loserScore = loserScore + 1;
		points += weight;
	}

	public void upWinner(Integer weight) {
		this.winner = winner + 1;
		points += weight;
	}
	
	@Override
    public int compareTo(CompetitorDTO other) {
        return other.getPoints().compareTo(this.points);
    }

}
