package com.rodri.bolaofacil.services.exceptions;

public class DataBaseException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public DataBaseException() { super("Integrity violation"); }
	
	public DataBaseException(String msg){ super(msg); }
	

}
