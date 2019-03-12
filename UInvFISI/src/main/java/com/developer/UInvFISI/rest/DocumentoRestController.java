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

import com.developer.UInvFISI.entity.Documento;
import com.developer.UInvFISI.service.DocumentoService;

@RestController
@RequestMapping("/api/documento")
public class DocumentoRestController {

	@Autowired
	private DocumentoService documentoService;
	
	@GetMapping("/documentos")
	public ResponseEntity<List<Documento>> findAll() {
		
		List<Documento> documentos = documentoService.findAll();
		if(documentos.size() == 0) {
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		else {
			
			return new ResponseEntity<List<Documento>>(documentos, HttpStatus.OK);
		}
	}
	
	@GetMapping("/documento/{documentoId}")
	ResponseEntity<Documento> getByDocumentoId(@PathVariable(value="documentoId") Integer documentoId) {
		
		Documento documento = null;
		if(documentoId != null && documentoId > 0) {
			
			documento = documentoService.getByCodigo(documentoId);
			if(documento == null) {
				
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		}
		
		return new ResponseEntity<Documento>(documento, HttpStatus.OK);
	}
	
	@PostMapping("/save")
	ResponseEntity<ResponseBaseOperacion> saveDocumento(@Valid @RequestBody Documento documento) {
	
		 documentoService.saveOrUpdate(documento);
		 ResponseBaseOperacion response = new ResponseBaseOperacion("success", documento);
		 return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
	}
	
	@PutMapping("/update/{documentoId}")
	ResponseEntity<ResponseBaseOperacion> updateDocumento(@PathVariable(value="documentoId") Integer documentoId,
			@Valid @RequestBody Documento documento) {
		
		Documento documentoOld = null;
		
		if(documentoId != null && documentoId > 0) {
			
			documentoOld = documentoService.getByCodigo(documentoId);
			if(documentoOld == null) {
				
				return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
			}
		}
		
		documentoOld.setAbreviatura(documento.getAbreviatura());
		documentoOld.setNombreDocumento(documento.getNombreDocumento());
		documentoService.saveOrUpdate(documentoOld);
		ResponseBaseOperacion response = new ResponseBaseOperacion("success", documentoOld);
		return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
	}
	
	@GetMapping("/documento/disabled/{documentoId}")
	public ResponseEntity<Void> disabledDocumento(@PathVariable(value="documentoId") Integer documentoId) {
		
		Documento documento = null;
		
		if(documentoId != null && documentoId > 0) {
			
			documento = documentoService.getByCodigo(documentoId);
			
			if(documento != null) {
				
				if(documento.getHabilitado() == true ) {
					
					documentoService.delete(documentoId);
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
	
	@GetMapping("/documento/enabled/{documentoId}")
	public ResponseEntity<Void> enabledDocumento(@PathVariable(value="documentoId") Integer documentoId) {
		
		Documento documento = null;
		if(documentoId != null && documentoId > 0) {
			
			documento = documentoService.getByCodigo(documentoId);
			
			if(documento != null) {
				
				if(documento.getHabilitado() == false) {
					
					documentoService.enabled(documentoId);
					return new ResponseEntity<Void>(HttpStatus.OK);
				}
				else {
					
					return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
				}
			}
			
			else {
				
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		}
		
		return null;
	}
	
}
