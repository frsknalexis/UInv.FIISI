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
import com.developer.UInvFISI.service.AsignacionService;
import com.developer.UInvFISI.util.Constantes;

@RestController
@RequestMapping("/api/asignacioninvestigacion")
public class AsignacionRestController {

	@Autowired
	@Qualifier("asignacionService")
	private AsignacionService asignacionService;
	
	@GetMapping("/investigaciones")
	public ResponseEntity<List<Asignacion>> findAll() {
		
		try {
			
			List<Asignacion> investigaciones = asignacionService.findAll();
			if(investigaciones.isEmpty()) {
				
				return new ResponseEntity<List<Asignacion>>(HttpStatus.NO_CONTENT);
			}
			else {
				
				return new ResponseEntity<List<Asignacion>>(investigaciones, HttpStatus.OK);
			}
		}
		catch(Exception e) {
			
			return new ResponseEntity<List<Asignacion>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/investigacion/{asignacionId}")
	public ResponseEntity<Asignacion> getByAsignacionId(@PathVariable(value="asignacionId") Integer asignacionId) {
		
		Asignacion asignacion = null;
		
		try {
			
			if(asignacionId != null && asignacionId > 0) {
				
				asignacion = asignacionService.getByAsignacionId(asignacionId);
				if(asignacion == null) {
					
					return new ResponseEntity<Asignacion>(HttpStatus.NO_CONTENT);
				}
			}
			
			return new ResponseEntity<Asignacion>(asignacion, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<Asignacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/totalAsignacion")
	public ResponseEntity<Long> obtenerTotalRegistrosAsignacion() {
		
		try {
			
			Long totalAsignacion = asignacionService.obtenerTotalRegistrosAsignacion();
			return new ResponseEntity<Long>(totalAsignacion, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/save")
	public ResponseEntity<ResponseBaseOperacion> saveAsignacion(@Valid @RequestBody Asignacion asignacion) {
		
		try {
			
			asignacionService.saveOrUpdate(asignacion);
			ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.CREATED_MESSAGE, asignacion);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.CREATED);
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update/{asignacionId}")
	public ResponseEntity<ResponseBaseOperacion> updateAsignacion(@PathVariable(value="asignacionId") Integer asignacionId,
			@Valid @RequestBody Asignacion asignacion) {
		
		Asignacion asignacionOld = null;
		
		try {
			
			if(asignacionId != null && asignacionId > 0) {
				
				asignacionOld = asignacionService.getByAsignacionId(asignacionId);
				if(asignacionOld == null) {
					return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
				}
			}
			
			asignacionOld.setNombreInvestigacion(asignacion.getNombreInvestigacion());
			asignacionOld.setLineaInvestigacion(asignacion.getLineaInvestigacion());
			asignacionOld.setAnio(asignacion.getAnio());
			asignacionOld.setFechaInicio(asignacion.getFechaInicio());
			asignacionOld.setFechaFin(asignacion.getFechaFin());
			asignacionService.saveOrUpdate(asignacionOld);
			ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.UPDATED_MESSAGE, asignacionOld);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/asignacion/disabled/{asignacionId}")
	public ResponseEntity<ResponseBaseOperacion> disabledAsignacion(@PathVariable(value="asignacionId") Integer asignacionId) {
		
		Asignacion asignacion = null;
		
		try {
			
			if(asignacionId != null && asignacionId > 0) {
				
				asignacion = asignacionService.getByAsignacionId(asignacionId);
				if(asignacion != null) {
					
					if(asignacion.getHabilitado().equals(true)) {
						
						asignacionService.delete(asignacionId);
						ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.SUCCESS_MESSAGE, asignacion);
						return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
					}
				}
				else {
					
					return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
				}
			}
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
		}
		
		return null;
	}
	
	@GetMapping("/asignacion/enabled/{asignacionId}")
	public ResponseEntity<ResponseBaseOperacion> enabledAsignacion(@PathVariable(value="asignacionId") Integer asignacionId) {
		
		Asignacion asignacion = null;
		
		try {
			
			if(asignacionId != null && asignacionId > 0) {
				
				asignacion = asignacionService.getByAsignacionId(asignacionId);
				if(asignacion != null) {
					
					if(asignacion.getHabilitado().equals(false)) {
						
						asignacionService.enabled(asignacionId);
						ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.SUCCESS_MESSAGE, asignacion);
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
