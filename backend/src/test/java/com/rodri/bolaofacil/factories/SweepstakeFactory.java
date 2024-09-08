package com.rodri.bolaofacil.factories;

import static com.rodri.bolaofacil.factories.LeagueFactory.customLeague;
import static com.rodri.bolaofacil.factories.LeagueFactory.existingLeague;

import com.rodri.bolaofacil.entities.Sweepstake;

public class SweepstakeFactory {
	
	public static Sweepstake sweepstake(Long sweepstakeId)
	{
		Sweepstake sweepstake = new Sweepstake();
		sweepstake.setId(sweepstakeId);
		sweepstake.setLeague(existingLeague());
		return sweepstake;
	}
	
	public static Sweepstake existingSweepstake() { return sweepstake(2L); }
	
	public static Sweepstake nonExistingSweepstake(){ return sweepstake(100L); }
	
	public static Sweepstake customSweepstake() 
	{
		Sweepstake sweepstake = existingSweepstake();
		sweepstake.setLeague(customLeague());
		return sweepstake;
	}
	
}
