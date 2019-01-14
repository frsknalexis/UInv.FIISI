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


import com.developer.UInvFISI.entity.Asignatura;
import com.developer.UInvFISI.entity.Docente;
import com.developer.UInvFISI.entity.Escuela;

import com.developer.UInvFISI.service.AsignaturaService;

import com.developer.UInvFISI.service.EscuelaService;


@Controller
@RequestMapping("/asignatura")
@SessionAttributes("asignatura")
public class AsignaturaController {

	
	@Autowired
	@Qualifier("asignaturaService")
	private AsignaturaService asignaturaService;
	

	
	@Autowired
	@Qualifier("escuelaService")
	private EscuelaService escuelaService;
	
	

	
	@GetMapping("/list")
	public String listarAsignaturas(Map<String, Object> model) {
		
		
		List<Asignatura> asignaturas = asignaturaService.findAll();
		
		model.put("asignaturas", asignaturas);
		model.put("titulo", "Listado de Asignaturas");
		return "asignatura/listar";
	}

	@GetMapping("/form")
	public String create(Map<String, Object> model) {
		
		List<Escuela> escuelas = escuelaService.findAllEnabled();
		
		
		Asignatura asignatura = new Asignatura();
		model.put("escuelas", escuelas);
		model.put("asignatura", asignatura);
		model.put("titulo", "Asignar Asignatura");
		return "asignatura/form";
	}
	
	@GetMapping("/form/{asignaturaId}")
	public String update(@PathVariable(value="asignaturaId") Integer asignaturaId, Map<String, Object> model, RedirectAttributes flash ) {
		
		List<Escuela> escuelas = escuelaService.findAllEnabled();
		Asignatura asignatura = null;
		if(asignaturaId != null && asignaturaId > 0) {
			asignatura = asignaturaService.getByAsignaturaId(asignaturaId);
			if(asignatura == null) {
				flash.addFlashAttribute("error", "Asignatura no encontrada");
				return "redirect:/asignatura/list";
			}
		}
		
		else {
			flash.addFlashAttribute("error", "Asignatura no encontrada");
			return "redirect:/asignatura/list";
		}
		
		model.put("titulo", "Editar Asignatura");
		model.put("asignatura", asignatura);
		model.put("escuelas", escuelas);
		return "asignatura/form";
	}
	
	@PostMapping("/form")
	public String saveAsignatura(@Valid Asignatura asignatura, SessionStatus status, RedirectAttributes flash) {
		
		String mensajeFlash = (asignatura.getAsignaturaId() != null) ? "Asignatura Editada con exito" : "Asignatura Creada con exito";
		asignaturaService.saveOrUpdate(asignatura);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/asignatura/list";
	}
	
	@GetMapping("/delete/{asignaturaId}")
	public String delete(@PathVariable(value="asignaturaId") Integer asignaturaId) {
		
		if(asignaturaId != null && asignaturaId > 0) {
			asignaturaService.delete(asignaturaId);
		}
		return "redirect:/asignatura/list";
	}
	
	@GetMapping("/enabled/{asignaturaId}")
	public String enabled(@PathVariable(value="asignaturaId") Integer asignaturaId) {
		
		if(asignaturaId != null && asignaturaId > 0) {
			asignaturaService.enabled(asignaturaId);
		}
		return "redirect:/asignatura/list"; 
	}
	
	@GetMapping("/detail/{asignaturaId}")
	public String detail(@PathVariable(value="asignaturaId") Integer asignaturaId, Map<String, Object> model) {
		
		Asignatura asignatura = null;
		
		if(asignaturaId != null && asignaturaId > 0) {
			
			asignatura = asignaturaService.getByAsignaturaId(asignaturaId);
		}
		
		model.put("asignatura", asignatura);
		model.put("titulo", "ASIGNATURA :" + ' ' + asignatura.getNombreAsignatura());
		return "asignatura/ver";
	}
}
