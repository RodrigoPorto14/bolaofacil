package com.rodri.bolaofacil.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rodri.bolaofacil.dto.RequestDTO;
import com.rodri.bolaofacil.services.RequestService;

@RestController
@RequestMapping(value = "/boloes/{sweepstakeId}/requests")
public class RequestResource {
	
	@Autowired
	RequestService service;
	
	@GetMapping
	public ResponseEntity<List<RequestDTO>> findAllBySweepstake(@PathVariable Long sweepstakeId)
	{
		return ResponseEntity.ok().body(service.findAllBySweepstake(sweepstakeId));
	}
	
	@PostMapping
	public ResponseEntity<RequestDTO> insert(@PathVariable Long sweepstakeId)
	{
		RequestDTO dto = service.insert(sweepstakeId);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getUserId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@DeleteMapping(value = "/{userId}")
	public ResponseEntity<Void> delete(@PathVariable Long sweepstakeId, @PathVariable Long userId)
	{
		service.delete(sweepstakeId,userId);
		return ResponseEntity.noContent().build();
	}
	
	

}
