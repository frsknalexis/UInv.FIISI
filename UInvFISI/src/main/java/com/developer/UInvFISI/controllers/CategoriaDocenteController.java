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

import com.developer.UInvFISI.entity.CategoriaDocente;
import com.developer.UInvFISI.service.CategoriaDocenteService;

@Controller
@RequestMapping("/categoriadoc")
@SessionAttributes("categoriaDocente")
public class CategoriaDocenteController {

	@Autowired
	@Qualifier("categoriaDocenteService")
	private CategoriaDocenteService categoriaDocenteService;
	
	@GetMapping("/listar")
	public String listarCategorias(Map<String, Object> model) {
		List<CategoriaDocente> categorias = categoriaDocenteService.findAll();
		model.put("categorias", categorias);
		model.put("titulo", "Listado de Categorias");
		return "categoriadocente/listar";
	}
	
	@GetMapping("/form")
	public String crear(Map<String, Object> model) {
		CategoriaDocente categoriaDocente = new CategoriaDocente();
		model.put("categoriaDocente", categoriaDocente);
		model.put("titulo", "Formulario de Categoria");
		return "categoriadocente/form";
	}
	
	@GetMapping("/form/{categoriaDocenteId}")
	public String update(@PathVariable(value="categoriaDocenteId") Integer categoriaDocenteId, Map<String, Object> model, RedirectAttributes flash) {
		CategoriaDocente categoriaDocente = null;
		if(categoriaDocenteId > 0) {
			categoriaDocente = categoriaDocenteService.getByCategoriaDocenteId(categoriaDocenteId);
			if(categoriaDocente == null) {
				flash.addFlashAttribute("error", "Categoria no encontrada");
				return "redirect:/categoriadoc/listar";
			}
		}
		else {
			flash.addFlashAttribute("error", "Categoria no encontrada");
			return "redirect:/categoriadoc/listar";
		}
		model.put("titulo", "Editar Categoria");
		model.put("categoriaDocente", categoriaDocente);
		return "categoriadocente/form";
	}
	
	@PostMapping("/form")
	public String save(@Valid CategoriaDocente categoriaDocente, SessionStatus status, RedirectAttributes flash) {
		String mensajeFlash = (categoriaDocente.getCategoriaDocenteId() != null) ? "Categoria Editada con exito" : "Categoria Creada con exito";
		categoriaDocenteService.saveOrUpdate(categoriaDocente);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/categoriadoc/listar";
	}
	
	@GetMapping("/delete/{categoriaDocenteId}")
	public String delete(@PathVariable(value="categoriaDocenteId") Integer categoriaDocenteId) {
		categoriaDocenteService.delete(categoriaDocenteId);
		return "redirect:/categoriadoc/listar";
	}
	
	@GetMapping("/enabled/{categoriaDocenteId}")
	public String enabled(@PathVariable(value="categoriaDocenteId") Integer categoriaDocenteId) {
		categoriaDocenteService.enabled(categoriaDocenteId);
		return "redirect:/categoriadoc/listar";
	}
	
	@GetMapping("/detail/{categoriaDocenteId}")
	public String ver(@PathVariable(value="categoriaDocenteId") Integer categoriaDocenteId, Map<String, Object> model) {
		CategoriaDocente categoria = null;
		if(categoriaDocenteId > 0) {
			categoria = categoriaDocenteService.getByCategoriaDocenteId(categoriaDocenteId);
		}
		
		model.put("titulo", "Categoria: " + categoria.getNombreCategoria());
		model.put("categoria", categoria);
		return "categoriadocente/ver";
	}
	
}
