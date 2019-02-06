package com.developer.UInvFISI.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.developer.UInvFISI.entity.Facultad;

import com.developer.UInvFISI.service.FacultadService;

@Controller
@RequestMapping("/facultad")
@SessionAttributes("facultad")
public class FacultadController {

	@Autowired
	@Qualifier("facultadService")
	private FacultadService facultadService;
	
	
	@GetMapping("/listar")
	public String listarFacultades(Model model) {
		List<Facultad> facultades = facultadService.findAll();
		model.addAttribute("titulo", "Listado de Facultades");
		model.addAttribute("facultades", facultades);
		return "facultad/listar";
	}
	
	@GetMapping("/form")
	public String crear(Map<String, Object> model) {
		Facultad facultad = new Facultad();
		model.put("facultad", facultad);
		model.put("titulo", "Formulario De Facultad");
		return "facultad/form";
	}
	
	@GetMapping("/form/{facultadId}")
	public String update(@PathVariable(value="facultadId") Integer facultadId, Map<String, Object> model, RedirectAttributes flash) {
		Facultad facultad = null;
		if(facultadId > 0) {
			facultad = facultadService.getByCodigo(facultadId);
			if(facultad == null) {
				flash.addFlashAttribute("error", "Facultad no encontrada");
				return "redirect:/facultad/listar";
			}
		}
		else {
			flash.addFlashAttribute("error", "Facultad no encontrada");
			return "redirect:/facultad/listar";
		}
		model.put("titulo", "Editar Facultad");
		model.put("facultad", facultad);
		return "facultad/form";
	}
	
	@PostMapping("/form")
	public String save(@Valid Facultad facultad, SessionStatus status, RedirectAttributes flash) {
		String mensajeFlash = (facultad.getFacultadId() != null) ? "Facultad Editada con exito" : "Facultad Creada con exito";
		facultadService.saveOrUpdate(facultad);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/facultad/listar";
	}
	
	@GetMapping("/delete/{facultadId}")
	public String delete(@PathVariable(value="facultadId") Integer facultadId, RedirectAttributes flash) {
		facultadService.delete(facultadId);
		flash.addFlashAttribute("success", "Facultad Deshabilitada con exito");
		return "redirect:/facultad/listar";
	}
	
	@GetMapping("/enabled/{facultadId}")
	public String enabled(@PathVariable(value="facultadId") Integer facultadId, RedirectAttributes flash) {
		facultadService.enabled(facultadId);
		flash.addFlashAttribute("success", "Facultad Habilitada con exito");
		return "redirect:/facultad/listar";
	}
	
	@DeleteMapping("/eliminar/{facultadId}")
	public String eliminar(@PathVariable(value="facultadId") Integer facultadId) {
		facultadService.delete2(facultadId);
		return "redirect:/facultad/listar";
	}
	
	@GetMapping("/detail/{facultadId}")
	public String detalle(@PathVariable(value="facultadId") Integer facultadId, Map<String, Object> model) {
		Facultad facultad= null;
		if(facultadId > 0)  {
			facultad = facultadService.getByCodigo(facultadId);
		}
		
		model.put("titulo", "Facultad: " + facultad.getNombreFacultad());
		model.put("facultad", facultad);
		return "/facultad/ver";
	}
	
}
