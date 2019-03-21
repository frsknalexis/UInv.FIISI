package com.developer.UInvFISI.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.developer.UInvFISI.entity.Asignacion;
import com.developer.UInvFISI.entity.AsignacionDocente;
import com.developer.UInvFISI.entity.InformeTrimestral;
import com.developer.UInvFISI.service.AsignacionDocenteService;
import com.developer.UInvFISI.service.AsignacionService;
import com.developer.UInvFISI.service.InformeTrimestralService;
import com.developer.UInvFISI.util.Constantes;


@Controller
@RequestMapping("/informeTrimestral")
@SessionAttributes("informeTrimestral")
public class InformeTrimestralController {

	private static final String UPLOAD_FOLDER = "C://Files//uploads";
	
	@Autowired
	@Qualifier("asignacionService")
	private AsignacionService asignacionService;
	
	@Autowired
	@Qualifier("asignacionDocenteService")
	private AsignacionDocenteService asignacionDocenteService;
	
	@Autowired
	@Qualifier("informeTrimestralService")
	private InformeTrimestralService informeTrimestralService;
	
	@GetMapping("/investigaciones")
	public String listarInvestigaciones(Map<String, Object> model) {
		
		List<Asignacion> investigaciones = asignacionService.findAllEnabled();
		
		model.put("investigaciones", investigaciones);
		model.put("titulo", "Proyectos de Investigacion FEDU");
		return Constantes.INFORME_TRIMESTRAL_VIEW;
	}
	
	@GetMapping("/investigadores/{asignacionId}")
	public String listarInvestigadores(@PathVariable(value="asignacionId") Integer asignacionId, Map<String, Object> model) {
		
		List<AsignacionDocente> investigadores = asignacionDocenteService.findByAsignacionIdAndHabilitado(asignacionId);
		Asignacion investigacion = asignacionService.getByAsignacionId(asignacionId);
		model.put("titulo", investigacion.getNombreInvestigacion());
		model.put("investigacion", investigacion);
		model.put("investigadores", investigadores);
		return Constantes.INFORME_TRIMESTRAL_INVESTIGADORES_VIEW;
	}
	
	@GetMapping("/formInformeTrimestral/{asignacionDetalleId}")
	String createInformeTrimestral(@PathVariable(value="asignacionDetalleId") Integer asignacionDetalleId, Map<String, Object> model,
			RedirectAttributes flash) {
		
		AsignacionDocente asignacionDetalle = null;
		if(asignacionDetalleId != null && asignacionDetalleId > 0) {
			
			asignacionDetalle = asignacionDocenteService.getAsignacionDocenteById(asignacionDetalleId);
		}
		
		if(asignacionDetalle == null) {
			
			flash.addFlashAttribute("error", "La Asignacion no existe");
			return "redirect:/asignacion/list";
		}
		
		InformeTrimestral informeTrimestral = new InformeTrimestral();
		informeTrimestral.setAsignacionDetalle(asignacionDetalle);
		
		model.put("informeTrimestral", informeTrimestral);
		model.put("titulo", "Cargar Informe Trimestral");
		return "informetrimestral/form";
	}
	
	@GetMapping("/formInformeTrimestral/{asignacionDetalleId}/{informeTrimestralId}")
	public String updateInformeTrimestral(@PathVariable(value="asignacionDetalleId") Integer asignacionDetalleId,
			@PathVariable(value="informeTrimestralId") Integer informeTrimestralId, Map<String, Object> model) {
		
		AsignacionDocente asignacionDetalle = null;
		InformeTrimestral informeTrimestral = null;
		
		if(asignacionDetalleId != null && asignacionDetalleId > 0) {
			
			asignacionDetalle = asignacionDocenteService.getAsignacionDocenteById(asignacionDetalleId);
			if(informeTrimestralId != null && informeTrimestralId > 0) {
				informeTrimestral = informeTrimestralService.findByAsignacionDetalleAsignacionDetalleIdAndInformeTrimestralId(asignacionDetalleId, informeTrimestralId);
			}
		}
		
		informeTrimestral.setAsignacionDetalle(asignacionDetalle);
		model.put("informeTrimestral", informeTrimestral);
		model.put("titulo", "Editar Informe Trimestral");
		return "informetrimestral/form";
	}
	
	@PostMapping("/formInformeTrimestral")
	String saveInformeTrimestral(@Valid InformeTrimestral informeTrimestral, SessionStatus status, @RequestParam("fileInformeTrimestral") MultipartFile file,
			RedirectAttributes flash) {
		
		if(file.isEmpty()) {
			
			flash.addFlashAttribute("error", "Seleccione un archivo");
			return "redirect:/informeTrimestral/formInformeTrimestral/" + informeTrimestral.getAsignacionDetalle().getAsignacionDetalleId();
		}
		
		try {
			
			byte[] bytes = file.getBytes();
			Path rutaCompleta = Paths.get(UPLOAD_FOLDER + "//" + file.getOriginalFilename());
			Files.write(rutaCompleta, bytes);
			flash.addFlashAttribute("info", "Ha subido correctamente el archivo: " + file.getOriginalFilename());
			
			informeTrimestral.setNombreFichero(file.getOriginalFilename());
			informeTrimestral.setTamanioFichero(file.getSize());
			informeTrimestral.setFormatoFichero(file.getContentType());
	
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		
		informeTrimestralService.saveOrUpdate(informeTrimestral);
		status.setComplete();
		flash.addFlashAttribute("success", "Informe Trimestral Cargado con exito");
		return "redirect:/asignacionDocente/formAsignacionDetalle/" + informeTrimestral.getAsignacionDetalle().getAsignacion().getAsignacionId();
	}
	
	
	@GetMapping("/download/{filename:.+}")
	@ResponseBody
	ResponseEntity<Resource> downloadInformeTrimestral(@PathVariable String filename, HttpServletRequest request) {
		
		Path rutaCompleta = Paths.get(UPLOAD_FOLDER + "//" + filename);
		Resource resource = null;
		
		try {
			
			resource = new UrlResource(rutaCompleta.toUri());
			if(!resource.exists() && !resource.isReadable()) {
				throw new RuntimeException("Error: no se puede leer el archivo: " + rutaCompleta.toString());
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
			contentType = "application/octet-stream";
		}
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
				
	}
	
	@GetMapping("/view/{filename:.+}")
	@ResponseBody
	void viewInformeTrimestral(@PathVariable String filename, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		File file = new File(UPLOAD_FOLDER + "//" + filename);
		
		if(file.exists()) {
			
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if(mimeType == null) {
				mimeType = "application/octet-stream";
			}
			
			response.setContentType(mimeType);
			
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=\"" + file.getName() + "\""));
			
			response.setContentLength((int) file.length());
			
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}
}
