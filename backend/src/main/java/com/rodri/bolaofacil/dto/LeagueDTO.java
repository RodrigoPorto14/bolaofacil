package com.rodri.bolaofacil.dto;

import java.io.Serializable;

import com.rodri.bolaofacil.entities.League;

public class LeagueDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	
	public LeagueDTO() {}
	
	public LeagueDTO(League entity)
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
