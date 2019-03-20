package com.developer.UInvFISI.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.developer.UInvFISI.entity.Asignacion;
import com.developer.UInvFISI.entity.LineaInvestigacion;
import com.developer.UInvFISI.repository.AsignacionDocenteRepository;
import com.developer.UInvFISI.service.AsignacionDocenteService;
import com.developer.UInvFISI.service.AsignacionService;
import com.developer.UInvFISI.service.CondicionService;
import com.developer.UInvFISI.service.DocenteService;
import com.developer.UInvFISI.service.LineaInvestigacionService;
import com.developer.UInvFISI.service.ProgramaService;

@Controller
@RequestMapping("/asignacion")
@SessionAttributes("asignacion")
public class AsignacionController {
	
	@Autowired
	@Qualifier("asignacionService")
	private AsignacionService asignacionService;
	
	@Autowired
	@Qualifier("condicionService")
	private CondicionService condicionService;
	
	@Autowired
	@Qualifier("docenteService")
	private DocenteService docenteService;
	
	@Autowired
	@Qualifier("lineaInvestigacionService")
	private LineaInvestigacionService lineaInvestigacionService;
	
	@Autowired
	@Qualifier("asignacionDocenteService")
	private AsignacionDocenteService asignacionDocenteService;
	
	@Autowired
	@Qualifier("programaService")
	private ProgramaService programaService;
	
	@Autowired
	@Qualifier("asignacionDctRepository")
	private AsignacionDocenteRepository asignacionDocenteRepository;

	
	@GetMapping("/list")
	public String listarInvestigaciones(Map<String, Object> model) {
		
		
		List<Asignacion> asignaciones = asignacionService.findAll();
		
		model.put("asignaciones", asignaciones);
		model.put("titulo", "Proyectos de Investigacion FEDU");
		return "asignacion/listar";
	}

	@GetMapping("/form")
	public String create(Map<String, Object> model) {
		
		List<LineaInvestigacion> lineasInvestigacion = lineaInvestigacionService.findAllEnabled();
		
		Asignacion asignacion = new Asignacion();
		model.put("lineasInvestigacion", lineasInvestigacion);
		model.put("asignacion", asignacion);
		model.put("titulo", "Asignar Investigacion");
		return "asignacion/form";
	}
	
	@GetMapping("/form/{asignacionId}")
	public String update(@PathVariable(value="asignacionId") Integer asignacionId, Map<String, Object> model, RedirectAttributes flash ) {
		
		List<LineaInvestigacion> lineasInvestigacion = lineaInvestigacionService.findAllEnabled();
		
		Asignacion asignacion = null;
		if(asignacionId != null && asignacionId > 0) {
			asignacion = asignacionService.getByAsignacionId(asignacionId);
			if(asignacion == null) {
				flash.addFlashAttribute("error", "Investigacion no encontrada");
				return "redirect:/asignacion/list";
			}
		}
		
		else {
			flash.addFlashAttribute("error", "Investigacion no encontrada");
			return "redirect:/asignacion/list";
		}
		
		model.put("titulo", "Editar Investigacion");
		model.put("asignacion", asignacion);
		model.put("lineasInvestigacion", lineasInvestigacion);
		return "asignacion/form";
	}
	
	@PostMapping("/form")
	public String saveAsignacion(@Valid Asignacion asignacion, SessionStatus status, RedirectAttributes flash) {
		
		String mensajeFlash = (asignacion.getAsignacionId() != null) ? "Investigacion Editada con exito" : "Investigacion Creada con exito";
		asignacionService.saveOrUpdate(asignacion);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/asignacion/list";
	}
	
	@GetMapping("/delete/{asignacionId}")
	public String delete(@PathVariable(value="asignacionId") Integer asignacionId) {
		
		if(asignacionId != null && asignacionId > 0) {
			asignacionService.delete(asignacionId);
		}
		return "redirect:/asignacion/list";
	}
	
	@GetMapping("/enabled/{asignacionId}")
	public String enabled(@PathVariable(value="asignacionId") Integer asignacionId) {
		
		if(asignacionId != null && asignacionId > 0) {
			asignacionService.enabled(asignacionId);
		}
		return "redirect:/asignacion/list"; 
	}
	
	@GetMapping("/detail/{asignacionId}")
	public String detail(@PathVariable(value="asignacionId") Integer asignacionId, Map<String, Object> model) {
		
		Asignacion asignacion = null;
		
		if(asignacionId != null && asignacionId > 0) {
			
			asignacion = asignacionService.getByAsignacionId(asignacionId);
		}
		
		model.put("asignacion", asignacion);
		model.put("titulo", "PROYECTO DE INVESTIGACION" + ' ' + asignacion.getAnio());
		return "asignacion/ver";
	}
}
