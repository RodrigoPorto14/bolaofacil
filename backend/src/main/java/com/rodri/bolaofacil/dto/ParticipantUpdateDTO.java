package com.rodri.bolaofacil.dto;

import java.io.Serializable;

import com.rodri.bolaofacil.enitities.Participant;
import com.rodri.bolaofacil.enitities.enums.Role;

public class ParticipantUpdateDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Role role;
	
	public ParticipantUpdateDTO() {}

	public ParticipantUpdateDTO(Participant entity) {
		this.role = entity.getRole();
	}

	public Role getRole() {
		return role;
	}
	
	
}
