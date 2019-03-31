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

import com.developer.UInvFISI.entity.Asignatura;
import com.developer.UInvFISI.service.AsignaturaService;
import com.developer.UInvFISI.util.Constantes;

@RestController
@RequestMapping("/api/asignatura")
public class AsignaturaRestController {

	@Autowired
	@Qualifier("asignaturaService")
	private AsignaturaService asignaturaService;
	
	@GetMapping("/asignaturas")
	public ResponseEntity<List<Asignatura>> findAll() {
		
		try {
			
			List<Asignatura> asignaturas = asignaturaService.findAll();
			if(asignaturas.isEmpty()) {
				
				return new ResponseEntity<List<Asignatura>>(HttpStatus.NO_CONTENT);
			}
			else {
				
				return new ResponseEntity<List<Asignatura>>(asignaturas, HttpStatus.OK);
			}
		}
		catch(Exception e) {
			
			return new ResponseEntity<List<Asignatura>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/asignatura/{asignaturaId}")
	public ResponseEntity<Asignatura> getByAsignaturaId(@PathVariable(value="asignaturaId") Integer asignaturaId) {
		
		Asignatura asignatura = null;
		
		try {
			
			if(asignaturaId != null && asignaturaId > 0) {
				
				asignatura = asignaturaService.getByAsignaturaId(asignaturaId);
				if(asignatura == null) {
					
					return new ResponseEntity<Asignatura>(HttpStatus.NO_CONTENT);
				}
			}
			
			return new ResponseEntity<Asignatura>(asignatura, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<Asignatura>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/totalAsignatura")
	public ResponseEntity<Long> obtenerTotalAsignaturas() {
		
		try {
			
			Long totalAsignaturas = asignaturaService.obtenerTotalAsignaturas();
			return new ResponseEntity<Long>(totalAsignaturas, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/save")
	public ResponseEntity<ResponseBaseOperacion> saveAsignatura(@Valid @RequestBody Asignatura asignatura) {
		
		try {
			
			asignaturaService.saveOrUpdate(asignatura);
			ResponseBaseOperacion response = new ResponseBaseOperacion("created", asignatura);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.CREATED);
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update/{asignaturaId}")
	public ResponseEntity<ResponseBaseOperacion> updateAsignatura(@PathVariable(value="asignaturaId") Integer asignaturaId,
			@Valid @RequestBody Asignatura asignatura) {
		
		Asignatura asignaturaOld = null;
		
		try {
			
			if(asignaturaId != null && asignaturaId > 0) {
				
				asignaturaOld = asignaturaService.getByAsignaturaId(asignaturaId);
				if(asignaturaOld == null) {
					
					return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
				}
			}
			
			asignaturaOld.setNombreAsignatura(asignatura.getNombreAsignatura());
			asignaturaOld.setEscuela(asignatura.getEscuela());
			asignaturaOld.setPeriodo(asignatura.getPeriodo());
			asignaturaOld.setCiclo(asignatura.getCiclo());
			asignaturaOld.setNombreDocente(asignatura.getNombreDocente());
			
			asignaturaService.saveOrUpdate(asignaturaOld);
			ResponseBaseOperacion response = new ResponseBaseOperacion("updated", asignaturaOld);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/asignatura/disabled/{asignaturaId}")
	public ResponseEntity<ResponseBaseOperacion> disabledAsignatura(@PathVariable(value="asignaturaId") Integer asignaturaId) {
		
		Asignatura asignatura = null;
		
		try {
			
			if(asignaturaId != null && asignaturaId.intValue() > 0) {
				asignatura = asignaturaService.getByAsignaturaId(asignaturaId);
				if(asignatura != null) {
					if(asignatura.getHabilitado().equals(true)) {
						asignaturaService.delete(asignaturaId);
						ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.SUCCESS_MESSAGE, asignatura);
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
	
	@GetMapping("/asignatura/enabled/{asignaturaId}")
	public ResponseEntity<ResponseBaseOperacion> enabledAsignatura(@PathVariable(value="asignaturaId") Integer asignaturaId) {
		
		Asignatura asignatura = null;
		
		try {
			
			if(asignaturaId != null && asignaturaId.intValue() > 0) {
				asignatura = asignaturaService.getByAsignaturaId(asignaturaId);
				if(asignatura != null) {
					if(asignatura.getHabilitado().equals(false)) {
						asignaturaService.enabled(asignaturaId);
						ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.SUCCESS_MESSAGE, asignatura);
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
