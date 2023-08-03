package com.rodri.bolaofacil.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rodri.bolaofacil.dto.UserDTO;
import com.rodri.bolaofacil.dto.UserInsertDTO;
import com.rodri.bolaofacil.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@GetMapping
	public ResponseEntity<UserDTO> findAuthenticated()
	{
		return ResponseEntity.ok().body(service.findAuthenticated());
	}
	
	@GetMapping("/verify")
    public String verifyUser(@RequestParam("token") String token) 
	{
        return service.verifyUser(token);
    }
	
	@PostMapping
	public ResponseEntity<String> insert(@Valid @RequestBody UserInsertDTO insertDto)
	{
		//URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(insertDto.getId()).toUri();
		return ResponseEntity.ok().body(service.insert(insertDto));
	}
	
	@PostMapping(value = "/send-email")
	public ResponseEntity<Void> sendVerificationEmail(@RequestParam("url") String url, @RequestParam("token") String token)
	{
		service.sendVerificationEmail(url, token);
		return ResponseEntity.noContent().build();
	}

}
