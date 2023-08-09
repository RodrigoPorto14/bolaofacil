package com.rodri.bolaofacil.services.exceptions;

public class ForbiddenException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public ForbiddenException()
	{
		super("Acesso não permitido");
	}

}
