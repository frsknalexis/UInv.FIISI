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

import com.developer.UInvFISI.entity.Trabajo;
import com.developer.UInvFISI.entity.Escuela;
import com.developer.UInvFISI.repository.AutorRepository;
import com.developer.UInvFISI.service.AutorService;
import com.developer.UInvFISI.service.TrabajoService;
import com.developer.UInvFISI.service.CondicionService;
import com.developer.UInvFISI.service.DocenteService;
import com.developer.UInvFISI.service.EscuelaService;
import com.developer.UInvFISI.service.FacultadService;

@Controller
@RequestMapping("/trabajo")
@SessionAttributes("trabajo")
public class TrabajoController {
	
	@Autowired
	@Qualifier("trabajoService")
	private TrabajoService trabajoService;
	
	@Autowired
	@Qualifier("condicionService")
	private CondicionService condicionService;
	
	@Autowired
	@Qualifier("docenteService")
	private DocenteService docenteService;
	
	@Autowired
	@Qualifier("escuelaService")
	private EscuelaService escuelaService;
	
	@Autowired
	@Qualifier("autorService")
	private AutorService autorService;
	
	@Autowired
	@Qualifier("facultadService")
	private FacultadService facultadService;
	
	@Autowired
	@Qualifier("autorAsRepository")
	private AutorRepository autorRepository;
	
	@GetMapping("/list")
	public String listarTrabajos(Map<String, Object> model) {
		
		
		List<Trabajo> trabajo = trabajoService.findAll();
		model.put("trabajo", trabajo);
		model.put("titulo", "PROYECTOS DE TESIS");
		return "trabajo/listar";
	}

	@GetMapping("/form")
	public String create(Map<String, Object> model) {
		
		List<Escuela> escuela = escuelaService.findAllEnabled();
		
		Trabajo trabajo = new Trabajo();
		model.put("escuela", escuela);
		model.put("trabajo", trabajo);
		model.put("titulo", "Asignar Trabajo");
		return "trabajo/form";
	}
	
	@GetMapping("/form/{trabajoId}")
	public String update(@PathVariable(value="trabajoId") Integer trabajoId, Map<String, Object> model, RedirectAttributes flash ) {
		
		List<Escuela> escuela = escuelaService.findAllEnabled();
		
		Trabajo trabajo = null;
		if(trabajoId != null && trabajoId > 0) {
			trabajo = trabajoService.getByTrabajoId(trabajoId);
			if(trabajo == null) {
				flash.addFlashAttribute("error", "Trabajo no encontrado");
				return "redirect:/trabajo/list";
			}
		}
		
		else {
			flash.addFlashAttribute("error", "Trabajo no encontrado");
			return "redirect:/trabajo/list";
		}
		
		model.put("titulo", "Editar Trabajo");
		model.put("trabajo", trabajo);
		model.put("escuela", escuela);
		return "trabajo/form";
	}
	
	@PostMapping("/form")
	public String saveTrabajo(@Valid Trabajo trabajo, SessionStatus status, RedirectAttributes flash) {
		
		String mensajeFlash = (trabajo.getTrabajoId() != null) ? "Trabajo Editado con exito" : "Trabajo Creado con exito";
		trabajoService.saveOrUpdate(trabajo);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/trabajo/list";
	}
	
	@GetMapping("/detail/{trabajoId}")
	public String detail(@PathVariable(value="trabajoId") Integer trabajoId, Map<String, Object> model) {
		
		Trabajo trabajo = null;
		
		if(trabajoId != null && trabajoId > 0) {
			
			trabajo = trabajoService.getByTrabajoId(trabajoId);
		}
		
		model.put("trabajo", trabajo);
		model.put("titulo", "PROYECTO DE TESIS" + ' ' + trabajo.getAnio());
		return "trabajo/ver";
	}
}
