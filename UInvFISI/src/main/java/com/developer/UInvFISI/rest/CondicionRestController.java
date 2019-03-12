package com.developer.UInvFISI.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.UInvFISI.entity.Condicion;
import com.developer.UInvFISI.service.CondicionService;

@RestController
@RequestMapping("/api/condicion")
public class CondicionRestController {

	@Autowired
	private CondicionService condicionService;
	
	@GetMapping("/condiciones")
	public ResponseEntity<List<Condicion>> findAll() {
		
		List<Condicion> condiciones = condicionService.findAll();
		
		if(condiciones.isEmpty()) {
			
			return new ResponseEntity<List<Condicion>>(HttpStatus.NO_CONTENT);
		}
		
		else {
			
			return new ResponseEntity<List<Condicion>>(condiciones, HttpStatus.OK);
		}
	}
	
	@GetMapping("/condicion/{condicionId}")
	public ResponseEntity<Condicion> getByCondicionId(@PathVariable(value="condicionId") Integer condicionId) {
		
		Condicion condicion = null;
		
		if(condicionId != null && condicionId > 0) {
			
			condicion = condicionService.getByCondicionId(condicionId);
			
			if(condicion == null) {
				
				return new ResponseEntity<Condicion>(HttpStatus.NO_CONTENT);
			}
		}
		
		return new ResponseEntity<Condicion>(condicion, HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<ResponseBaseOperacion> saveCondicion(@Valid @RequestBody Condicion condicion) {
		
		condicionService.saveOrUpdate(condicion);
		ResponseBaseOperacion response = new ResponseBaseOperacion("created", condicion);
		return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{condicionId}")
	public ResponseEntity<ResponseBaseOperacion> updateCondicion(@PathVariable(value="condicionId") Integer condicionId,
			@Valid @RequestBody Condicion condicion) {
		
		Condicion condicionOld = null;
		
		if(condicionId != null && condicionId > 0) {
			
			condicionOld = condicionService.getByCondicionId(condicionId);
			
			if(condicionOld == null) {
				
				return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
			}
		}
		
		condicionOld.setNombreCondicion(condicion.getNombreCondicion());
		condicionService.saveOrUpdate(condicionOld);
		ResponseBaseOperacion response = new ResponseBaseOperacion("updated", condicionOld);
		return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
	}
	
	@GetMapping("/condicion/disabled/{condicionId}")
	public ResponseEntity<Void> disabledCondicionDocente(@PathVariable(value="condicionId") Integer condicionId) {
		
		Condicion condicion = null;
		
		if(condicionId != null && condicionId > 0) {
			
			condicion = condicionService.getByCondicionId(condicionId);
			
			if(condicion != null) {
				
				if(condicion.getHabilitado().equals(true)) {
					
					condicionService.delete(condicionId);
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
	
	@GetMapping("/condicion/enabled/{condicionId}")
	public ResponseEntity<Void> enabledCondicionDocente(@PathVariable(value="condicionId") Integer condicionId) {
		
		Condicion condicion = null;
		
		if(condicionId != null && condicionId > 0) {
			
			condicion = condicionService.getByCondicionId(condicionId);
			
			if(condicion != null) {
				
				if(condicion.getHabilitado().equals(false)) {
					
					condicionService.enabled(condicionId);
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
