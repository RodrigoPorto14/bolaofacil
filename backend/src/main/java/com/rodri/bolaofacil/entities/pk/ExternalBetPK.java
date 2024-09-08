package com.rodri.bolaofacil.entities.pk;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.rodri.bolaofacil.entities.Sweepstake;
import com.rodri.bolaofacil.entities.User;

@Embeddable
public class ExternalBetPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sweepstake_id")
	private Sweepstake sweepstake;
	
	private Long externalMatchId;
	
	public ExternalBetPK() {}

	public ExternalBetPK(User user, Sweepstake sweepstake, Long externalMatchId) {
		this.user = user;
		this.sweepstake = sweepstake;
		this.externalMatchId = externalMatchId;
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

	public Long getExternalMatchId() {
		return externalMatchId;
	}

	public void setExternalMatchId(Long externalMatchId) {
		this.externalMatchId = externalMatchId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(externalMatchId, sweepstake, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExternalBetPK other = (ExternalBetPK) obj;
		return Objects.equals(externalMatchId, other.externalMatchId) && Objects.equals(sweepstake, other.sweepstake)
				&& Objects.equals(user, other.user);
	}

	

}
