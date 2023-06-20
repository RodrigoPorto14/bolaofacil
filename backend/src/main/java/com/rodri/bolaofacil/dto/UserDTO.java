package com.rodri.bolaofacil.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.rodri.bolaofacil.enitities.User;

public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank(message = "Campo obrigatório")
	@Email(message = "Favor entrar um email válido")
	private String email;
	
	@NotBlank(message = "Campo obrigatório")
	private String nickname;
	
	public UserDTO() {}
	
	public UserDTO(User entity)
	{
		id = entity.getId();
		email = entity.getEmail();
		nickname = entity.getNickname();
	}
	
	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}
	
	public String getNickname() {
		return nickname;
	}
	
}

