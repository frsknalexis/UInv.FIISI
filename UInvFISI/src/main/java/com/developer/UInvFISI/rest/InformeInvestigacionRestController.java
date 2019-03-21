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

import com.developer.UInvFISI.entity.Asignacion;
import com.developer.UInvFISI.entity.InformeInvestigacion;
import com.developer.UInvFISI.service.AsignacionService;
import com.developer.UInvFISI.service.InformeInvestigacionService;
import com.developer.UInvFISI.util.Constantes;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/informeinvestigacion")
public class InformeInvestigacionRestController {

	@Autowired
	@Qualifier("informeInvestigacionService")
	private InformeInvestigacionService informeInvestigacionService;
	
	@Autowired
	@Qualifier("asignacionService")
	private AsignacionService asignacionService;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@GetMapping("/informes")
	public ResponseEntity<List<InformeInvestigacion>> findAll() {
		
		try {
			
			List<InformeInvestigacion> informes = informeInvestigacionService.findAll();
			if(informes.isEmpty()) {
				
				return new ResponseEntity<List<InformeInvestigacion>>(HttpStatus.NO_CONTENT);
			}
			else {
				
				return new ResponseEntity<List<InformeInvestigacion>>(informes, HttpStatus.OK);
			}
		}
		catch(Exception e) {
			
			return new ResponseEntity<List<InformeInvestigacion>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/informes/asignacion/{asignacionId}")
	public ResponseEntity<List<InformeInvestigacion>> findInformesByAsignacionId(@PathVariable(value="asignacionId") Integer asignacionId) {
		
		try {
			
			List<InformeInvestigacion> informes = informeInvestigacionService.findByAsignacionAsignacionId(asignacionId);
			if(informes.isEmpty()) {
				
				return new ResponseEntity<List<InformeInvestigacion>>(HttpStatus.NO_CONTENT);
			}
			else {
				
				return new ResponseEntity<List<InformeInvestigacion>>(informes, HttpStatus.OK);
			}
		}
		catch(Exception e) {
			
			return new ResponseEntity<List<InformeInvestigacion>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value="/save/asignacion/{asignacionId}", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseBaseOperacion> saveInformeInvestigacion(@PathVariable(value="asignacionId") Integer asignacionId,
			@RequestParam(value=Constantes.INFORME_INVESTIGACION_JSON_PARAM, required=true) String informeJson,
			@RequestParam(value=Constantes.INFORME_INVESTIGACION_FILE_PARAM, required=true) MultipartFile file) {
		
		Asignacion asignacion = null;
		
		try {
			
			if(asignacionId != null && asignacionId > 0) {
				
				asignacion = asignacionService.getByAsignacionId(asignacionId);
				if(asignacion == null) {
					
					return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
				}
			}
			
			InformeInvestigacion informeInvestigacion = objectMapper.readValue(informeJson, InformeInvestigacion.class);
			
			if(!file.isEmpty()) {
				
				Path rootPath = Paths.get(Constantes.UPLOAD_FOLDER_BASE).resolve(Constantes.FOLDER_INFORMES_INVESTIGACION)
										.resolve(file.getOriginalFilename());
				Path rootAbsolutePath = rootPath.toAbsolutePath();
				
				try {
					
					Files.copy(file.getInputStream(), rootAbsolutePath);
					informeInvestigacion.setNombreFichero(file.getOriginalFilename());
					informeInvestigacion.setFormatoFichero(file.getContentType());
					informeInvestigacion.setTamanioFichero(Long.toString(file.getSize()));
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
			
			informeInvestigacion.setAsignacion(asignacion);
			informeInvestigacionService.saveOrUpdate(informeInvestigacion);
			ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.CREATED_MESSAGE, informeInvestigacion);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value="/update/asignacion/{asignacionId}/informeInvestigacion/{informeAsignacionId}", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseBaseOperacion> updateInformeInvestigacion(@PathVariable(value="asignacionId") Integer asignacionId,
			@PathVariable(value="informeAsignacionId") Integer informeAsignacionId, @RequestParam(value=Constantes.INFORME_INVESTIGACION_JSON_PARAM, required=true) String informeJson,
			@RequestParam(value=Constantes.INFORME_INVESTIGACION_FILE_PARAM, required=true) MultipartFile file) {
		
		Asignacion asignacion = null;
		InformeInvestigacion informeInvestigacionOld = null;
		
		try {
			
			if(asignacionId != null && asignacionId > 0) {
				
				asignacion = asignacionService.getByAsignacionId(asignacionId);
				if(informeAsignacionId != null && informeAsignacionId > 0) {
					informeInvestigacionOld = informeInvestigacionService.getByInformeAsignacionId(informeAsignacionId);
					if(informeInvestigacionOld == null) {
						
						return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
					}
				}
			}
			
			InformeInvestigacion informeInvestigacion = objectMapper.readValue(informeJson, InformeInvestigacion.class);
			
			if(!file.isEmpty()) {
				
				if(informeInvestigacionOld.getInformeAsignacionId() != null && informeInvestigacionOld.getInformeAsignacionId() > 0 
						&& informeInvestigacionOld.getNombreFichero() != null && informeInvestigacionOld.getNombreFichero().length() > 0) {
					
					Path rootPath = Paths.get(Constantes.UPLOAD_FOLDER_BASE).resolve(Constantes.FOLDER_INFORMES_INVESTIGACION)
											.resolve(informeInvestigacionOld.getNombreFichero()).toAbsolutePath();
					File archivo = rootPath.toFile();
					
					if(archivo.exists() && archivo.canRead()) {
						archivo.delete();
					}
				}
				
				Path rootPath = Paths.get(Constantes.UPLOAD_FOLDER_BASE).resolve(Constantes.FOLDER_INFORMES_INVESTIGACION)
										.resolve(file.getOriginalFilename());
				Path rootAbsolutePath = rootPath.toAbsolutePath();
				
				try {
					
					Files.copy(file.getInputStream(), rootAbsolutePath);
					informeInvestigacionOld.setNombreFichero(file.getOriginalFilename());
					informeInvestigacionOld.setFormatoFichero(file.getContentType());
					informeInvestigacionOld.setTamanioFichero(Long.toString(file.getSize()));
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
			
			informeInvestigacionOld.setAsignacion(asignacion);
			informeInvestigacionOld.setAsunto(informeInvestigacion.getAsunto());
			informeInvestigacionService.saveOrUpdate(informeInvestigacionOld);
			ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.UPDATED_MESSAGE, informeInvestigacionOld);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
			
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value=Constantes.DOWNLOAD_URI)
	public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request) {
		
		Path pathFile = Paths.get(Constantes.UPLOAD_FOLDER_BASE).resolve(Constantes.FOLDER_INFORMES_INVESTIGACION)
								.resolve(filename).toAbsolutePath();
		Resource resource = null;
		
		try {
			
			resource = new UrlResource(pathFile.toUri());
			if(!resource.exists() || !resource.isReadable()) {
				throw new RuntimeException("Error: no se puede leer el archivo: " + pathFile.toString());
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
		
		Path pathFile = Paths.get(Constantes.UPLOAD_FOLDER_BASE).resolve(Constantes.FOLDER_INFORMES_INVESTIGACION)
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
