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
	
	
	
	/*****************************************************************
	                      ENCONTRAR USUARIO                           
	*****************************************************************/
	
	@Transactional(readOnly = true)
	public UserDTO findAuthenticated() 
	{
		User user = authService.authenticated();
		return new UserDTO(user);
	}
	
	/*****************************************************************
                          ATUALIZAR USUARIO                             
	*****************************************************************/
	
	@Transactional
	public UserDTO update(UserDTO dto)
	{
		User user = authService.authenticated();
		user.setNickname(dto.getNickname());
		userRep.save(user);
		return new UserDTO(user);
	}
	
	/*****************************************************************
                  CADASTRO DE USUARIO EM DUAS ETAPAS
	 *****************************************************************/
	@Transactional
	public String insert(UserInsertDTO dto) 
	{
		validateUser(dto.getEmail(), dto.getNickname());
		User entity = new User();
		User user = userRep.save(entity);
		dto.setId(user.getId());
		String token = jwtUtil.generateRegisterToken(dto);
		return token;	
	}
	
	@Transactional
	public String verifyUser(String token) 
	{
		jwtUtil.validateToken(token);
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
	
	/*****************************************************************
    					 RECUPERAÇÃO DE SENHA
	*****************************************************************/
	@Transactional
	public String passwordRecovery(String email)
	{
		System.out.println("===========================================================");
		User user = userRep.findByEmail(email);
		System.out.println("===========================================================");
		
		if(user == null)
			throw new ResourceNotFoundException("Email não encontrado");
		
		String token = jwtUtil.generatePasswordRecoveryToken(user.getId(), user.getEmail(), 60);
		return token;
	}
	
	@Transactional
	public String validateRecoveryToken(String token)
	{
		jwtUtil.validateToken(token);
		return token;
	}
	
	@Transactional
	public String resetPassword(String token, String newPassword)
	{
		jwtUtil.validateToken(token);
		Long id = Long.parseLong(jwtUtil.getClaims(token).getSubject());
		User user = userRep.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		user.setPassword(passwordEncoder.encode(newPassword));
		return "Senha alterada com sucesso";
	}

	/*****************************************************************
    							ENVIAR EMAIL
	*****************************************************************/
	@Transactional(readOnly = true)
	public void sendEmail(String url, String token, String type) 
	{
		jwtUtil.validateToken(token);
		String email = (String)jwtUtil.getClaims(token).get("email");
		String link, subject, message;
		
		if(type.equals("VERIFICATION"))
		{
			link = url + "/confirm-email/" + token;
			subject = "Verificação de Registro Bolão Fácil";
			message = "Por favor, clique no link abaixo para verificar seu registro:\n\n" + link;
			emailService.sendEmail(email, subject, message);
		}
		else if(type.equals("PASSWORD_RECOVERY"))
		{
			link = url + "/password/reset/" + token;
			subject = "Recuperação de senha Bolão Fácil";
			message = "Você está recebendo este e-mail porque você ou outra pessoa solicitou uma redefinição de senha para sua conta\n\n"
			+ "Clique no link abaixo para redefinir sua senha:\n\n" + link;
			emailService.sendEmail(email, subject, message);
		}
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		User user = userRep.findByEmail(username);
		
		if(user == null) 
			throw new UsernameNotFoundException("Email not found");
		
		return user;
	}

	
	
}
