package com.rodri.bolaofacil.services.exceptions;

public class InvalidTokenException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public InvalidTokenException() { super("Token inválido ou expirado"); }
	
	public InvalidTokenException(String msg) { super(msg); }
	
}
