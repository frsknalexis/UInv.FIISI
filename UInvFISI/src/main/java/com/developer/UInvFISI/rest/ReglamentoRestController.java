package com.developer.UInvFISI.rest;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.developer.UInvFISI.entity.Reglamento;
import com.developer.UInvFISI.service.ReglamentoService;
import com.developer.UInvFISI.util.Constantes;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/reglamento")
public class ReglamentoRestController {

	@Autowired
	@Qualifier("reglamentoService")
	private ReglamentoService reglamentoService;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@GetMapping("/reglamentos")
	public ResponseEntity<List<Reglamento>> findAll() {
		
		try {
			
			List<Reglamento> reglamentos = reglamentoService.findAll();
			if(reglamentos.isEmpty()) {
				
				return new ResponseEntity<List<Reglamento>>(HttpStatus.NO_CONTENT);
			}
			else {
				
				return new ResponseEntity<List<Reglamento>>(reglamentos, HttpStatus.OK);
			}
		}
		catch(Exception e) {
			
			return new ResponseEntity<List<Reglamento>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value="/save", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseBaseOperacion> saveReglamento(@RequestParam(value=Constantes.REGLAMENTO_FILE_PARAM, required=true) MultipartFile file,
			@RequestParam(value=Constantes.REGLAMENTO_JSON_PARAM, required=true) String reglamentoJson) {
		
		try {
			
			Reglamento reglamento = objectMapper.readValue(reglamentoJson, Reglamento.class);
			if(!file.isEmpty()) {
				
				Path rootPath = Paths.get(Constantes.UPLOAD_FOLDER_BASE).resolve(Constantes.FOLDER_REGLAMENTO).resolve(file.getOriginalFilename());
				Path rootAbsolutePath = rootPath.toAbsolutePath();
				
				try {
					
					Files.copy(file.getInputStream(), rootAbsolutePath);
					reglamento.setNombreFichero(file.getOriginalFilename());
					reglamento.setFormatoFichero(file.getContentType());
					reglamento.setTamanioFichero(Long.toString(file.getSize()));
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
			
			reglamentoService.saveOrUpdate(reglamento);
			ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.CREATED_MESSAGE, reglamento);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.CREATED);
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value="/update/{reglamentoId}", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseBaseOperacion> updateReglamento(@PathVariable(value="reglamentoId") Integer reglamentoId,
			@RequestParam(value=Constantes.REGLAMENTO_FILE_PARAM, required=true) MultipartFile file, 
			@RequestParam(value=Constantes.REGLAMENTO_JSON_PARAM, required=true) String reglamentoJson) {
		
		Reglamento reglamentoOld = null;
		
		try {
			
			if(reglamentoId != null && reglamentoId > 0) {
				
				reglamentoOld = reglamentoService.getByReglamentoId(reglamentoId);
				if(reglamentoOld == null) {
					
					return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT); 
				}
			}
			
			Reglamento reglamento = objectMapper.readValue(reglamentoJson, Reglamento.class);
			
			if(!file.isEmpty()) {
				
				if(reglamentoOld.getReglamentoId()!= null && reglamentoOld.getReglamentoId() > 0
					&& reglamentoOld.getNombreFichero() != null && reglamentoOld.getNombreFichero().length() > 0) {
					
					Path rootPath = Paths.get(Constantes.UPLOAD_FOLDER_BASE).resolve(Constantes.FOLDER_REGLAMENTO)
							.resolve(reglamentoOld.getNombreFichero()).toAbsolutePath();
					File archivo = rootPath.toFile();
					
					if(archivo.exists() && archivo.canRead()) {
						archivo.delete();
					}
				}
				
				Path rootPath = Paths.get(Constantes.UPLOAD_FOLDER_BASE).resolve(Constantes.FOLDER_REGLAMENTO)
						.resolve(file.getOriginalFilename());
				Path rootAbsolutePath = rootPath.toAbsolutePath();
				
				try {
					
					Files.copy(file.getInputStream(), rootAbsolutePath);
					reglamentoOld.setNombreFichero(file.getOriginalFilename());
					reglamentoOld.setFormatoFichero(file.getContentType());
					reglamentoOld.setTamanioFichero(Long.toString(file.getSize()));
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
			
			reglamentoOld.setAsunto(reglamento.getAsunto());
			reglamentoService.saveOrUpdate(reglamentoOld);
			ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.UPDATED_MESSAGE, reglamentoOld);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value=Constantes.DOWNLOAD_URI)
	public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request) {
		
		Path pathFile = Paths.get(Constantes.UPLOAD_FOLDER_BASE).resolve(Constantes.FOLDER_REGLAMENTO)
								.resolve(filename).toAbsolutePath();
		Resource resource = null;
		
		try {
			
			resource = new UrlResource(pathFile.toUri());
			if(!resource.exists() || !resource.isReadable()) {
				throw new RuntimeException("Error: no se puede leer el archivo " + pathFile.toString());
			}
		}
		catch(MalformedURLException e) {
			e.printStackTrace();
		}
		
		String contentType = null;
		
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		if(contentType == null) {
			contentType = Constantes.DEFAULT_CONTENT_TYPE;
		}
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, String.format(Constantes.FILE_DOWNLOAD_HTTP_HEADER, resource.getFilename()))
				.body(resource);
	}
	
	@GetMapping(value=Constantes.VIEW_PDF_URI)
	public ResponseEntity<Resource> viewPDF(@PathVariable String filename, HttpServletRequest request) {
		
		Path pathFile = Paths.get(Constantes.UPLOAD_FOLDER_BASE).resolve(Constantes.FOLDER_REGLAMENTO)
							.resolve(filename).toAbsolutePath();
		
		Resource resource = null;
		
		try {
			
			resource = new UrlResource(pathFile.toUri());
			if(!resource.exists() || !resource.isReadable()) {
				throw new RuntimeException("Error: no se puede leer el archivo " + pathFile.toString());
			}
		}
		catch(MalformedURLException e) {
			e.printStackTrace();
		}
		
		String contentType = null;
		
		try {
			
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		if(contentType == null) {
			contentType = Constantes.DEFAULT_CONTENT_TYPE;
		}
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, String.format(Constantes.VIEW_PDF_HTTP_HEADER, resource.getFilename()))
				.body(resource);
	}
}
