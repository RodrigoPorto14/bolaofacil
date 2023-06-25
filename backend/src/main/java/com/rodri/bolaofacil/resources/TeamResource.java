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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rodri.bolaofacil.dto.TeamDTO;
import com.rodri.bolaofacil.dto.TeamSampleDTO;
import com.rodri.bolaofacil.dto.UriDTO;
import com.rodri.bolaofacil.services.TeamService;

@RestController
@RequestMapping(value = "/boloes/{sweepstakeId}/times")
public class TeamResource {

	@Autowired
	TeamService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<TeamDTO> findById(@PathVariable Long sweepstakeId, @PathVariable Long id)
	{
		return ResponseEntity.ok().body(service.findById(sweepstakeId,id));
	}
	
	@GetMapping()
	public ResponseEntity<List<TeamSampleDTO>> findAllBySweepstake(@PathVariable Long sweepstakeId)
	{
		return ResponseEntity.ok().body(service.findAllBySweepstake(sweepstakeId));
	}
	
	@PostMapping()
	public ResponseEntity<TeamDTO> insert(@PathVariable Long sweepstakeId, @RequestBody TeamDTO dto)
	{
		dto = service.insert(sweepstakeId, dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<TeamDTO> update(@PathVariable Long sweepstakeId,@PathVariable Long id, @RequestBody TeamDTO dto)
	{
		return ResponseEntity.ok().body(service.update(sweepstakeId, id, dto));
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long sweepstakeId, @PathVariable Long id)
	{
		service.delete(sweepstakeId,id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/upload")
	public ResponseEntity<UriDTO> uploadFile(@PathVariable Long sweepstakeId, @RequestParam("file") MultipartFile file)
	{
		UriDTO dto = service.uploadFile(sweepstakeId, file);
		return ResponseEntity.ok().body(dto);
	}
}
