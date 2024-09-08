package com.rodri.bolaofacil.factories;

import static com.rodri.bolaofacil.factories.SweepstakeFactory.existingSweepstake;
import static com.rodri.bolaofacil.factories.SweepstakeFactory.nonExistingSweepstake;
import static com.rodri.bolaofacil.factories.UserFactory.existingUser;
import static com.rodri.bolaofacil.factories.UserFactory.nonExistingUser;

import com.rodri.bolaofacil.entities.ExternalBet;
import com.rodri.bolaofacil.entities.Sweepstake;
import com.rodri.bolaofacil.entities.User;

public class ExternalBetFactory {
	
	private static ExternalBet ExternalBet(User user, Sweepstake sweepstake, Long matchId)
	{
		return new ExternalBet(user, sweepstake, matchId, 2, 0);
	}
	
	public static ExternalBet existingExternalBet() { return ExternalBet(existingUser(), existingSweepstake(), 2L); }
	
	public static ExternalBet nonExistingExternalBet(){ return ExternalBet(nonExistingUser(), nonExistingSweepstake(), 3L); }
	
}
