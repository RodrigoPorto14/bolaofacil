package com.rodri.bolaofacil.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.rodri.bolaofacil.entities.pk.RequestPK;

@Entity
@Table(name = "tb_request")
public class Request implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private RequestPK id;
	
	public Request() {}

	public Request(User user, Sweepstake sweepstake) 
	{
		id = new RequestPK(user,sweepstake);
	}

	public User getUser() { return id.getUser(); }
	
	public void setUser(User user) { id.setUser(user); }
	
	public Sweepstake getSweepstake() { return id.getSweepstake(); }
	
	public void setSweepStake(Sweepstake sweepstake) { id.setSweepstake(sweepstake); }

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
		Request other = (Request) obj;
		return Objects.equals(id, other.id);
	}
}
