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

import com.developer.UInvFISI.entity.Asignatura;
import com.developer.UInvFISI.entity.AsignaturaAlumno;
import com.developer.UInvFISI.service.AsignaturaAlumnoService;
import com.developer.UInvFISI.service.AsignaturaService;
import com.developer.UInvFISI.util.Constantes;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/asignaturaalumno")
public class AsignaturaAlumnoRestController {

	@Autowired
	@Qualifier("asignaturaAlumnoService")
	private AsignaturaAlumnoService asignaturaAlumnoService;
	
	@Autowired
	@Qualifier("asignaturaService")
	private AsignaturaService asignaturaService;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@GetMapping("/asignaturaalumnos")
	public ResponseEntity<List<AsignaturaAlumno>> findAll() {
		
		try {
			
			List<AsignaturaAlumno> asignaturaAlumnos = asignaturaAlumnoService.findAll();
			if(asignaturaAlumnos.isEmpty()) {
				
				return new ResponseEntity<List<AsignaturaAlumno>>(HttpStatus.NO_CONTENT);
			}
			else {
				
				return new ResponseEntity<List<AsignaturaAlumno>>(asignaturaAlumnos, HttpStatus.OK);
			}
		}
		catch(Exception e) {
			
			return new ResponseEntity<List<AsignaturaAlumno>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/asignaturaalumnos/asignatura/{asignaturaId}")
	public ResponseEntity<List<AsignaturaAlumno>> findAlumnosByAsignaturaId(@PathVariable(value="asignaturaId") Integer asignaturaId) {
		
		try {
			
			List<AsignaturaAlumno> asignaturaAlumnos = null;
			
			if(asignaturaId != null && asignaturaId > 0) {
				
				asignaturaAlumnos = asignaturaAlumnoService.findByAsignaturaAsignaturaId(asignaturaId);
				if(asignaturaAlumnos.isEmpty()) {
					
					return new ResponseEntity<List<AsignaturaAlumno>>(HttpStatus.NO_CONTENT);
				}
			}
			
			return new ResponseEntity<List<AsignaturaAlumno>>(asignaturaAlumnos, HttpStatus.OK);
		}
		catch(Exception e) {
			
			return new ResponseEntity<List<AsignaturaAlumno>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value="/save/asignatura/{asignaturaId}", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseBaseOperacion> saveAsignaturaAlumno(@PathVariable(value="asignaturaId") Integer asignaturaId,
		@RequestParam(value=Constantes.ASIGNATURA_ALUM_JSON_PARAM, required=true) String alumJson, 
		@RequestParam(value=Constantes.ASIGNATURA_ALUM_FILE_PARAM, required=true) MultipartFile file) {
		
		Asignatura asignatura = null;
		
		try {
			
			if(asignaturaId != null && asignaturaId > 0) {
				
				asignatura = asignaturaService.getByAsignaturaId(asignaturaId);
				if(asignatura == null) {
					
					return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
				}
			}
			
			AsignaturaAlumno asignaturaAlumno = objectMapper.readValue(alumJson, AsignaturaAlumno.class);
			
			if(!file.isEmpty()) {
			
				Path rootPath = Paths.get(Constantes.UPLOAD_FOLDER_BASE).resolve(Constantes.FOLDER_ARCHIVO_ALUMNOS)
										.resolve(file.getOriginalFilename());
				Path rootAbsolutePath = rootPath.toAbsolutePath();
				
				try {
					Files.copy(file.getInputStream(), rootAbsolutePath);
					asignaturaAlumno.setNombreFichero(file.getOriginalFilename());
					asignaturaAlumno.setFormatoFichero(file.getContentType());
					asignaturaAlumno.setTamanioFichero(Long.toString(file.getSize()));
					
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
			
			asignaturaAlumno.setAsignatura(asignatura);
			asignaturaAlumnoService.saveOrUpdate(asignaturaAlumno);
			ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.CREATED_MESSAGE, asignaturaAlumno);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.CREATED);
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value="/update/asignatura/{asignaturaId}/asignaturaDetalle/{asignaturaDetalleId}", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseBaseOperacion> updateAsignaturaAlumno(@PathVariable(value="asignaturaId") Integer asignaturaId,
			@PathVariable(value="asignaturaDetalleId") Integer asignaturaDetalleId, @RequestParam(value=Constantes.ASIGNATURA_ALUM_JSON_PARAM, required=true) String alumJson,
			@RequestParam(value=Constantes.ASIGNATURA_ALUM_FILE_PARAM, required=true) MultipartFile file) {
		
		Asignatura asignatura = null;
		AsignaturaAlumno asignaturaAlumnoOld = null;
		
		try {
			
			if(asignaturaId != null && asignaturaId > 0) {
				
				asignatura = asignaturaService.getByAsignaturaId(asignaturaId);
				if(asignaturaDetalleId != null && asignaturaDetalleId > 0) {
					asignaturaAlumnoOld = asignaturaAlumnoService.getAsignaturaAlumnoById(asignaturaDetalleId);
				}
			}
			
			AsignaturaAlumno asignaturaAlumno = objectMapper.readValue(alumJson, AsignaturaAlumno.class);
			
			if(!file.isEmpty()) {
				
				if(asignaturaAlumnoOld.getAsignaturaDetalleId() != null && asignaturaAlumnoOld.getAsignaturaDetalleId() > 0
						&& asignaturaAlumnoOld.getNombreFichero() != null && asignaturaAlumnoOld.getNombreFichero().length() > 0) {
					
					Path rootPath = Paths.get(Constantes.UPLOAD_FOLDER_BASE).resolve(Constantes.FOLDER_ARCHIVO_ALUMNOS)
												.resolve(asignaturaAlumnoOld.getNombreFichero()).toAbsolutePath();
					File archivo = rootPath.toFile();
					
					if(archivo.exists() && archivo.canRead()) {
						archivo.delete();
					}					
				}
				
				Path rootPath = Paths.get(Constantes.UPLOAD_FOLDER_BASE).resolve(Constantes.FOLDER_ARCHIVO_ALUMNOS)
										.resolve(file.getOriginalFilename());
				Path rootAbsolutePath = rootPath.toAbsolutePath();
				
				try {
					
					Files.copy(file.getInputStream(), rootAbsolutePath);
					asignaturaAlumnoOld.setNombreFichero(file.getOriginalFilename());
					asignaturaAlumnoOld.setFormatoFichero(file.getContentType());
					asignaturaAlumnoOld.setTamanioFichero(Long.toString(file.getSize()));
				}
				catch(IOException e) {
					e.printStackTrace();
				}	
			}
			
			asignaturaAlumnoOld.setAsignatura(asignatura);
			asignaturaAlumnoOld.setAlumno(asignaturaAlumno.getAlumno());
			asignaturaAlumnoOld.setDocumento1(asignaturaAlumno.getDocumento1());
			asignaturaAlumnoOld.setNroDocumento(asignaturaAlumno.getNroDocumento());
			asignaturaAlumnoOld.setAsunto(asignaturaAlumno.getAsunto());
			asignaturaAlumnoService.saveOrUpdate(asignaturaAlumnoOld);
			ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.UPDATED_MESSAGE, asignaturaAlumnoOld);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.OK);
			
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping(value=Constantes.DOWNLOAD_URI)
	public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request) {
		
		Path pathFile = Paths.get(Constantes.UPLOAD_FOLDER_BASE).resolve(Constantes.FOLDER_ARCHIVO_ALUMNOS)
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
		
		Path pathFile = Paths.get(Constantes.UPLOAD_FOLDER_BASE).resolve(Constantes.FOLDER_ARCHIVO_ALUMNOS)
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
