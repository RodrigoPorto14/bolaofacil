package com.rodri.bolaofacil.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rodri.bolaofacil.entities.Sweepstake;

public class SweepstakeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank @Size(min = 4, max = 25)
	private String name;
	@NotNull
	private boolean private_;
	private Long leagueId;
	private String ownerName;
	private boolean hasRequest;
	
	public SweepstakeDTO() {}
	
	public SweepstakeDTO(Long id, String name, boolean isPrivate, String ownerName)
	{
		this.id = id;
		this.name = name;
		this.private_ = isPrivate;
		this.ownerName = ownerName;
	}
	
	public SweepstakeDTO(Long id, String name, boolean isPrivate, String ownerName, boolean hasRequest)
	{
		this(id, name, isPrivate, ownerName);
		this.hasRequest = hasRequest;
	}
	
	public SweepstakeDTO(Sweepstake entity)
	{
		id = entity.getId();
		name = entity.getName();
		private_ = entity.isPrivate();
		leagueId = entity.getLeague().getId();
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

	public Long getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(Long leagueId) {
		this.leagueId = leagueId;
	}

	public boolean isHasRequest() {
		return hasRequest;
	}

	public String getOwnerName() {
		return ownerName;
	}
	
	
}
