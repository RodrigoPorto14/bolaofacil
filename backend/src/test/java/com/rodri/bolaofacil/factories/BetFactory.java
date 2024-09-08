package com.rodri.bolaofacil.factories;

import static com.rodri.bolaofacil.factories.MatchFactory.match;
import static com.rodri.bolaofacil.factories.SweepstakeFactory.customSweepstake;
import static com.rodri.bolaofacil.factories.UserFactory.existingUser;

import com.rodri.bolaofacil.entities.Bet;
import com.rodri.bolaofacil.entities.Match;
import com.rodri.bolaofacil.entities.Sweepstake;
import com.rodri.bolaofacil.entities.User;

public class BetFactory {
	
	private static Bet bet(User user, Sweepstake sweepstake, Match match)
	{
		return new Bet(user, sweepstake, match, 2, 0);
	}
	
	public static Bet betWithMatchStarted() { return bet(existingUser(), customSweepstake(), match(1L)); }
	
	public static Bet existingBet() { return bet(existingUser(), customSweepstake(), match(2L)); }
	
	public static Bet nonExistingBet(){ return bet(existingUser(), customSweepstake(), match(4L)); }
	
}
