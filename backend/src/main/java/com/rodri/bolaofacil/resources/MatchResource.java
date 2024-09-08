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
@RequestMapping()
public class MatchResource {

	@Autowired
	MatchService service;
	
	@GetMapping(value = "/partidas/{id}")
	public ResponseEntity<MatchUpdateDTO> findById(@PathVariable Long id)
	{
		return ResponseEntity.ok().body(service.findById(id));
	}
	
	@GetMapping(value = "/boloes/{sweepstakeId}/partidas")
	public ResponseEntity<List<MatchSampleDTO>> findAllBySweepstake(@PathVariable Long sweepstakeId)
	{
		return ResponseEntity.ok().body(service.findAllBySweepstake(sweepstakeId));
	}
	
	@PostMapping(value = "/partidas")
	public ResponseEntity<MatchInsertDTO> insert(@Valid @RequestBody MatchInsertDTO dto)
	{
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/partidas/{id}")
	public ResponseEntity<MatchUpdateDTO> update(@PathVariable Long id, @Valid @RequestBody MatchUpdateDTO dto)
	{
		return ResponseEntity.ok().body(service.update(id, dto));
	}
	
	@DeleteMapping(value = "/partidas/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id)
	{
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
