package com.rodri.bolaofacil.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserInsertDTO extends UserDTO {
	private static final long serialVersionUID = 1L;
	
	@NotBlank @Email
	private String email;
	
	@NotBlank @Size(min = 6)
	private String password;
	
	public UserInsertDTO() {}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}
}

