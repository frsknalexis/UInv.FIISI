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

import com.developer.UInvFISI.entity.Docente;
import com.developer.UInvFISI.service.DocenteService;

@RestController
@RequestMapping("/api/docente")
public class DocenteRestController {

	@Autowired
	@Qualifier("docenteService")
	private DocenteService docenteService;
	
	@GetMapping("/docentes")
	public ResponseEntity<List<Docente>> findAll() {
		
		try {
			
			List<Docente> docentes = docenteService.findAll();
			
			if(docentes.isEmpty()) {
				
				return new ResponseEntity<List<Docente>>(HttpStatus.NO_CONTENT);
			}
			
			else {
				
				return new ResponseEntity<List<Docente>>(docentes, HttpStatus.OK);
			}
		}
		catch(Exception e) {
			
			return new ResponseEntity<List<Docente>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/docentes/autocomplete/{termino}")
	public ResponseEntity<List<Docente>> cargarDocentesByName(@PathVariable(value="termino") String termino) {
		
		try {
			
			if((termino.matches("^[a-zA-Z]+$"))) {
				
				List<Docente> docentes = docenteService.findByNombresDocente(termino);
				if(docentes.isEmpty()) {
					return new ResponseEntity<List<Docente>>(HttpStatus.NO_CONTENT);
				}
				
				else {
					return new ResponseEntity<List<Docente>>(docentes, HttpStatus.OK);
				}
			}
			else {
				return new ResponseEntity<List<Docente>>(HttpStatus.NO_CONTENT);
			}
		}
		catch(Exception e) {
			return new ResponseEntity<List<Docente>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/docente/{docenteId}")
	public ResponseEntity<Docente> getByDocenteId(@PathVariable(value="docenteId") Integer docenteId) {
		
		Docente docente = null;
		
		try {
			
			if(docenteId != null && docenteId > 0) {
				
				docente = docenteService.getByDocenteId(docenteId);
				if(docente == null) {
					
					return new ResponseEntity<Docente>(HttpStatus.NO_CONTENT);
				}
			}
			
			return new ResponseEntity<Docente>(docente, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<Docente>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/contadorDocentes")
	public ResponseEntity<Long> obtenertotalDocentes() {
		
		try {
			
			Long totalDocentes = docenteService.obtenerTotalRegistrosDocentes();
			return new ResponseEntity<Long>(totalDocentes, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/save")
	public ResponseEntity<ResponseBaseOperacion> saveDocente(@Valid @RequestBody Docente docente) {
		
		try {
			
			docenteService.saveOrUpdate(docente);
			ResponseBaseOperacion response = new ResponseBaseOperacion("created", docente);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.CREATED);
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update/{docenteId}")
	public ResponseEntity<ResponseBaseOperacion> updateDocente(@PathVariable(value="docenteId") Integer docenteId,
			@Valid @RequestBody Docente docente) {
		
		Docente docenteOld = null;
		
		try {
			
			if(docenteId != null && docenteId > 0) {
				
				docenteOld = docenteService.getByDocenteId(docenteId);
			}
			
			docenteOld.setNombresDocente(docente.getNombresDocente());
			docenteOld.setApellidosDocente(docente.getApellidosDocente());
			docenteOld.setCategoriaDocente(docente.getCategoriaDocente());
			docenteOld.setDocumento(docente.getDocumento());
			docenteOld.setNroDocumento(docente.getNroDocumento());
			docenteOld.setRegimenDedicacion(docente.getRegimenDedicacion());
			docenteOld.setDinaDatosAcademicos(docente.getDinaDatosAcademicos());
			docenteOld.setDinaProyectosInvestigacion(docente.getDinaProyectosInvestigacion());
			docenteOld.setCodigoOrcid(docente.getCodigoOrcid());
			docenteOld.setPublicacionesOrcid(docente.getPublicacionesOrcid());
			
			docenteService.saveOrUpdate(docenteOld);
			ResponseBaseOperacion response = new ResponseBaseOperacion("updated", docenteOld);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/docente/disabled/{docenteId}")
	public ResponseEntity<ResponseBaseOperacion> disabledDocente(@PathVariable(value="docenteId") Integer docenteId) {
		
		Docente docente = null;
		
		try {
			
			if(docenteId != null && docenteId > 0) {
				
				docente = docenteService.getByDocenteId(docenteId);
				if(docente != null) {
					
					if(docente.getHabilitado().equals(true)) {
						
						docenteService.delete(docenteId);
						ResponseBaseOperacion response = new ResponseBaseOperacion("success", docente);
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
	
	@GetMapping("/docente/enabled/{docenteId}")
	public ResponseEntity<ResponseBaseOperacion> enabledDocente(@PathVariable(value="docenteId") Integer docenteId) {
		
		Docente docente = null;
		
		try {
			
			if(docenteId != null && docenteId > 0) {
				
				docente = docenteService.getByDocenteId(docenteId);
				if(docente != null) {
					
					if(docente.getHabilitado().equals(false)) {
						
						docenteService.enabled(docenteId);
						ResponseBaseOperacion response = new ResponseBaseOperacion("success", docente);
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
