package com.rodri.bolaofacil.dto;

import com.rodri.bolaofacil.entities.Participant;

public class ParticipantSampleDTO extends ParticipantUpdateDTO {
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	private String userNickname;
	
	public ParticipantSampleDTO() {}

	public ParticipantSampleDTO(Participant entity) {
		super(entity);
		this.userId = entity.getUser().getId();
		this.userNickname = entity.getUser().getNickname();
	}

	public Long getUserId() {
		return userId;
	}

	public String getUserNickname() {
		return userNickname;
	}
}
