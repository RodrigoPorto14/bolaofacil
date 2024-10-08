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

import com.rodri.bolaofacil.dto.TeamInsertDTO;
import com.rodri.bolaofacil.dto.TeamUpdateDTO;
import com.rodri.bolaofacil.services.TeamService;

@RestController
@RequestMapping()
public class TeamResource {

	@Autowired
	TeamService service;
	
	@GetMapping(value = "/times/{id}")
	public ResponseEntity<TeamInsertDTO> findById(@PathVariable Long id)
	{
		return ResponseEntity.ok().body(service.findById(id));
	}
	
	@GetMapping(value = "/boloes/{sweepstakeId}/times")
	public ResponseEntity<List<TeamUpdateDTO>> findAllBySweepstake(@PathVariable Long sweepstakeId)
	{
		return ResponseEntity.ok().body(service.findAllBySweepstake(sweepstakeId));
	}
	
	@PostMapping(value = "/times")
	public ResponseEntity<TeamInsertDTO> insert(@Valid @RequestBody TeamInsertDTO dto)
	{
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/times/{id}")
	public ResponseEntity<TeamUpdateDTO> update(@PathVariable Long id, @Valid @RequestBody TeamUpdateDTO dto)
	{
		return ResponseEntity.ok().body(service.update(id, dto));
	}
	
	@DeleteMapping(value = "/times/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id)
	{
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
//	@PostMapping(value = "/upload")
//	public ResponseEntity<UriDTO> uploadFile(@PathVariable Long sweepstakeId, @RequestParam("file") MultipartFile file)
//	{
//		UriDTO dto = service.uploadFile(sweepstakeId, file);
//		return ResponseEntity.ok().body(dto);
//	}
}
