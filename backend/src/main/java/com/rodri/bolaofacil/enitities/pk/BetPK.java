package com.rodri.bolaofacil.enitities.pk;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.rodri.bolaofacil.enitities.Match;
import com.rodri.bolaofacil.enitities.Sweepstake;
import com.rodri.bolaofacil.enitities.User;

@Embeddable
public class BetPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sweepstake_id")
	private Sweepstake sweepstake;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "match_id")
	private Match match;
	
	public BetPK() {}

	public BetPK(User user, Sweepstake sweepstake, Match match) {
		this.user = user;
		this.sweepstake = sweepstake;
		this.match = match;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Sweepstake getSweepstake() {
		return sweepstake;
	}

	public void setSweepstake(Sweepstake sweepstake) {
		this.sweepstake = sweepstake;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	@Override
	public int hashCode() {
		return Objects.hash(match, sweepstake, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BetPK other = (BetPK) obj;
		return Objects.equals(match, other.match) && Objects.equals(sweepstake, other.sweepstake)
				&& Objects.equals(user, other.user);
	}

}
