package com.rodri.bolaofacil.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rodri.bolaofacil.dto.SweepstakeDTO;
import com.rodri.bolaofacil.services.SweepstakeService;

@RestController
@RequestMapping(value = "/boloes")
public class SweepstakeResource {

	@Autowired
	SweepstakeService service;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<SweepstakeDTO> findById(@PathVariable Long id)
	{
		return ResponseEntity.ok().body(service.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<SweepstakeDTO>> findAllByAuthenticated()
	{
		return ResponseEntity.ok().body(service.findAllByAuthenticated());
	}
	
	@GetMapping(value = "/busca")
	public ResponseEntity<List<SweepstakeDTO>> findByName(@RequestParam(value = "name", defaultValue = "") String name)
	{
		return ResponseEntity.ok().body(service.findByName(name));
	}
	
	@PostMapping
	public ResponseEntity<SweepstakeDTO> insert(@Valid @RequestBody SweepstakeDTO dto)
	{
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<SweepstakeDTO> update(@PathVariable Long id, @Valid @RequestBody SweepstakeDTO dto)
	{
		return ResponseEntity.ok().body(service.update(id, dto));
	}
	
	
}
