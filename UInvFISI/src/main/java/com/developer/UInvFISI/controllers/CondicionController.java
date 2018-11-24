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

import com.developer.UInvFISI.entity.Condicion;
import com.developer.UInvFISI.service.CondicionService;

@Controller
@RequestMapping("/condicion")
@SessionAttributes("condicion")
public class CondicionController {

	@Autowired
	@Qualifier("condicionService")
	private CondicionService condicionService;
	
	@GetMapping("/listar")
	public String listarCondicion(Map<String, Object> model) {
		List<Condicion> condiciones = condicionService.findAll();
		model.put("condiciones", condiciones);
		model.put("titulo", "Listado Condiciones");
		return "condicion/listar";
	}
	
	@GetMapping("/form")
	public String create(Map<String, Object> model) {
		Condicion condicion = new Condicion();
		model.put("condicion", condicion);
		model.put("titulo", "Formulario Condicion");
		return "condicion/form";
	}
	
	@GetMapping("/form/{condicionId}")
	public String update(@PathVariable(value="condicionId") Integer condicionId, Map<String, Object> model, RedirectAttributes flash) {
		Condicion condicion = null;
		if(condicionId != null && condicionId > 0) {
			condicion = condicionService.getByCondicionId(condicionId);
			if(condicion == null) {
				flash.addFlashAttribute("error", "Condicion no encontrada");
				return "redirect:/condicion/listar";
			}
		}
		else {
			flash.addFlashAttribute("error", "Condicion no encontrada");
			return "redirect:/condicion/listar";
		}
		model.put("titulo", "Editar Condicion");
		model.put("condicion", condicion);
		return "condicion/form";
	}
	
	@PostMapping("/form")
	public String save(@Valid Condicion condicion, SessionStatus status, RedirectAttributes flash) {
		String mensajeFlash = (condicion.getCondicionId() != null) ? "Condicion Editada con exito" : "Condicion Creada con exito";
		condicionService.saveOrUpdate(condicion);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/condicion/listar";
	}
	
	@GetMapping("/delete/{condicionId}")
	public String delete(@PathVariable(value="condicionId") Integer condicionId) {
		if(condicionId != null && condicionId > 0) {
			condicionService.delete(condicionId);
		}
		return "redirect:/condicion/listar";
	}
	
	@GetMapping("/enabled/{condicionId}")
	public String enabled(@PathVariable(value="condicionId") Integer condicionId) {
		if(condicionId != null && condicionId > 0) {
			condicionService.enabled(condicionId);
		}
		return "redirect:/condicion/listar";
	}
	
	@GetMapping("/detail/{condicionId}")
	public String ver(@PathVariable(value="condicionId") Integer condicionId, Map<String, Object> model) {
		Condicion condicion = null;
		if(condicionId != null && condicionId > 0) {
			condicion = condicionService.getByCondicionId(condicionId);
		}
		model.put("titulo", "Condicion: " + condicion.getNombreCondicion());
		model.put("condicion", condicion);
		return "condicion/ver";
	}
}
