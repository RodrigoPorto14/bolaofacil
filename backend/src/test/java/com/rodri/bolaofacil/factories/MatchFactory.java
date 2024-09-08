package com.rodri.bolaofacil.factories;

import static com.rodri.bolaofacil.factories.SweepstakeFactory.existingSweepstake;
import java.time.Duration;
import java.time.Instant;

import com.rodri.bolaofacil.entities.Match;

public class MatchFactory {
	
	public static Match match(Long matchId)
	{
		Match match = new Match();
		match.setId(matchId);
		match.setSweepstake(existingSweepstake());
		return match;
	}
	
	public static Match existingMatch() { return match(2L); }
	
	public static Match nonExistingMatch(){ return match(100L); }
	
	public static Match futureMatch() 
	{
		Match match = existingMatch();
		match.setStartMoment(Instant.now().plus(Duration.ofDays(1)));
		return match;
	}
	
	public static Match pastMatch() 
	{
		Match match = existingMatch();
		match.setStartMoment(Instant.now().minus(Duration.ofDays(1)));
		return match;
	}
	
}
