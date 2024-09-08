package com.rodri.bolaofacil.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.rodri.bolaofacil.entities.Team;

public class TeamUpdateDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank @Size(max = 20)
	private String name;
	
	private String imgUri;
	
	public TeamUpdateDTO() {}
	
	public TeamUpdateDTO(Long id, String name, String imgUri) {
		this.id = id;
		this.name = name;
		this.imgUri = imgUri;
	}

	public TeamUpdateDTO(Team entity)
	{
		id = entity.getId();
		name = entity.getName();
		imgUri = entity.getImgUri();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getImgUri() {
		return imgUri;
	}
}
