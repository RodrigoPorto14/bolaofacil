package com.rodri.bolaofacil.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserPasswordUpdateDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotBlank @Size(min = 6)
	private String newPassword;
	
	@NotBlank @Size(min = 6)
	private String oldPassword;
	
	public UserPasswordUpdateDTO() {}

	public String getNewPassword() {
		return newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}
	
	
}

