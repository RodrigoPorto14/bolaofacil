package com.rodri.bolaofacil.dto;

import java.io.Serializable;

import com.rodri.bolaofacil.enitities.Team;

public class TeamSampleDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	
	public TeamSampleDTO() {}
	
	public TeamSampleDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public TeamSampleDTO(Team entity)
	{
		id = entity.getId();
		name = entity.getName();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
