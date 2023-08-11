package com.rodri.bolaofacil.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rodri.bolaofacil.dto.UserDTO;
import com.rodri.bolaofacil.dto.UserInsertDTO;
import com.rodri.bolaofacil.dto.UserPasswordUpdateDTO;
import com.rodri.bolaofacil.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	/*****************************************************************
    					   ENCONTRAR USUARIO                           
	*****************************************************************/
	
	@GetMapping
	public ResponseEntity<UserDTO> findAuthenticated()
	{
		return ResponseEntity.ok().body(service.findAuthenticated());
	}
	
	/*****************************************************************
    					    ATUALIZAR USUARIO                             
	*****************************************************************/
	
	@PutMapping
	public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO dto)
	{
		return ResponseEntity.ok().body(service.update(dto));
	}
	
	@PutMapping(value = "/password")
	public ResponseEntity<Void> updatePassword(@Valid @RequestBody UserPasswordUpdateDTO dto)
	{
		service.updatePassword(dto);
		return ResponseEntity.noContent().build();
	}
	
	/*****************************************************************
    			CADASTRO DE USUARIO EM DUAS ETAPAS
	 *****************************************************************/
	
	@PostMapping
	public ResponseEntity<String> insert(@Valid @RequestBody UserInsertDTO insertDto)
	{
		//URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(insertDto.getId()).toUri();
		return ResponseEntity.ok().body(service.insert(insertDto));
	}
	
	@GetMapping("/verify")
    public String verifyUser(@RequestParam("token") String token) 
	{
        return service.verifyUser(token);
    }
	
	/*****************************************************************
	 					 RECUPERAÇÃO DE SENHA
	 *****************************************************************/
	
	@PostMapping(value = "/password-recovery")
	public ResponseEntity<String> passwordRecovery(@RequestParam("email") String email)
	{
		return ResponseEntity.ok().body(service.passwordRecovery(email));
	}
	
	@PutMapping(value = "/password-reset")
	public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword)
	{
		return ResponseEntity.ok().body(service.resetPassword(token, newPassword));
	}
	
	@PostMapping(value = "/validate-token")
	public ResponseEntity<String> validateRecoveryToken(@RequestParam("token") String token)
	{
		return ResponseEntity.ok().body(service.validateRecoveryToken(token));
	}
	
	/*****************************************************************
							  ENVIAR EMAIL
	 *****************************************************************/
	
	@PostMapping(value = "/send-email")
	public ResponseEntity<Void> sendEmail(@RequestParam("url") String url, 
										  @RequestParam("token") String token, 
										  @RequestParam("type") String type)
	{
		service.sendEmail(url, token, type);
		return ResponseEntity.noContent().build();
	}

}
