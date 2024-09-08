package com.rodri.bolaofacil.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.rodri.bolaofacil.entities.enums.Role;
import com.rodri.bolaofacil.entities.pk.ParticipantPK;

@Entity
@Table(name = "tb_participant")
public class Participant implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private ParticipantPK id;
	
	private Role role;
	private Instant lastAccess;
	
	public Participant() {}

	public Participant(User user, Sweepstake sweepstake, Role role, Instant lastAccess) {
		id = new ParticipantPK(user,sweepstake);
		this.role = role;
		this.lastAccess = lastAccess;
	}

	public User getUser() { return id.getUser(); }
	
	public void setUser(User user) { id.setUser(user); }
	
	public Sweepstake getSweepstake() { return id.getSweepstake(); }
	
	public void setSweepStake(Sweepstake sweepstake) { id.setSweepstake(sweepstake); }

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Instant getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Instant lastAccess) {
		this.lastAccess = lastAccess;
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
		Participant other = (Participant) obj;
		return Objects.equals(id, other.id);
	}
}
