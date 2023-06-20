package com.rodri.bolaofacil.dto;

import java.io.Serializable;

import com.rodri.bolaofacil.enitities.Match;

public class MatchSampleDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	
	public MatchSampleDTO() {}
	
	public MatchSampleDTO(Match match)
	{
		this.id = match.getId();
		this.name = match.getHomeTeam().getName() + " X " + match.getAwayTeam().getName();
	}

	public Long getId() {
		return id;
	}

	public String getName(){
		return name;
	}
}
