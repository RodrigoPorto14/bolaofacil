package com.rodri.bolaofacil.dto;

import javax.validation.constraints.NotNull;

import com.rodri.bolaofacil.entities.Team;

public class TeamInsertDTO extends TeamUpdateDTO {
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Long sweepstakeId;
	
	public TeamInsertDTO() {}

	public TeamInsertDTO(Team entity)
	{
		super(entity);
		sweepstakeId = entity.getSweepstake().getId();
	}

	public TeamInsertDTO(Long id, String name, String imgUri, Long sweepstakeId) {
		super(id, name, imgUri);
		this.sweepstakeId = sweepstakeId;
	}

	public Long getSweepstakeId() {
		return sweepstakeId;
	}
	
}
