package com.rodri.bolaofacil.factories;

import static com.rodri.bolaofacil.factories.SweepstakeFactory.existingSweepstake;
import com.rodri.bolaofacil.entities.Team;

public class TeamFactory {
	
	public static Team team(Long id)
	{
		return new Team(id, "Time", "time.png");
	}
	
	public static Team existingTeam() 
	{ 
		Team team = new Team(1L, "Vasco", "https://upload.wikimedia.org/wikipedia/pt/thumb/a/ac/CRVascodaGama.png/180px-CRVascodaGama.png");
		team.setSweepstake(existingSweepstake());
		return team;
	}
	
}
