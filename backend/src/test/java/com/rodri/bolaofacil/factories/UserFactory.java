package com.rodri.bolaofacil.factories;

import com.rodri.bolaofacil.entities.User;

public class UserFactory {
	
	public static User user(Long userId)
	{
		User user = new User();
		user.setId(userId);
		return user;
	}
	
	public static User existingUser() { return user(1L); }
	
	public static User nonExistingUser(){ return user(100L); }
	
	public static User ownerUser() { return new User(1L, "rodrigo@gmail.com", "Rodrigo", "654321"); }
	
	public static User playerUser() { return new User(2L, "larissa@gmail.com", "Larissa", "654321"); }

	
}
