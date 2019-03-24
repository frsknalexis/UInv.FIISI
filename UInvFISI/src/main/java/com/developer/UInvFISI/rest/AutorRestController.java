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

import com.developer.UInvFISI.entity.Autor;
import com.developer.UInvFISI.entity.Trabajo;
import com.developer.UInvFISI.service.AutorService;
import com.developer.UInvFISI.service.TrabajoService;
import com.developer.UInvFISI.util.Constantes;

@RestController
@RequestMapping("/api/autor")
public class AutorRestController {

	@Autowired
	@Qualifier("trabajoService")
	private TrabajoService trabajoService;
	
	@Autowired
	@Qualifier("autorService")
	private AutorService autorService;
	
	@GetMapping("/autores")
	public ResponseEntity<List<Autor>> findAll() {
		
		try {
			
			List<Autor> autores = autorService.findAll();
			if(autores.isEmpty()) {
				
				return new ResponseEntity<List<Autor>>(HttpStatus.NO_CONTENT);
			}
			else {
				
				return new ResponseEntity<List<Autor>>(autores, HttpStatus.OK);
			}
		}
		catch(Exception e) {
			
			return new ResponseEntity<List<Autor>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/autores/trabajo/{trabajoId}")
	public ResponseEntity<List<Autor>> getAutoresByTrabajoId(@PathVariable(value="trabajoId") Integer trabajoId) {
		
		try {
			
			List<Autor> autores = autorService.findByTrabajoId(trabajoId);
			if(autores.isEmpty()) {
				
				return new ResponseEntity<List<Autor>>(HttpStatus.NO_CONTENT);
			}
			else {
				
				return new ResponseEntity<List<Autor>>(autores, HttpStatus.OK);
			}
		}
		catch(Exception e) {
			
			return new ResponseEntity<List<Autor>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/save/trabajo/{trabajoId}")
	public ResponseEntity<ResponseBaseOperacion> saveAutor(@PathVariable(value="trabajoId") Integer trabajoId, @Valid @RequestBody Autor autor) {
		
		Trabajo trabajo = null;
		
		try {
			
			if(trabajoId != null && trabajoId > 0) {
				
				trabajo = trabajoService.getByTrabajoId(trabajoId);
				if(trabajo == null) {
					
					return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
				}
			}
			
			autor.setTrabajo(trabajo);
			autorService.saveOrUpdate(autor);
			ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.CREATED_MESSAGE, autor);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.CREATED);
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update/trabajo/{trabajoId}/autor/{autorId}")
	public ResponseEntity<ResponseBaseOperacion> updateAutor(@PathVariable(value="trabajoId") Integer trabajoId,
			@PathVariable(value="autorId") Integer autorId, @Valid @RequestBody Autor autor) {
		
		Trabajo trabajo = null;
		Autor autorOld = null;
		
		try {
			
			if(trabajoId != null && trabajoId > 0) {
				
				trabajo = trabajoService.getByTrabajoId(trabajoId);
				if(autorId != null && autorId > 0) {
					
					autorOld = autorService.getAutorById(autorId);
					if(autorOld == null) {
						
						return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
					}
				}
			}
			
			autorOld.setTrabajo(trabajo);
			autorOld.setNombre(autor.getNombre());
			autorOld.setEmail(autor.getEmail());
			autorOld.setCelular(autor.getCelular());
			autorOld.setDocumento(autor.getDocumento());
			autorOld.setNroDocumento(autor.getNroDocumento());
			autorOld.setCondicion(autor.getCondicion());
			autorService.saveOrUpdate(autorOld);
			ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.UPDATED_MESSAGE, autorOld);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/autor/disabled/{autorId}")
	public ResponseEntity<ResponseBaseOperacion> disabledAutor(@PathVariable(value="autorId") Integer autorId) {
		
		Autor autor = null;
		
		try {
			
			if(autorId != null && autorId > 0) {
				
				autor = autorService.getAutorById(autorId);
				if(autor != null) {
					
					if(autor.getHabilitado().equals(true)) {
						
						autorService.disabled(autorId);
						ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.SUCCESS_MESSAGE, autor);
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
	
	@GetMapping("/autor/enabled/{autorId}")
	public ResponseEntity<ResponseBaseOperacion> enabledAutor(@PathVariable(value="autorId") Integer autorId) {
		
		Autor autor = null;
		
		try {
			
			if(autorId != null && autorId > 0) {
				
				autor = autorService.getAutorById(autorId);
				if(autor != null) {
					
					if(autor.getHabilitado().equals(false)) {
						
						autorService.enabled(autorId);
						ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.SUCCESS_MESSAGE, autor);
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
