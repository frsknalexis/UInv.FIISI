package com.developer.UInvFISI.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.UInvFISI.entity.Escuela;
import com.developer.UInvFISI.service.EscuelaService;

@RestController
@RequestMapping("/api/escuela")
public class EscuelaRestController {

	@Autowired
	@Qualifier("escuelaService")
	private EscuelaService escuelaService;
	
	@GetMapping("/escuelas")
	public ResponseEntity<List<Escuela>> findAll() {
		
		try {
			
			List<Escuela> escuelas = escuelaService.findAll();
			if(escuelas.isEmpty()) {
				
				return new ResponseEntity<List<Escuela>>(HttpStatus.NO_CONTENT);
			}
			else {
				
				return new ResponseEntity<List<Escuela>>(escuelas, HttpStatus.OK);
			}
		}
		catch(Exception e) {
			
			return new ResponseEntity<List<Escuela>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/escuela/{escuelaId}")
	public ResponseEntity<Escuela> getByEscuelaId(@PathVariable(value="escuelaId") Integer escuelaId) {
		
		Escuela escuela = null;
		
		try {
			
			if(escuelaId != null && escuelaId > 0) {
				
				escuela = escuelaService.getByCodigo(escuelaId);
				if(escuela == null) {
					
					return new ResponseEntity<Escuela>(HttpStatus.NO_CONTENT);
				}
			}
			
			return new ResponseEntity<Escuela>(escuela, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<Escuela>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/save")
	public ResponseEntity<ResponseBaseOperacion> saveEscuela(@Valid @RequestBody Escuela escuela) {
		
		try {
			
			escuelaService.saveOrUpdate(escuela);
			ResponseBaseOperacion response = new ResponseBaseOperacion("created", escuela);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.CREATED);
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update/{escuelaId}")
	public ResponseEntity<ResponseBaseOperacion> updateEscuela(@PathVariable(value="escuelaId") Integer escuelaId,
			@Valid @RequestBody Escuela escuela) {
		
		Escuela escuelaOld = null;
		
		try {
			
			if(escuelaId != null && escuelaId > 0) {
				
				escuelaOld = escuelaService.getByCodigo(escuelaId);
				if(escuelaOld == null) {
					
					return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
				}
			}
			
			escuelaOld.setNombreEscuela(escuela.getNombreEscuela());
			escuelaOld.setDirectorEscuela(escuela.getDirectorEscuela());
			
			escuelaService.saveOrUpdate(escuelaOld);
			ResponseBaseOperacion response = new ResponseBaseOperacion("updated", escuelaOld);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/escuela/disabled/{escuelaId}")
	public ResponseEntity<ResponseBaseOperacion> disabledEscuela(@PathVariable(value="escuelaId") Integer escuelaId) {
		
		Escuela escuela = null;
		
		try {
			
			if(escuelaId != null && escuelaId > 0) {
				
				escuela = escuelaService.getByCodigo(escuelaId);
				if(escuela != null) {
					
					if(escuela.getHabilitado().equals(true)) {
						
						escuelaService.delete(escuelaId);
						ResponseBaseOperacion response = new ResponseBaseOperacion("success", escuela);
						return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
					}
				}
				else {
					
					return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
				}
			}
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
		
		return null;
	}
	
	@GetMapping("/escuela/enabled/{escuelaId}")
	public ResponseEntity<ResponseBaseOperacion> enabledEscuela(@PathVariable(value="escuelaId") Integer escuelaId) {
		
		Escuela escuela = null;
		
		try {
			
			if(escuelaId != null && escuelaId > 0) {
				
				escuela = escuelaService.getByCodigo(escuelaId);
				if(escuela != null) {
					
					if(escuela.getHabilitado().equals(false)) {
						
						escuelaService.enabled(escuelaId);
						ResponseBaseOperacion response = new ResponseBaseOperacion("success", escuela);
						return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
					}
				}
				else {
					
					return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
				}
			}
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
		
		return null;
	}
}
