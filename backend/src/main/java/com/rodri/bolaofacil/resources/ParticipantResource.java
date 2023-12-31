package com.rodri.bolaofacil.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rodri.bolaofacil.dto.ParticipantDTO;
import com.rodri.bolaofacil.dto.ParticipantSampleDTO;
import com.rodri.bolaofacil.dto.ParticipantUpdateDTO;
import com.rodri.bolaofacil.services.ParticipantService;

@RestController
@RequestMapping(value = "/boloes/{sweepstakeId}/participantes")
public class ParticipantResource {
	
	@Autowired
	ParticipantService service;
	
	
	@GetMapping(value = "/authenticated")
	public ResponseEntity<ParticipantDTO> findAuthenticatedBySweepstake(@PathVariable Long sweepstakeId)
	{
		return ResponseEntity.ok().body(service.findAuthenticatedBySweepstake(sweepstakeId));
	}
	
	@GetMapping()
	public ResponseEntity<List<ParticipantSampleDTO>> findAllBySweepstake(@PathVariable Long sweepstakeId)
	{
		return ResponseEntity.ok().body(service.findAllBySweepstake(sweepstakeId));
	}
	
	@PostMapping()
	public ResponseEntity<ParticipantSampleDTO> insertAuthenticated(@PathVariable Long sweepstakeId)
	{
		ParticipantSampleDTO dto = service.insertAuthenticated(sweepstakeId);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getUserId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PostMapping(value = "/{userId}")
	public ResponseEntity<ParticipantSampleDTO> insert(@PathVariable Long sweepstakeId, @PathVariable Long userId)
	{
		ParticipantSampleDTO dto = service.insert(sweepstakeId, userId);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getUserId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ParticipantSampleDTO> update(@PathVariable Long sweepstakeId, @PathVariable Long id, @RequestBody ParticipantUpdateDTO dto)
	{
		return ResponseEntity.ok().body(service.update(sweepstakeId, id, dto));
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long sweepstakeId, @PathVariable Long id)
	{
		service.delete(sweepstakeId,id);
		return ResponseEntity.noContent().build();
	}
}
