package com.rodri.bolaofacil.dto;

import com.rodri.bolaofacil.enitities.Participant;
import com.rodri.bolaofacil.enitities.Sweepstake;

public class ParticipantDTO extends ParticipantUpdateDTO
{
	private static final long serialVersionUID = 1L;
	
	private String sweepstakeName;
	private boolean isCustom;
	
	public ParticipantDTO() {}
	
	public ParticipantDTO(Participant entity)
	{
		super(entity);
		Sweepstake sweepstake = entity.getSweepstake();
		sweepstakeName = sweepstake.getName();
		isCustom = sweepstake.getLeague().isCustom();
	}

	public String getSweepstakeName() {
		return sweepstakeName;
	}

	public boolean isCustom() {
		return isCustom;
	}
	
}
