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
import com.rodri.bolaofacil.services.exceptions.InvalidTokenException;
import com.rodri.bolaofacil.services.exceptions.ResourceNotFoundException;

import io.jsonwebtoken.Claims;

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
	public String insert(UserInsertDTO dto) 
	{
		validateUser(dto.getEmail(), dto.getNickname());
		User entity = new User();
		User user = userRep.save(entity);
		dto.setId(user.getId());
		String token = jwtUtil.generateToken(dto);
		return token;	
	}
	
	@Transactional
	public String verifyUser(String token) 
	{
		if(!jwtUtil.validateToken(token))
			throw new InvalidTokenException("Token inválido ou expirado");
		
		Claims claims = jwtUtil.getClaims(token);
		Long id = Long.parseLong(claims.getSubject());
		String email = (String)claims.get("email");
		String nickname = (String)claims.get("nickname");
		
		validateUser(email,nickname);
		
		String password = passwordEncoder.encode((String)claims.get("password"));
        
		User entity = userRep.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não cadastrado"));
		entity.setNickname(nickname);
		entity.setEmail(email);
		entity.setPassword(password);
		userRep.save(entity);
		return "Verificação concluída com sucesso";
	}
	
	@Transactional(readOnly = true)
	public void sendVerificationEmail(String url, String token) 
	{
		String email = (String)jwtUtil.getClaims(token).get("email");
		String verificationLink = url + "/confirm-email/" + token;
        String subject = "Verificação de Registro Bolão Fácil";
        String message = "Por favor, clique no link abaixo para verificar seu registro:\n\n" + verificationLink;
        emailService.sendEmail(email, subject, message);
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
