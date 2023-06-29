package com.rodri.bolaofacil.cblol;

import java.util.List;

public class CblolMatch 
{
	private Long id;
	private String state;
	private Long previousMatchIds;
	private List<String> flags;
	private List<CblolTeam> teams;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getPreviousMatchIds() {
		return previousMatchIds;
	}
	public void setPreviousMatchIds(Long previousMatchIds) {
		this.previousMatchIds = previousMatchIds;
	}
	public List<String> getFlags() {
		return flags;
	}
	public void setFlags(List<String> flags) {
		this.flags = flags;
	}
	public List<CblolTeam> getTeams() {
		return teams;
	}
	public void setTeams(List<CblolTeam> teams) {
		this.teams = teams;
	}
}
