package com.rodri.bolaofacil.dto;

import java.io.Serializable;

import com.rodri.bolaofacil.enitities.Sweepstake;

public class SweepstakeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private boolean private_;
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
	
	public String getOwnerName() {
		return ownerName;
	}
}
