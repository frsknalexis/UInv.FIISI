package com.developer.UInvFISI.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.developer.UInvFISI.entity.InformeTrabajo;
import com.developer.UInvFISI.entity.Trabajo;
import com.developer.UInvFISI.service.InformeTrabajoService;
import com.developer.UInvFISI.service.TrabajoService;
import com.developer.UInvFISI.util.Constantes;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/informetrabajo")
public class InformeTrabajoRestController {

	@Autowired
	@Qualifier("informeTrabajoService")
	private InformeTrabajoService informeTrabajoService;
	
	@Autowired
	@Qualifier("trabajoService")
	private TrabajoService trabajoService;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@GetMapping("/informestrabajos")
	public ResponseEntity<List<InformeTrabajo>> findAll() {
		
		try {
			
			List<InformeTrabajo> trabajos = informeTrabajoService.findAll();
			if(trabajos.isEmpty()) {
				
				return new ResponseEntity<List<InformeTrabajo>>(HttpStatus.NO_CONTENT);
			}
			else {
				
				return new ResponseEntity<List<InformeTrabajo>>(trabajos, HttpStatus.OK);
			}
		}
		catch(Exception e) {
			
			return new ResponseEntity<List<InformeTrabajo>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value="/save/trabajo/{trabajoId}", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseBaseOperacion> saveInformeTrabajo(@PathVariable(value="trabajoId") Integer trabajoId,
			@RequestParam(value=Constantes.INFORME_TRABAJO_JSON_PARAM, required=true) String informeTrabajoJson,
			@RequestParam(value=Constantes.INFORME_TRABAJO_FILE_PARAM, required=true) MultipartFile file) {
		
		Trabajo trabajo = null;
		
		try {
			
			if(trabajoId != null && trabajoId > 0) {
				
				trabajo = trabajoService.getByTrabajoId(trabajoId);
				if(trabajo == null) {
					
					return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.NO_CONTENT);
				}
			}
			
			InformeTrabajo informeTrabajo = objectMapper.readValue(informeTrabajoJson, InformeTrabajo.class);
			
			if(!file.isEmpty()) {
				
				Path rootPath = Paths.get(Constantes.UPLOAD_FOLDER_BASE).resolve(Constantes.FOLDER_INFORMES_TRABAJOS)
											.resolve(file.getOriginalFilename());
				Path rootAbsolutePath = rootPath.toAbsolutePath();
				
				try {
					
					Files.copy(file.getInputStream(), rootAbsolutePath);
					informeTrabajo.setNombreFichero(file.getOriginalFilename());
					informeTrabajo.setFormatoFichero(file.getContentType());
					informeTrabajo.setTamanioFichero(Long.toString(file.getSize()));
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
			
			informeTrabajo.setTrabajo(trabajo);
			informeTrabajoService.saveOrUpdate(informeTrabajo);
			ResponseBaseOperacion response = new ResponseBaseOperacion(Constantes.CREATED_MESSAGE, informeTrabajo);
			return new ResponseEntity<ResponseBaseOperacion>(response, HttpStatus.CREATED);
		}
		catch(Exception e) {
			
			return new ResponseEntity<ResponseBaseOperacion>(HttpStatus.BAD_REQUEST);
		}
	}
}
