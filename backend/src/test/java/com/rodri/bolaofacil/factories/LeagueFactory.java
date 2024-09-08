package com.rodri.bolaofacil.factories;

import com.rodri.bolaofacil.entities.League;

public class LeagueFactory {
	
	public static League customLeague(){ return new League(1L, "Personalizado", "", "", true, true); }
	
	public static League existingLeague() { return new League(2L, "CBLOL", "cblol", "2014-1", true, false); }
	
	public static League nonExistingLeague()
	{ 
		League league = new League();
		league.setId(100L);
		return league;
	}
	
}
