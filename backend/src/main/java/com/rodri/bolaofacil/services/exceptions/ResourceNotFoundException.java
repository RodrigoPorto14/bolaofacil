package com.rodri.bolaofacil.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(){ super("Entity not found"); }
	
	public ResourceNotFoundException(String msg){ super(msg); }

}
