package com.rodri.bolaofacil.enitities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rodri.bolaofacil.enitities.enums.Tournament;

@Entity
@Table(name = "tb_sweepstake")
public class Sweepstake implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private boolean isPrivate;
	private Tournament tournament;
	
	public Sweepstake() {}
	
	public Sweepstake(Long id, String name, boolean isPrivate, User owner, Tournament tournament) {
		this.id = id;
		this.name = name;
		this.isPrivate = isPrivate;
		this.tournament = tournament;
	}

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

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
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
		Sweepstake other = (Sweepstake) obj;
		return Objects.equals(id, other.id);
	}
}
