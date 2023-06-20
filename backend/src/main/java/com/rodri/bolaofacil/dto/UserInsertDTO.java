package com.rodri.bolaofacil.dto;

import javax.validation.constraints.NotBlank;

public class UserInsertDTO extends UserDTO {
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Campo obrigatório")
	private String password;
	
	public UserInsertDTO() {}

	public String getPassword() {
		return password;
	}
	
}

