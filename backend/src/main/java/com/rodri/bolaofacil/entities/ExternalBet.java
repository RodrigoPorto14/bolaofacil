package com.rodri.bolaofacil.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.rodri.bolaofacil.entities.pk.ExternalBetPK;

@Entity
@Table(name = "tb_external_bet")
public class ExternalBet implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private ExternalBetPK id;
	
	private Integer homeTeamScore;
	private Integer awayTeamScore;
	
	public ExternalBet() {}

	public ExternalBet(User user, Sweepstake sweepstake, Long externalMatchId, Integer homeTeamScore, Integer awayTeamScore) 
	{
		id = new ExternalBetPK();
		id.setUser(user);
		id.setSweepstake(sweepstake);
		id.setExternalMatchId(externalMatchId);
		this.homeTeamScore = homeTeamScore;
		this.awayTeamScore = awayTeamScore;
	}
	
	public User getUser() { return id.getUser(); }
	
	public void setUser(User user) { id.setUser(user); }
	
	public Sweepstake getSweepstake() { return id.getSweepstake(); }
	
	public void setSweepStake(Sweepstake sweepstake) { id.setSweepstake(sweepstake); }
	
	public Long getExternalMatchId() { return id.getExternalMatchId(); }
	
	public void setExternalMatchId(Long externalMatchId) { id.setExternalMatchId(externalMatchId); }

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
		ExternalBet other = (ExternalBet) obj;
		return Objects.equals(id, other.id);
	}
}
