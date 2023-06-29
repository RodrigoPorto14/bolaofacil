package com.rodri.bolaofacil.dto;

import com.rodri.bolaofacil.enitities.Team;

public class TeamDTO extends TeamSampleDTO {
	private static final long serialVersionUID = 1L;
	
	private String imgUri;
	
	public TeamDTO() {}

	public TeamDTO(Team entity)
	{
		super(entity);
		imgUri = entity.getImgUri();
	}

	public TeamDTO(Long id, String name, String imgUri) {
		super(id, name);
		this.imgUri = imgUri;
	}

	public String getImgUri() {
		return imgUri;
	}
}
