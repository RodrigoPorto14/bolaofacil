package com.rodri.bolaofacil.dto;

import java.io.Serializable;

import com.rodri.bolaofacil.enitities.Rule;


public class RuleSampleDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
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
