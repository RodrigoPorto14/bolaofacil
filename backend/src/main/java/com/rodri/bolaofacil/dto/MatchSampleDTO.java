package com.rodri.bolaofacil.dto;

import java.io.Serializable;
import java.time.Instant;

import com.rodri.bolaofacil.enitities.Match;

public class MatchSampleDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private Instant startMoment;
	
	public MatchSampleDTO() {}
	
	public MatchSampleDTO(Match match)
	{
		this.id = match.getId();
		this.name = match.getHomeTeam().getName() + " X " + match.getAwayTeam().getName();
		this.startMoment = match.getStartMoment();
	}

	public Long getId() {
		return id;
	}

	public String getName(){
		return name;
	}

	public Instant getStartMoment() {
		return startMoment;
	}
	
}
