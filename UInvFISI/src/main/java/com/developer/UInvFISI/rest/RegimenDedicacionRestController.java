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

import com.developer.UInvFISI.entity.RegimenDedicacion;
import com.developer.UInvFISI.service.RegimenDedicacionService;

@RestController
@RequestMapping("/api/regimen")
public class RegimenDedicacionRestController {

	@Autowired
	@Qualifier("regimenDedicacionService")
	private RegimenDedicacionService regimenDedicacionService;
	
	@GetMapping("/regimenes")
	public ResponseEntity<List<RegimenDedicacion>> findAll() {
		
		List<RegimenDedicacion> regimenes = regimenDedicacionService.findAll();
		
		if(regimenes.isEmpty()) {
			
			return new ResponseEntity<List<RegimenDedicacion>>(HttpStatus.NO_CONTENT);
		}
		
		else {
			
			return new ResponseEntity<List<RegimenDedicacion>>(regimenes, HttpStatus.OK);
		}
	}
	
	@GetMapping("/regimen/{regimenDedicacionId}")
	public ResponseEntity<RegimenDedicacion> getByRegimenDedicacionId(@PathVariable(value="regimenDedicacionId") Integer regimenDedicacionId) {
		
		RegimenDedicacion regimenDedicacion = null;
		
		if(regimenDedicacionId != null && regimenDedicacionId > 0) {
			
			regimenDedicacion = regimenDedicacionService.getByRegimenDedicacionId(regimenDedicacionId);
			
			if(regimenDedicacion == null) {
				
				return new ResponseEntity<RegimenDedicacion>(HttpStatus.NO_CONTENT);
			}
		}
		
		return new ResponseEntity<RegimenDedicacion>(regimenDedicacion, HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<ResponseBaseOperacion> saveRegimenDedicacion(@Valid @RequestBody RegimenDedicacion regimenDedicacion) {
		
		regimenDedicacionService.saveOrUpdate(regimenDedicacion);
		ResponseBaseOperacion response = new ResponseBaseOperacion("created", regimenDedicacion);
		return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{regimenDedicacionId}")
	public ResponseEntity<ResponseBaseOperacion> updateRegimenDedicacion(@PathVariable(value="regimenDedicacionId") Integer regimenDedicacionId,
			@Valid @RequestBody RegimenDedicacion regimenDedicacion) {
		
		RegimenDedicacion regimenDedicacionOld = null;
		
		if(regimenDedicacionId != null && regimenDedicacionId > 0) {
			
			regimenDedicacionOld = regimenDedicacionService.getByRegimenDedicacionId(regimenDedicacionId);
			
			if(regimenDedicacionOld == null) {
				
				return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
			}
		}
		
		regimenDedicacionOld.setNombreRegimen(regimenDedicacion.getNombreRegimen());
		regimenDedicacionService.saveOrUpdate(regimenDedicacionOld);
		ResponseBaseOperacion response = new ResponseBaseOperacion("updated", regimenDedicacionOld);
		return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
	}
	
	@GetMapping("/regimen/disabled/{regimenDedicacionId}")
	public ResponseEntity<Void> disabledRegimenDedicacion(@PathVariable(value="regimenDedicacionId") Integer regimenDedicacionId) {
		
		RegimenDedicacion regimenDedicacion = null;
		
		if(regimenDedicacionId != null && regimenDedicacionId > 0) {
			
			regimenDedicacion = regimenDedicacionService.getByRegimenDedicacionId(regimenDedicacionId);
			
			if(regimenDedicacion != null) {
				
				if(regimenDedicacion.getHabilitado().equals(true)) {
					
					regimenDedicacionService.delete(regimenDedicacionId);
					return new ResponseEntity<Void>(HttpStatus.OK);
				}
				
				else {
					
					return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
				}
			}
			
			else {
				
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		}
		
		return null;
	}
	
	@GetMapping("/regimen/enabled/{regimenDedicacionId}")
	public ResponseEntity<Void> enabledRegimenDedicacion(@PathVariable(value="regimenDedicacionId") Integer regimenDedicacionId) {
		
		RegimenDedicacion regimenDedicacion = null;
		
		if(regimenDedicacionId != null && regimenDedicacionId > 0) {
			
			regimenDedicacion = regimenDedicacionService.getByRegimenDedicacionId(regimenDedicacionId);
			
			if(regimenDedicacion != null) {
				
				if(regimenDedicacion.getHabilitado().equals(false)) {
					
					regimenDedicacionService.enabled(regimenDedicacionId);
					return new ResponseEntity<Void>(HttpStatus.OK);
				}
				
				else {
					
					return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
				}
			}
			
			else {
				
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		}
		
		return null;
	}
}
