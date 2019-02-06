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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.developer.UInvFISI.entity.CategoriaDocente;
import com.developer.UInvFISI.entity.Docente;
import com.developer.UInvFISI.entity.Documento;
import com.developer.UInvFISI.entity.RegimenDedicacion;
import com.developer.UInvFISI.service.CategoriaDocenteService;
import com.developer.UInvFISI.service.DocenteService;
import com.developer.UInvFISI.service.DocumentoService;
import com.developer.UInvFISI.service.RegimenDedicacionService;

@Controller
@RequestMapping("/docente")
@SessionAttributes("docente")
public class DocenteController {

	@Autowired
	@Qualifier("docenteService")
	private DocenteService docenteService;
	
	@Autowired
	@Qualifier("documentoService")
	private DocumentoService documentoService;
	
	@Autowired
	@Qualifier("categoriaDocenteService")
	private CategoriaDocenteService categoriaDocenteService;
	
	@Autowired
	@Qualifier("regimenDedicacionService")
	private RegimenDedicacionService regimenDedicacionService;
	
		
	@GetMapping("/listar")
	public String listarDocentes(Map<String, Object> model) {
		List<Docente> docentes = docenteService.findAll();
		model.put("titulo", "Listado Docentes DINA Y ORCID");
		model.put("docentes", docentes);
		return "docente/listar";
	}
	
	@GetMapping("/form")
	public String create(Map<String, Object> model) {
		List<Documento> documentos = documentoService.findAllEnabled();
		List<CategoriaDocente> categorias = categoriaDocenteService.findAllEnabled();
		List<RegimenDedicacion> regimenes = regimenDedicacionService.findAllEnabled();
		Docente docente = new Docente();
		model.put("regimenes", regimenes);
		model.put("categorias", categorias);
		model.put("documentos", documentos);
		model.put("titulo", "Formulario Docente");
		model.put("docente", docente);
		return "docente/form";
	}
	
	@PostMapping("/form")
	public String save(@Valid Docente docente, SessionStatus status, RedirectAttributes flash) {
		String mensajeFlash= (docente.getDocenteId() != null) ? "Docente Editado con exito" : "Docente Creado con exito";
		docenteService.saveOrUpdate(docente);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/docente/listar";
	}
	
	@GetMapping("/form/{docenteId}")
	public String update(@PathVariable(value="docenteId") Integer docenteId, Map<String, Object> model, RedirectAttributes flash ) {
		Docente docente = null;
		List<Documento> documentos = documentoService.findAllEnabled();
		List<CategoriaDocente> categorias = categoriaDocenteService.findAllEnabled();
		List<RegimenDedicacion> regimenes = regimenDedicacionService.findAllEnabled();
		if(docenteId != null && docenteId > 0) {
			docente = docenteService.getByDocenteId(docenteId);
			if(docente == null) {
				flash.addFlashAttribute("error", "Docente no encontrado");
				return "redirect:/docente/listar";
			}
			
		}
		else {
			flash.addFlashAttribute("error", "Docente no encontrado");
			return "redirect:/docente/listar";
		}
		model.put("titulo", "Editar Docente");
		model.put("regimenes", regimenes);
		model.put("categorias", categorias);
		model.put("documentos", documentos);
		model.put("docente", docente);
		return "docente/form";
	}
	
	@GetMapping("/enabled/{docenteId}")
	public String enabled(@PathVariable(value="docenteId") Integer docenteId) {
		if(docenteId != null && docenteId > 0) {
			docenteService.enabled(docenteId);
		}
		return "redirect:/docente/listar";
	}
	
	@GetMapping("/delete/{docenteId}")
	public String delete(@PathVariable(value="docenteId") Integer docenteId) {
		if(docenteId != null && docenteId > 0) {
			docenteService.delete(docenteId);
		}
		return "redirect:/docente/listar";
	}
	
	@GetMapping("/detail/{docenteId}")
	public String detalle(@PathVariable(value="docenteId") Integer docenteId, Map<String, Object> model) {
		Docente docente = null;
		if(docenteId != null && docenteId > 0) {
			docente = docenteService.getByDocenteId(docenteId);
		}
		model.put("titulo", "Docente: " + docente.getNombresDocente() + " " + docente.getApellidosDocente());
		model.put("docente", docente);
		return "docente/ver";
	}
	
	@GetMapping(value="/cargar-docentes/{termino}", produces= {"application/json"})
	@ResponseBody List<Docente> cargarDocentes(@PathVariable(value="termino") String termino) {
		List<Docente> docentes = docenteService.findByNombresDocente(termino);
		return docentes;
	}
	
}
