package com.rodri.bolaofacil.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.rodri.bolaofacil.entities.Rule;


public class RuleSampleDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank @Size(max = 20)
	private String name;
	
	public RuleSampleDTO() {}

	public RuleSampleDTO(Rule entity) {
		id = entity.getId();
		name = entity.getName();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
