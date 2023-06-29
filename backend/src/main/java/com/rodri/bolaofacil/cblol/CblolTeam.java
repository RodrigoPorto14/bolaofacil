package com.rodri.bolaofacil.cblol;

public class CblolTeam 
{
	private Long id;
	private String slug;
	private String name;
	private String code;
	private String image;
	private CblolResult result;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public CblolResult getResult() {
		return result;
	}
	public void setResult(CblolResult result) {
		this.result = result;
	}
   
}
