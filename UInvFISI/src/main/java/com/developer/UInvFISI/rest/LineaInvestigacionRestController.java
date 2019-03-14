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

import com.developer.UInvFISI.entity.LineaInvestigacion;
import com.developer.UInvFISI.entity.Programa;
import com.developer.UInvFISI.service.LineaInvestigacionService;
import com.developer.UInvFISI.service.ProgramaService;

@RestController
@RequestMapping("/api/linea")
public class LineaInvestigacionRestController {

	@Autowired
	@Qualifier("lineaInvestigacionService")
	private LineaInvestigacionService lineaInvestigacionService;
	
	@Autowired
	@Qualifier("programaService")
	private ProgramaService programaService;
	
	@GetMapping("/lineas")
	public ResponseEntity<List<LineaInvestigacion>> findAll() {
		
		try {
			
			List<LineaInvestigacion> lineasInvestigacion = lineaInvestigacionService.findAll();
			if(lineasInvestigacion.isEmpty()) {
				
				return new ResponseEntity<List<LineaInvestigacion>>(HttpStatus.NO_CONTENT);
			}
			
			else {
				
				return new ResponseEntity<List<LineaInvestigacion>>(lineasInvestigacion, HttpStatus.OK);
			}
		}
		catch(Exception e) {
			
			return new ResponseEntity<List<LineaInvestigacion>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/linea/{lineaInvestigacionId}")
	public ResponseEntity<LineaInvestigacion> getByLineaInvestigacionId(@PathVariable(value="lineaInvestigacionId") Integer lineaInvestigacionId) {
		
		LineaInvestigacion lineaInvestigacion = null;
		
		try {
			
			if(lineaInvestigacionId != null && lineaInvestigacionId > 0) {
				
				lineaInvestigacion = lineaInvestigacionService.getByLineaInvestigacionId(lineaInvestigacionId);
				
				if(lineaInvestigacion == null) {
					
					return new ResponseEntity<LineaInvestigacion>(HttpStatus.NO_CONTENT);
				}
			}
			
			return new ResponseEntity<LineaInvestigacion>(lineaInvestigacion, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<LineaInvestigacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/lineas/programa/{programaId}")
	public ResponseEntity<List<LineaInvestigacion>> findLineasByProgramaId(@PathVariable(value="programaId") Integer programaId) {
		
		try {
			
			List<LineaInvestigacion> lineasInvestigacion = null;
			
			if(programaId != null && programaId > 0) {
				
				lineasInvestigacion = lineaInvestigacionService.findByProgramaProgramaId(programaId);
				
				if(lineasInvestigacion.isEmpty()) {
					
					return new ResponseEntity<List<LineaInvestigacion>>(HttpStatus.NO_CONTENT);
				}
			}
			
			return new ResponseEntity<List<LineaInvestigacion>>(lineasInvestigacion, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<List<LineaInvestigacion>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/save/programa/{programaId}")
	public ResponseEntity<ResponseBaseOperacion> saveLineaInvestigacion(@PathVariable(value="programaId") Integer programaId,
			@Valid @RequestBody LineaInvestigacion lineaInvestigacion) {
		
		Programa programa = null;
		
		try {
			
			if(programaId != null && programaId > 0) {
				
				programa = programaService.getByProgramaId(programaId);
				if(programa == null) {
					
					return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
				}
			}
			
			lineaInvestigacion.setPrograma(programa);
			lineaInvestigacionService.saveOrUpdate(lineaInvestigacion);
			ResponseBaseOperacion response = new ResponseBaseOperacion("created", lineaInvestigacion);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.CREATED);
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping("/update/programa/{programaId}/linea/{lineaInvestigacionId}")
	public ResponseEntity<ResponseBaseOperacion> updateLineaInvestigacion(@PathVariable(value="programaId") Integer programaId,
			@PathVariable(value="lineaInvestigacionId") Integer lineaInvestigacionId, @Valid @RequestBody LineaInvestigacion lineaInvestigacion) {
		
		Programa programa = null;
		LineaInvestigacion lineaInvestigacionOld = null;
		
		try {
			
			if(programaId != null && programaId > 0) {
				
				programa = programaService.getByProgramaId(programaId);
				if(lineaInvestigacionId != null && lineaInvestigacionId > 0) {
					lineaInvestigacionOld = lineaInvestigacionService.getByLineaInvestigacionId(lineaInvestigacionId);
				}
			}
			
			lineaInvestigacionOld.setPrograma(programa);
			lineaInvestigacionOld.setNombreLineaInvestigacion(lineaInvestigacion.getNombreLineaInvestigacion());
			lineaInvestigacionService.saveOrUpdate(lineaInvestigacionOld);
			ResponseBaseOperacion response = new ResponseBaseOperacion("updated", lineaInvestigacionOld);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/linea/disabled/{lineaInvestigacionId}")
	public ResponseEntity<ResponseBaseOperacion> disabledLineaInvestigacion(@PathVariable(value="lineaInvestigacionId") Integer lineaInvestigacionId) {
		
		LineaInvestigacion lineaInvestigacion = null;
		
		try {
			
			if(lineaInvestigacionId != null && lineaInvestigacionId > 0) {
				
				lineaInvestigacion = lineaInvestigacionService.getByLineaInvestigacionId(lineaInvestigacionId);
				
				if(lineaInvestigacion != null) {
					
					if(lineaInvestigacion.getHabilitado().equals(true)) {
						
						lineaInvestigacionService.delete(lineaInvestigacionId);
						ResponseBaseOperacion response = new ResponseBaseOperacion("success", lineaInvestigacion);
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
	
	@GetMapping("/linea/enabled/{lineaInvestigacionId}")
	public ResponseEntity<ResponseBaseOperacion> enabledLineaInvestigacion(@PathVariable(value="lineaInvestigacionId") Integer lineaInvestigacionId) {
		
		LineaInvestigacion lineaInvestigacion = null;
			
		try {
			
			if(lineaInvestigacionId != null && lineaInvestigacionId > 0) {
				
				lineaInvestigacion = lineaInvestigacionService.getByLineaInvestigacionId(lineaInvestigacionId);
				
				if(lineaInvestigacion != null) {
					
					if(lineaInvestigacion.getHabilitado().equals(false)) {
						
						lineaInvestigacionService.enabled(lineaInvestigacionId);
						ResponseBaseOperacion response = new ResponseBaseOperacion("success", lineaInvestigacion);
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
