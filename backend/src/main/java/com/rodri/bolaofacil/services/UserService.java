package com.rodri.bolaofacil.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rodri.bolaofacil.components.JwtUtil;
import com.rodri.bolaofacil.dto.UserDTO;
import com.rodri.bolaofacil.dto.UserInsertDTO;
import com.rodri.bolaofacil.enitities.User;
import com.rodri.bolaofacil.repositories.UserRepository;
import com.rodri.bolaofacil.services.exceptions.DataBaseException;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
    private JwtUtil jwtUtil;
	
	@Autowired
	EmailService emailService;
	
	@Value("${base.url}")
	private String baseUrl;
	
	@Autowired
	UserRepository userRep;
	
	@Transactional(readOnly = true)
	public UserDTO findAuthenticated() 
	{
		User user = authService.authenticated();
		return new UserDTO(user);
	}
	
	@Transactional
	public UserDTO insert(UserInsertDTO dto) 
	{
		validateUser(dto.getEmail(), dto.getNickname());
		User entity = new User();
		entity.setEmail(dto.getEmail());
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity.setNickname(dto.getNickname());
		userRep.save(entity);
		
		User user = userRep.findByEmail(dto.getEmail());
		String token = jwtUtil.generateToken(user.getId());
		user.setToken(token);
		userRep.save(user);
		
		return new UserDTO(user);		
	}
	
	@Transactional(readOnly = true)
	public void sendVerificationEmail(UserDTO dto) 
	{
		String verificationLink = baseUrl + "/users/verify?token=" + dto.getToken();
        String subject = "Verificação de Registro Bolão Fácil";
        String message = "Por favor, clique no link abaixo para verificar seu registro:\n\n" + verificationLink;
        emailService.sendEmail(dto.getEmail(), subject, message);
	}
	
	@Transactional
	public String verifyUser(String token) 
	{
		User user = userRep.findByToken(token);
		
		if(user == null)
			return "Token inválido ou expirado";
        
		user.setActive(true);
		userRep.save(user);
		return "Verificação concluída com sucesso";
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		User user = userRep.findByEmail(username);
		
		if(user == null) 
			throw new UsernameNotFoundException("Email not found"); 
		
		return user;
	}
	
	private void validateUser(String email, String nickname)
	{
		String msg = "";
		
		if(userRep.findByEmail(email) != null)
			msg += "existing email ";

		if(userRep.findByNickname(nickname) != null)
			msg += "existing nickname ";	
			
		if(!msg.isEmpty())
			throw new DataBaseException(msg);
	}

	
	
}
