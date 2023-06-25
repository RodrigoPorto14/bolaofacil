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

import com.rodri.bolaofacil.dto.RuleDTO;
import com.rodri.bolaofacil.dto.RuleSampleDTO;
import com.rodri.bolaofacil.services.RuleService;

@RestController
@RequestMapping(value = "/boloes/{sweepstakeId}/regras")
public class RuleResource {

	@Autowired
	RuleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<RuleDTO> findById(@PathVariable Long sweepstakeId, @PathVariable Long id)
	{
		return ResponseEntity.ok().body(service.findById(sweepstakeId,id));
	}
	
	@GetMapping()
	public ResponseEntity<List<RuleSampleDTO>> findAllBySweepstake(@PathVariable Long sweepstakeId)
	{
		return ResponseEntity.ok().body(service.findAllBySweepstake(sweepstakeId));
	}
	
	@PostMapping()
	public ResponseEntity<RuleDTO> insert(@PathVariable Long sweepstakeId, @RequestBody RuleDTO dto)
	{
		dto = service.insert(sweepstakeId, dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<RuleDTO> update(@PathVariable Long sweepstakeId,@PathVariable Long id, @RequestBody RuleDTO dto)
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
