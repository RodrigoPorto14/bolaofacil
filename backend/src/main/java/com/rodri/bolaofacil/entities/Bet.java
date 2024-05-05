package com.rodri.bolaofacil.enitities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.rodri.bolaofacil.enitities.pk.BetPK;

@Entity
@Table(name = "tb_bet")
public class Bet implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private BetPK id;
	
	private Integer homeTeamScore;
	private Integer awayTeamScore;
	
	public Bet() {}

	public Bet(User user, Sweepstake sweepstake, Match match, Integer homeTeamScore, Integer awayTeamScore) 
	{
		id = new BetPK();
		id.setUser(user);
		id.setSweepstake(sweepstake);
		id.setMatch(match);
		this.homeTeamScore = homeTeamScore;
		this.awayTeamScore = awayTeamScore;
	}
	
	public User getUser() { return id.getUser(); }
	
	public void setUser(User user) { id.setUser(user); }
	
	public Sweepstake getSweepstake() { return id.getSweepstake(); }
	
	public void setSweepStake(Sweepstake sweepstake) { id.setSweepstake(sweepstake); }
	
	public Match getMatch() { return id.getMatch(); }
	
	public void setMatch(Match match) { id.setMatch(match); }

	public Integer getHomeTeamScore() {
		return homeTeamScore;
	}

	public void setHomeTeamScore(Integer homeTeamScore) {
		this.homeTeamScore = homeTeamScore;
	}

	public Integer getAwayTeamScore() {
		return awayTeamScore;
	}

	public void setAwayTeamScore(Integer awayTeamScore) {
		this.awayTeamScore = awayTeamScore;
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
		Bet other = (Bet) obj;
		return Objects.equals(id, other.id);
	}
}
