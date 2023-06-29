package com.rodri.bolaofacil.dto;

import com.rodri.bolaofacil.enitities.Participant;
import com.rodri.bolaofacil.enitities.enums.Tournament;

public class ParticipantDTO extends ParticipantUpdateDTO
{
	private static final long serialVersionUID = 1L;
	
	private String sweepstakeName;
	private Tournament tournament;
	
	public ParticipantDTO() {}
	
	public ParticipantDTO(Participant entity)
	{
		super(entity);
		sweepstakeName = entity.getSweepstake().getName();
		tournament = entity.getSweepstake().getTournament();
	}

	public String getSweepstakeName() {
		return sweepstakeName;
	}

	public Tournament getTournament() {
		return tournament;
	}
	
}
