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

import com.developer.UInvFISI.entity.Facultad;
import com.developer.UInvFISI.service.FacultadService;

@RestController
@RequestMapping("/api/facultad")
public class FacultadRestController {

	@Autowired
	@Qualifier("facultadService")
	private FacultadService facultadService;
	
	@GetMapping("/facultades")
	public ResponseEntity<List<Facultad>> findAll() {
		
		List<Facultad> documentos = facultadService.findAll();
		
		if(documentos.isEmpty()) {
			
			return new ResponseEntity<List<Facultad>>(HttpStatus.NO_CONTENT);
		}
		
		else {
			
			return new ResponseEntity<List<Facultad>>(documentos, HttpStatus.OK);
		}
	}
	
	@GetMapping("/facultad/{facultadId}")
	public ResponseEntity<Facultad> getByFacultadId(@PathVariable(value="facultadId") Integer facultadId) {
		
		Facultad facultad = null;
		
		if(facultadId != null && facultadId > 0) {
			
			facultad = facultadService.getByCodigo(facultadId);
			
			if(facultad == null) {
				
				return new ResponseEntity<Facultad>(HttpStatus.NO_CONTENT);
			}
		}
		
		return new ResponseEntity<Facultad>(facultad, HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<ResponseBaseOperacion> saveFacultad(@Valid @RequestBody Facultad facultad) {
		
		facultadService.saveOrUpdate(facultad);
		ResponseBaseOperacion response = new ResponseBaseOperacion("created", facultad);
		return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{facultadId}")
	public ResponseEntity<ResponseBaseOperacion> updateFacultad(@PathVariable(value="facultadId") Integer facultadId,
			@RequestBody Facultad facultad) {
		
		Facultad facultadOld = null;
		
		if(facultadId != null && facultadId > 0) {
			
			facultadOld = facultadService.getByCodigo(facultadId);
			
			if(facultadOld == null) {
				
				return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
			}
		}
		
		facultadOld.setAbreviaturaFacultad(facultad.getAbreviaturaFacultad());
		facultadOld.setNombreFacultad(facultad.getNombreFacultad());
		facultadService.saveOrUpdate(facultadOld);
		ResponseBaseOperacion response = new ResponseBaseOperacion("updated", facultadOld);
		return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
	}
	
	@GetMapping("/facultad/disabled/{facultadId}")
	public ResponseEntity<ResponseBaseOperacion> disabledFacultad(@PathVariable(value="facultadId") Integer facultadId) {
		
		Facultad facultad = null;
		
		if(facultadId != null && facultadId > 0) {
			
			facultad = facultadService.getByCodigo(facultadId);
			
			if(facultad != null) {
				
				if(facultad.getHabilitado().equals(true)) {
					
					facultadService.delete(facultadId);
					ResponseBaseOperacion response = new ResponseBaseOperacion("success", facultad);
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
	
	@GetMapping("/facultad/enabled/{facultadId}")
	public ResponseEntity<ResponseBaseOperacion> enabledFacultad(@PathVariable(value="facultadId") Integer facultadId) {
		
		Facultad facultad = null;
		
		if(facultadId != null && facultadId > 0) {
			
			facultad = facultadService.getByCodigo(facultadId);
			
			if(facultad != null) {
				
				if(facultad.getHabilitado().equals(false)) {
					
					facultadService.enabled(facultadId);
					ResponseBaseOperacion response = new ResponseBaseOperacion("success", facultad);
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
