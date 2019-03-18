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
}
