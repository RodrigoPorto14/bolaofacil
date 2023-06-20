package com.rodri.bolaofacil.enitities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_rule")
public class Rule implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private Integer exactScore;
	private Integer winnerScore;
	private Integer scoreDifference;
	private Integer loserScore;
	private Integer winner;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sweepstake_id")
	private Sweepstake sweepstake;
	
	public Rule() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getExactScore() {
		return exactScore;
	}

	public void setExactScore(Integer exactScore) {
		this.exactScore = exactScore;
	}

	public Integer getWinnerScore() {
		return winnerScore;
	}

	public void setWinnerScore(Integer winnerScore) {
		this.winnerScore = winnerScore;
	}

	public Integer getScoreDifference() {
		return scoreDifference;
	}

	public void setScoreDifference(Integer scoreDifference) {
		this.scoreDifference = scoreDifference;
	}

	public Integer getLoserScore() {
		return loserScore;
	}

	public void setLoserScore(Integer loserScore) {
		this.loserScore = loserScore;
	}

	public Integer getWinner() {
		return winner;
	}

	public void setWinner(Integer winner) {
		this.winner = winner;
	}
	
	public Sweepstake getSweepstake() {
		return sweepstake;
	}

	public void setSweepstake(Sweepstake sweepstake) {
		this.sweepstake = sweepstake;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rule other = (Rule) obj;
		return Objects.equals(id, other.id);
	}
	
}
