package com.developer.UInvFISI.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.developer.UInvFISI.entity.AsignacionDocente;
import com.developer.UInvFISI.entity.InformeTrimestral;
import com.developer.UInvFISI.service.AsignacionDocenteService;
import com.developer.UInvFISI.service.InformeTrimestralService;


@Controller
@RequestMapping("/informeTrimestral")
@SessionAttributes("informeTrimestral")
public class InformeTrimestralController {

	private static final String UPLOAD_FOLDER = "C://Files//uploads";
	
	@Autowired
	@Qualifier("asignacionDocenteService")
	private AsignacionDocenteService asignacionDocenteService;
	
	@Autowired
	@Qualifier("informeTrimestralService")
	private InformeTrimestralService informeTrimestralService;
	
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
}
