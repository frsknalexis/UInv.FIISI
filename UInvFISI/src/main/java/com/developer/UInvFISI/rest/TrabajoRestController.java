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

import com.developer.UInvFISI.entity.Trabajo;
import com.developer.UInvFISI.service.TrabajoService;
import com.developer.UInvFISI.util.Constantes;

@RestController
@RequestMapping("/api/trabajo")
public class TrabajoRestController {

	@Autowired
	@Qualifier("trabajoService")
	private TrabajoService trabajoService;
	
	@GetMapping("/trabajos")
	public ResponseEntity<List<Trabajo>> findAll() {
		
		try {
			
			List<Trabajo> trabajos = trabajoService.findAll();
			if(trabajos.isEmpty()) {
				
				return new ResponseEntity<List<Trabajo>>(HttpStatus.NO_CONTENT);
			}
			else {
				
				return ResponseEntity.ok()
						.body(trabajos);
			}
			
		}
		catch(Exception e) {
			
			return new ResponseEntity<List<Trabajo>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/trabajo/{trabajoId}")
	public ResponseEntity<Trabajo> getByTrabajoId(@PathVariable(value="trabajoId") Integer trabajoId) {
		
		Trabajo trabajo = null;
		
		try {
			
			if(trabajoId != null && trabajoId > 0) {
				
				trabajo = trabajoService.getByTrabajoId(trabajoId);
				if(trabajo == null) {
					
					return new ResponseEntity<Trabajo>(HttpStatus.NO_CONTENT);
				}
			}
			
			return new ResponseEntity<Trabajo>(trabajo, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<Trabajo>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/totalTrabajos")
	public ResponseEntity<Long> obtenerTotalTrabajos() {
		
		try {
			
			Long totalTrabajos = trabajoService.obtenerTotalTrabajos();
			return new ResponseEntity<Long>(totalTrabajos, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/save")
	public ResponseEntity<ResponseBaseOperacion> saveTrabajo(@Valid @RequestBody Trabajo trabajo) {
		
		try {
			
			trabajoService.saveOrUpdate(trabajo);
			ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.CREATED_MESSAGE, trabajo);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.CREATED);
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update/{trabajoId}")
	public ResponseEntity<ResponseBaseOperacion> updateTrabajo(@PathVariable(value="trabajoId") Integer trabajoId, @Valid @RequestBody Trabajo trabajo) {
		
		Trabajo trabajoOld = null;
		
		try {
			
			if(trabajoId != null && trabajoId > 0) {
				
				trabajoOld = trabajoService.getByTrabajoId(trabajoId);
				if(trabajoOld == null) {
					
					return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
				}
			}
			
			trabajoOld.setNombre(trabajo.getNombre());
			trabajoOld.setEscuela(trabajo.getEscuela());
			trabajoOld.setAnio(trabajo.getAnio());
			trabajoOld.setTipoTrabajo(trabajo.getTipoTrabajo());
			trabajoOld.setDenominacion(trabajo.getDenominacion());
			trabajoOld.setFechaSustentacion(trabajo.getFechaSustentacion());
			trabajoOld.setCitaTrabajo(trabajo.getCitaTrabajo());
			trabajoOld.setGradoAcademico(trabajo.getGradoAcademico());
			trabajoOld.setCantidadHojas(trabajo.getCantidadHojas());
			trabajoOld.setAreaConocimiento(trabajo.getAreaConocimiento());
			trabajoService.saveOrUpdate(trabajoOld);
			ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.UPDATED_MESSAGE,trabajoOld);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/trabajo/disabled/{trabajoId}")
	public ResponseEntity<ResponseBaseOperacion> disabledTrabajo(@PathVariable(value="trabajoId") Integer trabajoId) {
		
		Trabajo trabajo = null;
		
		try {
			
			if(trabajoId != null && trabajoId > 0) {
				
				trabajo = trabajoService.getByTrabajoId(trabajoId);
				if(trabajo != null) {
					
					if(trabajo.getHabilitado().equals(true)) {
						
						trabajoService.delete(trabajoId);
						ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.SUCCESS_MESSAGE, trabajo);
						return ResponseEntity.ok()
								.body(response);
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
	
	@GetMapping("/trabajo/enabled/{trabajoId}")
	public ResponseEntity<ResponseBaseOperacion> enabledTrabajo(@PathVariable(value="trabajoId") Integer trabajoId) {
		
		Trabajo trabajo = null;
		
		try {
			
			if(trabajoId != null && trabajoId > 0) {
				
				trabajo = trabajoService.getByTrabajoId(trabajoId);
				if(trabajo != null) {
					
					if(trabajo.getHabilitado().equals(false)) {
						
						trabajoService.enabled(trabajoId);
						ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.SUCCESS_MESSAGE, trabajo);
						return ResponseEntity.ok()
								.body(response);
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
