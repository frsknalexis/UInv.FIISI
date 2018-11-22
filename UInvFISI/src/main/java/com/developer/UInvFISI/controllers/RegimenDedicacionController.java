package com.developer.UInvFISI.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.developer.UInvFISI.entity.RegimenDedicacion;
import com.developer.UInvFISI.service.RegimenDedicacionService;

@Controller
@RequestMapping("/regimen")
@SessionAttributes("regimenDedicacion")
public class RegimenDedicacionController {

	@Autowired
	@Qualifier("regimenDedicacionService")
	private RegimenDedicacionService regimenDedicacionService;
	
	@GetMapping("/listar")
	public String listarRegimenes(Map<String, Object> model) {
		List<RegimenDedicacion> regimenes = regimenDedicacionService.findAll();
		model.put("regimenes", regimenes);
		model.put("titulo", "Listado Regimen Dedicacion");
		return "regimen/listar";
	}
	
	@GetMapping("/form")
	public String crear(Map<String, Object> model) {
		RegimenDedicacion regimenDedicacion = new RegimenDedicacion();
		model.put("regimenDedicacion", regimenDedicacion);
		model.put("titulo", "Formulario Regimen");
		return "regimen/form";
	}
	
	@GetMapping("/form/{regimenDedicacionId}")
	public String update(@PathVariable(value="regimenDedicacionId") Integer regimenDedicacionId, Map<String, Object> model, RedirectAttributes flash) {
		RegimenDedicacion regimenDedicacion = null;
		if(regimenDedicacionId != null && regimenDedicacionId > 0) {
			regimenDedicacion = regimenDedicacionService.getByRegimenDedicacionId(regimenDedicacionId);
			if(regimenDedicacion == null) {
				flash.addFlashAttribute("error", "Regimen no encontrado");
				return "redirect:/regimen/listar";
			}
		}
		else {
			flash.addFlashAttribute("error", "Regimen no encontrado");
			return "redirect:/regimen/listar";
		}
		model.put("titulo", "Editar Regimen");
		model.put("regimenDedicacion", regimenDedicacion);
		return "regimen/form";
	}
	
	@PostMapping("/form")
	public String save(@Valid RegimenDedicacion regimenDedicacion, SessionStatus status, RedirectAttributes flash) {
		String mensajeFlash = (regimenDedicacion.getRegimenDedicacionId() != null) ? "Regimen Editado con exito" : "Regimen Creado con exito";
		regimenDedicacionService.saveOrUpdate(regimenDedicacion);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/regimen/listar";
	}
	
	@GetMapping("/delete/{regimenDedicacionId}")
	public String delete(@PathVariable(value="regimenDedicacionId") Integer regimenDedicacionId) {
		if(regimenDedicacionId != null && regimenDedicacionId > 0) {
			regimenDedicacionService.delete(regimenDedicacionId);
		}
		return "redirect:/regimen/listar";
	}
	
	@GetMapping("/enabled/{regimenDedicacionId}")
	public String enabled(@PathVariable(value="regimenDedicacionId") Integer regimenDedicacionId) {
		if(regimenDedicacionId != null && regimenDedicacionId > 0) {
			regimenDedicacionService.enabled(regimenDedicacionId);
		}
		return "redirect:/regimen/listar";
	}
	
	@GetMapping("/detail/{regimenDedicacionId}")
	public String ver(@PathVariable(value="regimenDedicacionId") Integer regimenDedicacionId, Model model) {
		RegimenDedicacion regimen = null;
		if(regimenDedicacionId != null && regimenDedicacionId > 0) {
			regimen = regimenDedicacionService.getByRegimenDedicacionId(regimenDedicacionId);
		}
		model.addAttribute("titulo", "Regimen: " + regimen.getNombreRegimen());
		model.addAttribute("regimen", regimen);
		return "regimen/ver";
	}
}
