package com.rodri.bolaofacil.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rodri.bolaofacil.enitities.Sweepstake;
import com.rodri.bolaofacil.enitities.enums.Tournament;

public class SweepstakeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank @Size(min = 4, max = 25)
	private String name;
	@NotNull
	private boolean private_;
	private Tournament tournament;
	private String ownerName;
	
	public SweepstakeDTO() {}
	
	public SweepstakeDTO(Long id, String name, boolean isPrivate, String ownerName)
	{
		this.id = id;
		this.name = name;
		this.private_ = isPrivate;
		this.ownerName = ownerName;
	}
	
	public SweepstakeDTO(Sweepstake entity)
	{
		id = entity.getId();
		name = entity.getName();
		private_ = entity.isPrivate();
		tournament = entity.getTournament();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public boolean getPrivate_() {
		return private_;
	}
	
	public Tournament getTournament() {
		return tournament;
	}

	public String getOwnerName() {
		return ownerName;
	}
}
