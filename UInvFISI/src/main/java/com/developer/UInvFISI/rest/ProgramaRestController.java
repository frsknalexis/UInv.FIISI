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

import com.developer.UInvFISI.entity.Programa;
import com.developer.UInvFISI.service.ProgramaService;

@RestController
@RequestMapping("/api/programa")
public class ProgramaRestController {

	@Autowired
	@Qualifier("programaService")
	private ProgramaService programaService;
	
	@GetMapping("/programas")
	public ResponseEntity<List<Programa>> findAll() {
		
		List<Programa> programas = programaService.findAll();
		
		if(programas.isEmpty()) {
			
			return new ResponseEntity<List<Programa>>(HttpStatus.NO_CONTENT);
		}
		
		else {
			
			return new ResponseEntity<List<Programa>>(programas, HttpStatus.OK);
		}
	}
	
	@GetMapping("/programa/{programaId}")
	public ResponseEntity<Programa> getByProgramaId(@PathVariable(value="programaId") Integer programaId) {
		
		Programa programa = null;
		
		if(programaId != null && programaId > 0) {
			
			programa = programaService.getByProgramaId(programaId);
			
			if(programa == null) {
				
				return new ResponseEntity<Programa>(HttpStatus.NO_CONTENT);
			}
		}
		
		return new ResponseEntity<Programa>(programa, HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<ResponseBaseOperacion> savePrograma(@Valid @RequestBody Programa programa) {
		
		programaService.saveOrUpdate(programa);
		ResponseBaseOperacion response = new ResponseBaseOperacion("created", programa);
		return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{programaId}")
	public ResponseEntity<ResponseBaseOperacion> updatePrograma(@PathVariable(value="programaId") Integer programaId,
			@RequestBody Programa programa) {
		
		Programa programaOld = null;
		
		if(programaId != null && programaId > 0) {
			
			programaOld = programaService.getByProgramaId(programaId);
			
			if(programaOld == null) {
				
				return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
			}
		}
		
		programaOld.setNombrePrograma(programa.getNombrePrograma());
		programaService.saveOrUpdate(programaOld);
		ResponseBaseOperacion response = new ResponseBaseOperacion("updated", programaOld);
		return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
	}
	
	@GetMapping("/programa/disabled/{programaId}")
	public ResponseEntity<ResponseBaseOperacion> disabledPrograma(@PathVariable(value="programaId") Integer programaId) {
		
		Programa programa = null;
		
		if(programaId != null && programaId > 0) {
			
			programa = programaService.getByProgramaId(programaId);
			
			if(programa != null) {
				
				if(programa.getHabilitado().equals(true)) {
					
					programaService.delete(programaId);
					ResponseBaseOperacion response = new ResponseBaseOperacion("success", programa);
					return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
				}
				
				else {
					
					return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
				}
			}
			
			else {
				
				return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
			}
		}
		
		return null;
	}
	
	@GetMapping("/programa/enabled/{programaId}")
	public ResponseEntity<ResponseBaseOperacion> enabledPrograma(@PathVariable(value="programaId") Integer programaId) {
		
		Programa programa = null;
		
		if(programaId != null && programaId > 0) {
			
			programa = programaService.getByProgramaId(programaId);
			
			if(programa != null) {
				
				if(programa.getHabilitado().equals(false)) {
					
					programaService.enabled(programaId);
					ResponseBaseOperacion response = new ResponseBaseOperacion("success", programa);
					return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
				}
				
				else {
					
					return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
				}
			}
			
			else {
				
				return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
			}
		}
		
		return null;
	}
}
