package com.rodri.bolaofacil.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.rodri.bolaofacil.entities.User;

public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank @Size(min = 4, max = 16) @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Apenas letra e numeros são permitidos")
	private String nickname;
	
	public UserDTO() {}
	
	public UserDTO(User entity)
	{
		id = entity.getId();
		nickname = entity.getNickname();
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
	public String getNickname() {
		return nickname;
	}
}

