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

import com.developer.UInvFISI.entity.Programa;
import com.developer.UInvFISI.service.ProgramaService;

@Controller
@RequestMapping("/programa")
@SessionAttributes("programa")
public class ProgramaController {
	
	@Autowired
	@Qualifier("programaService")
	private ProgramaService programaService;
	
	@GetMapping("/listar")
	public String listarProgramas(Map<String, Object> model) {
		List<Programa> programas = programaService.findAll();
		model.put("programas", programas);
		model.put("titulo", "Listado Programas");
		return "programa/listar";
	}
	
	@GetMapping("/form")
	public String create(Map<String, Object> model) {
		Programa programa = new Programa();
		model.put("programa", programa);
		model.put("titulo", "Formulario Programa");
		return "programa/form";
	}
	
	@GetMapping("/form/{programaId}")
	public String update(@PathVariable(value="programaId") Integer programaId, Map<String, Object> model, RedirectAttributes flash) {
		Programa programa = null;
		if(programaId != null && programaId > 0) {
			programa = programaService.getByProgramaId(programaId);
			if(programa == null) {
				flash.addFlashAttribute("error", "Programa no encontrado");
				return "redirect:/programa/listar";
			}
		}
		else {
			flash.addFlashAttribute("error", "Programa no encontrado");
			return "redirect:/programa/listar";
		}
		model.put("titulo", "Editar Programa");
		model.put("programa", programa);
		return "programa/form";
	}
	
	@PostMapping("/form")
	public String save(@Valid Programa programa, SessionStatus status, RedirectAttributes flash) {
		String mensajeFlash = (programa.getProgramaId() != null && programa.getProgramaId() > 0) ? "Programa Editado con exito" : 
			"Programa Creado con exito";
		programaService.saveOrUpdate(programa);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/programa/listar";
	}
	
	@GetMapping("/delete/{programaId}")
	public String delete(@PathVariable(value="programaId") Integer programaId) {
		if(programaId != null && programaId > 0) {
			programaService.delete(programaId);
		}
		return "redirect:/programa/listar";
	}
	
	@GetMapping("/enabled/{programaId}")
	public String enabled(@PathVariable(value="programaId") Integer programaId) {
		if(programaId != null && programaId > 0) {
			programaService.enabled(programaId);
		}
		return "redirect:/programa/listar";
	}
	
	@GetMapping("/detail/{programaId}")
	public String ver(@PathVariable(value="programaId") Integer programaId, Map<String, Object> model) {
		Programa programa = null;
		if(programaId > 0 && programaId != null) {
			programa = programaService.getByProgramaId(programaId);
		}
		model.put("titulo", "PROGRAMA Y LINEAS INVESTIGACION");
		model.put("programa", programa);
		return "programa/ver";
	}

}
