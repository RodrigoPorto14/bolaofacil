package com.rodri.bolaofacil.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_league")
public class League implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String endpoint;
	private String season;
	private boolean isActive;
	private boolean isCustom;
	
	public League() {}

	public League(Long id, String name, String endpoint, String season, boolean isActive, boolean isCustom) {
		this.id = id;
		this.name = name;
		this.endpoint = endpoint;
		this.season = season;
		this.isActive = isActive;
		this.isCustom = isCustom;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEndpoint() {
		return endpoint;
	}


	public boolean isCustom() {
		return isCustom;
	}


	public void setCustom(boolean isCustom) {
		this.isCustom = isCustom;
	}


	public void setApi(String endpoint) {
		this.endpoint = endpoint;
	}


	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		League other = (League) obj;
		return Objects.equals(id, other.id);
	}
}
