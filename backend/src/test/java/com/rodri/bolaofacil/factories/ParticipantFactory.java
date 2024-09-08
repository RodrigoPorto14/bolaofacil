package com.rodri.bolaofacil.factories;

import static com.rodri.bolaofacil.factories.UserFactory.*;

import java.time.Instant;

import static com.rodri.bolaofacil.factories.SweepstakeFactory.*;

import com.rodri.bolaofacil.entities.Participant;
import com.rodri.bolaofacil.entities.Sweepstake;
import com.rodri.bolaofacil.entities.User;
import com.rodri.bolaofacil.entities.enums.Role;

public class ParticipantFactory {
	
	public static Participant participant(User user, Sweepstake sweepstake)
	{
		return new Participant(user, sweepstake, Role.OWNER, Instant.now());
	}
	
	public static Participant existingParticipant() { return participant(existingUser(), existingSweepstake()); }
	
	public static Participant nonExistingParticipant(){ return participant(nonExistingUser(), nonExistingSweepstake()); }
	
}
