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

import com.developer.UInvFISI.entity.Escuela;

import com.developer.UInvFISI.service.EscuelaService;


@Controller
@RequestMapping("/escuela")
@SessionAttributes("escuela")
public class EscuelaController {

	@Autowired
	@Qualifier("escuelaService")
	private EscuelaService escuelaService;
	
	
	@GetMapping("/listar")
	public String listarEscuelas(Model model) {
		List<Escuela> escuelas = escuelaService.findAll();
		model.addAttribute("titulo", "Listado de Escuelas");
		model.addAttribute("escuelas", escuelas);
		return "escuela/listar";
	}
	
	@GetMapping("/form")
	public String crear(Map<String, Object> model) {
		Escuela escuela = new Escuela();
		model.put("escuela", escuela);
		model.put("titulo", "Formulario De Escuela");
		return "escuela/form";
	}
	
	@GetMapping("/form/{escuelaId}")
	public String update(@PathVariable(value="escuelaId") Integer escuelaId, Map<String, Object> model, RedirectAttributes flash) {
		Escuela escuela = null;
		if(escuelaId > 0) {
			escuela = escuelaService.getByCodigo(escuelaId);
			if(escuela == null) {
				flash.addFlashAttribute("error", "Escuela no encontrada");
				return "redirect:/escuela/listar";
			}
		}
		else {
			flash.addFlashAttribute("error", "Escuela no encontrada");
			return "redirect:/escuela/listar";
		}
		model.put("titulo", "Editar Escuela");
		model.put("escuela", escuela);
		return "escuela/form";
	}
	
	@PostMapping("/form")
	public String save(@Valid Escuela escuela, SessionStatus status, RedirectAttributes flash) {
		String mensajeFlash = (escuela.getEscuelaId() != null) ? "Escuela Editada con exito" : "Escuela Creada con exito";
		escuelaService.saveOrUpdate(escuela);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/escuela/listar";
	}
	
	@GetMapping("/delete/{escuelaId}")
	public String delete(@PathVariable(value="escuelaId") Integer escuelaId, RedirectAttributes flash) {
		escuelaService.delete(escuelaId);
		flash.addFlashAttribute("success", "Escuela Deshabilitada con exito");
		return "redirect:/escuela/listar";
	}
	
	@GetMapping("/enabled/{escuelaId}")
	public String enabled(@PathVariable(value="escuelaId") Integer escuelaId, RedirectAttributes flash) {
		escuelaService.enabled(escuelaId);
		flash.addFlashAttribute("success", "Escuela Habilitada con exito");
		return "redirect:/escuela/listar";
	}
	
	@DeleteMapping("/eliminar/{escuelaId}")
	public String eliminar(@PathVariable(value="escueladId") Integer escuelaId) {
		escuelaService.delete2(escuelaId);
		return "redirect:/escuela/listar";
	}
	
	@GetMapping("/detail/{escuelaId}")
	public String detalle(@PathVariable(value="escuelaId") Integer escuelaId, Map<String, Object> model) {
		Escuela escuela= null;
		if(escuelaId > 0)  {
			escuela = escuelaService.getByCodigo(escuelaId);
		}
		
		model.put("titulo", "E.A.P" + ' ' + escuela.getNombreEscuela());
		model.put("escuela", escuela);
		return "/escuela/ver";
	}
}
