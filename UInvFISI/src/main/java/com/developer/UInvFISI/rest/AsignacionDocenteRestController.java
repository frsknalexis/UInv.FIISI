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

import com.developer.UInvFISI.entity.Asignacion;
import com.developer.UInvFISI.entity.AsignacionDocente;
import com.developer.UInvFISI.service.AsignacionDocenteService;
import com.developer.UInvFISI.service.AsignacionService;
import com.developer.UInvFISI.util.Constantes;

@RestController
@RequestMapping("/api/asignaciondocente")
public class AsignacionDocenteRestController {

	@Autowired
	@Qualifier("asignacionDocenteService")
	private AsignacionDocenteService asignacionDocenteService;
	
	@Autowired
	@Qualifier("asignacionService")
	private AsignacionService asignacionService;
	
	@GetMapping("/asignacionesdocentes")
	public ResponseEntity<List<AsignacionDocente>> findAll() {
		
		try {
			
			List<AsignacionDocente> asignacionesdocentes = asignacionDocenteService.findAll();
			if(asignacionesdocentes.isEmpty()) {
				
				return new ResponseEntity<List<AsignacionDocente>>(HttpStatus.NO_CONTENT);
			}
			else {
				
				return new ResponseEntity<List<AsignacionDocente>>(asignacionesdocentes, HttpStatus.OK);
			}
		}
		catch(Exception e) {
			
			return new ResponseEntity<List<AsignacionDocente>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/asignaciondocente/{asignacionDetalleId}")
	public ResponseEntity<AsignacionDocente> getByAsignacionDetalleId(@PathVariable(value="asignacionDetalleId") Integer asignacionDetalleId) {
		
		AsignacionDocente asignacionDetalle = null;
		
		try {
			
			if(asignacionDetalleId != null && asignacionDetalleId > 0) {
				
				asignacionDetalle = asignacionDocenteService.getAsignacionDocenteById(asignacionDetalleId);
				if(asignacionDetalle == null) {
					
					return new ResponseEntity<AsignacionDocente>(HttpStatus.NO_CONTENT);
				}
			}
			
			return new ResponseEntity<AsignacionDocente>(asignacionDetalle, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<AsignacionDocente>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/asignaciondocentes/asignacion/{asignacionId}")
	public ResponseEntity<List<AsignacionDocente>> findAsignacionDocentesByAsignacionId(@PathVariable(value="asignacionId") Integer asignacionId) {
		
		try {
			
			List<AsignacionDocente> asignacionDocentes = asignacionDocenteService.findByAsignacionId(asignacionId);
			if(asignacionDocentes.isEmpty()) {
				
				return new ResponseEntity<List<AsignacionDocente>>(HttpStatus.NO_CONTENT);
			}
			else {
				
				return new ResponseEntity<List<AsignacionDocente>>(asignacionDocentes, HttpStatus.OK);
			}
		}
		catch(Exception e) {
			
			return new ResponseEntity<List<AsignacionDocente>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/asignaciondocenteshabilitados/asignacion/{asignacionId}")
	public ResponseEntity<List<AsignacionDocente>> findAsignacionDocentesByAsignacionIdAndHabilitado(@PathVariable(value="asignacionId") Integer asignacionId) {
		
		try {
			
			List<AsignacionDocente> asignacionDocentes = asignacionDocenteService.findByAsignacionIdAndHabilitado(asignacionId);
			if(asignacionDocentes.isEmpty()) {
				
				return new ResponseEntity<List<AsignacionDocente>>(HttpStatus.NO_CONTENT);
			}
			else {
				
				return ResponseEntity.ok()
						.body(asignacionDocentes);
			}
		}
		catch(Exception e) {
			
			return new ResponseEntity<List<AsignacionDocente>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/save/asignacion/{asignacionId}")
	public ResponseEntity<ResponseBaseOperacion> saveAsignacionDocente(@PathVariable(value="asignacionId") Integer asignacionId,
			@Valid @RequestBody AsignacionDocente asignacionDocente) {
		
		Asignacion asignacion = null;
		
		try {
			
			if(asignacionId != null && asignacionId > 0) {
				
				asignacion = asignacionService.getByAsignacionId(asignacionId);
				if(asignacion == null) {
					
					return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
				}
			}
			
			asignacionDocente.setAsignacion(asignacion);
			asignacionDocenteService.saveOrUpdate(asignacionDocente);
			ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.CREATED_MESSAGE, asignacionDocente);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.CREATED);
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update/asignacion/{asignacionId}/asignaciondocente/{asignacionDetalleId}")
	public ResponseEntity<ResponseBaseOperacion> updateAsignacionDocente(@PathVariable(value="asignacionId") Integer asignacionId,
			@PathVariable(value="asignacionDetalleId") Integer asignacionDetalleId, @Valid @RequestBody AsignacionDocente asignacionDocente) {
		
		Asignacion asignacion = null;
		AsignacionDocente asignacionDocenteOld = null;
		
		try {
			
			if(asignacionId != null && asignacionId > 0) {
				
				asignacion = asignacionService.getByAsignacionId(asignacionId);
				if(asignacionDetalleId != null && asignacionDetalleId > 0) {
					
					asignacionDocenteOld = asignacionDocenteService.getAsignacionDocenteById(asignacionDetalleId);
					if(asignacionDocenteOld == null) {
						
						return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
					}
				}
			}
			
			asignacionDocenteOld.setAsignacion(asignacion);
			asignacionDocenteOld.setInvestigador(asignacionDocente.getInvestigador());
			asignacionDocenteOld.setFacultad(asignacionDocente.getFacultad());
			asignacionDocenteOld.setCondicion(asignacionDocente.getCondicion());
			asignacionDocenteService.saveOrUpdate(asignacionDocenteOld);
			ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.CREATED_MESSAGE, asignacionDocenteOld);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/investigador/disabled/{asignacionDetalleId}")
	public ResponseEntity<ResponseBaseOperacion> disabledInvestigador(@PathVariable(value="asignacionDetalleId") Integer asignacionDetalleId) {
		
		AsignacionDocente asignacionDocente = null;
		
		try {
			
			if(asignacionDetalleId != null && asignacionDetalleId > 0) {
				
				asignacionDocente = asignacionDocenteService.getAsignacionDocenteById(asignacionDetalleId);
				if(asignacionDocente != null) {
					
					if(asignacionDocente.getHabilitado().equals(true)) {
						
						asignacionDocenteService.disabled(asignacionDetalleId);
						ResponseBaseOperacion response = new ResponseBaseOperacion("success", asignacionDocente);
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
	
	@GetMapping("/investigador/enabled/{asignacionDetalleId}")
	public ResponseEntity<ResponseBaseOperacion> enabledInvestigador(@PathVariable(value="asignacionDetalleId") Integer asignacionDetalleId) {
		
		AsignacionDocente asignacionDocente = null;
		
		try {
			
			if(asignacionDetalleId != null && asignacionDetalleId > 0) {
				
				asignacionDocente = asignacionDocenteService.getAsignacionDocenteById(asignacionDetalleId);
				if(asignacionDocente != null) {
					
					if(asignacionDocente.getHabilitado().equals(false)) {
						
						asignacionDocenteService.enabled(asignacionDetalleId);
						ResponseBaseOperacion response = new ResponseBaseOperacion("success", asignacionDocente);
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
