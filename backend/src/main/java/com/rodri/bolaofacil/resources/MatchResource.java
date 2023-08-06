package com.rodri.bolaofacil.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

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

import com.rodri.bolaofacil.dto.MatchInsertDTO;
import com.rodri.bolaofacil.dto.MatchSampleDTO;
import com.rodri.bolaofacil.dto.MatchUpdateDTO;
import com.rodri.bolaofacil.services.MatchService;

@RestController
@RequestMapping(value = "/boloes/{sweepstakeId}/partidas")
public class MatchResource {

	@Autowired
	MatchService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<MatchUpdateDTO> findById(@PathVariable Long sweepstakeId, @PathVariable Long id)
	{
		return ResponseEntity.ok().body(service.findById(sweepstakeId,id));
	}
	
	@GetMapping()
	public ResponseEntity<List<MatchSampleDTO>> findAllBySweepstake(@PathVariable Long sweepstakeId)
	{
		return ResponseEntity.ok().body(service.findAllBySweepstake(sweepstakeId));
	}
	
	@PostMapping()
	public ResponseEntity<MatchInsertDTO> insert(@PathVariable Long sweepstakeId, @Valid @RequestBody MatchInsertDTO dto)
	{
		dto = service.insert(sweepstakeId, dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<MatchUpdateDTO> update(@PathVariable Long sweepstakeId, @PathVariable Long id, @Valid @RequestBody MatchUpdateDTO dto)
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
