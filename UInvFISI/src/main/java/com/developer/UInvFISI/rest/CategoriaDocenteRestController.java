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

import com.developer.UInvFISI.entity.CategoriaDocente;
import com.developer.UInvFISI.service.CategoriaDocenteService;

@RestController
@RequestMapping("/api/categoriadocente")
public class CategoriaDocenteRestController {

	@Autowired
	private CategoriaDocenteService categoriaDocenteService;
	
	@GetMapping("/categorias")
	public ResponseEntity<List<CategoriaDocente>> findAll() {
		
		List<CategoriaDocente> categoriasDocente = categoriaDocenteService.findAll();
		
		if(categoriasDocente.size() == 0) {
			
			return new ResponseEntity<List<CategoriaDocente>>(HttpStatus.NO_CONTENT);
		}
		
		else {
			
			return new ResponseEntity<List<CategoriaDocente>>(categoriasDocente, HttpStatus.OK);
		}
	}
	
	@GetMapping("/categoria/{categoriaDocenteId}")
	public ResponseEntity<CategoriaDocente> getByCategoriaDocenteId(@PathVariable(value="categoriaDocenteId") Integer categoriaDocenteId) {
		
		CategoriaDocente categoriaDocente = null;
		
		if(categoriaDocenteId != null && categoriaDocenteId > 0) {
			
			categoriaDocente = categoriaDocenteService.getByCategoriaDocenteId(categoriaDocenteId);
			
			if(categoriaDocente == null) {
				
				return new ResponseEntity<CategoriaDocente>(HttpStatus.NO_CONTENT);
			}
		}
		
		return new ResponseEntity<CategoriaDocente>(categoriaDocente, HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<ResponseBaseOperacion> saveCategoria(@Valid @RequestBody CategoriaDocente categoriaDocente) {
		
		categoriaDocenteService.saveOrUpdate(categoriaDocente);
		ResponseBaseOperacion response = new ResponseBaseOperacion("success", categoriaDocente);
		return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{categoriaDocenteId}")
	public ResponseEntity<ResponseBaseOperacion> updateCategoria(@PathVariable(value="categoriaDocenteId")Integer categoriaDocenteId,
			@Valid @RequestBody CategoriaDocente categoriaDocente) {
		
		CategoriaDocente categoriaDocenteOld = null;
		
		if(categoriaDocenteId != null && categoriaDocenteId > 0) {
			
			categoriaDocenteOld = categoriaDocenteService.getByCategoriaDocenteId(categoriaDocenteId);
			
			if(categoriaDocenteOld == null) {
				
				return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
			}
		}
		
		categoriaDocenteOld.setNombreCategoria(categoriaDocente.getNombreCategoria());
		categoriaDocenteService.saveOrUpdate(categoriaDocenteOld);
		ResponseBaseOperacion response = new ResponseBaseOperacion("updated", categoriaDocenteOld);
		return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
	}
	
	@GetMapping("/categoria/disabled/{categoriaDocenteId}")
	public ResponseEntity<Void> disabledCategoriaDocente(@PathVariable(value="categoriaDocenteId") Integer categoriaDocenteId) {
		
		CategoriaDocente categoriaDocente = null;
		
		if(categoriaDocenteId != null && categoriaDocenteId > 0) {
			
			categoriaDocente = categoriaDocenteService.getByCategoriaDocenteId(categoriaDocenteId);
			
			if(categoriaDocente != null) {
				
				if(categoriaDocente.getHabilitado() == true) {
					
					categoriaDocenteService.delete(categoriaDocenteId);
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
	
	@GetMapping("/categoria/enabled/{categoriaDocenteId}")
	public ResponseEntity<Void> enabledCategoriaDocente(@PathVariable(value="categoriaDocenteId") Integer categoriaDocenteId) {
		
		CategoriaDocente categoriaDocente = null;
		
		if(categoriaDocenteId != null && categoriaDocenteId > 0) {
			
			categoriaDocente = categoriaDocenteService.getByCategoriaDocenteId(categoriaDocenteId);
			
			if(categoriaDocente != null) {
				
				if(categoriaDocente.getHabilitado().equals(false)) {
					
					categoriaDocenteService.enabled(categoriaDocenteId);
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
