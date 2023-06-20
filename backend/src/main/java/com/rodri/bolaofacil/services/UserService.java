package com.rodri.bolaofacil.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rodri.bolaofacil.dto.UserDTO;
import com.rodri.bolaofacil.dto.UserInsertDTO;
import com.rodri.bolaofacil.enitities.User;
import com.rodri.bolaofacil.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRep;
	
	@Transactional
	public UserDTO insert(UserInsertDTO dto) {
		User entity = new User();
		entity.setEmail(dto.getEmail());
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity.setNickname(dto.getNickname());
		userRep.save(entity);
		return new UserDTO(entity);		
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRep.findByEmail(username);
		if(user == null) throw new UsernameNotFoundException("Email not found"); 
		return user;
	}
	
}
