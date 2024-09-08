package com.rodri.bolaofacil.dto;

import java.io.Serializable;

import com.rodri.bolaofacil.entities.Request;


public class RequestDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	private Long sweepstakeId;
	private String userNickname;
	
	public RequestDTO() {}

	public RequestDTO(Request entity) 
	{
		this.userId = entity.getUser().getId();
		this.sweepstakeId = entity.getSweepstake().getId();
		this.userNickname = entity.getUser().getNickname();
	}

	public Long getUserId() {
		return userId;
	}

	public Long getSweepstakeId() {
		return sweepstakeId;
	}

	public String getUserNickname() {
		return userNickname;
	}
	
}
