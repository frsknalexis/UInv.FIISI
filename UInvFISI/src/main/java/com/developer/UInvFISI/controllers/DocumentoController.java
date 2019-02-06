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

import com.developer.UInvFISI.entity.Documento;
import com.developer.UInvFISI.service.DocumentoService;

@Controller
@RequestMapping("/documento")
@SessionAttributes("documento")
public class DocumentoController {

	@Autowired
	@Qualifier("documentoService")
	private DocumentoService documentoService;
	
	
	@GetMapping("/listar")
	public String listarDocumentos(Model model) {
		List<Documento> documentos = documentoService.findAll();
		model.addAttribute("titulo", "Listado de Documentos");
		model.addAttribute("documentos", documentos);
		return "documento/listar";
	}
	
	@GetMapping("/form")
	public String crear(Map<String, Object> model) {
		Documento documento = new Documento();
		model.put("documento", documento);
		model.put("titulo", "Formulario De Documento");
		return "documento/form";
	}
	
	@GetMapping("/form/{documentoId}")
	public String update(@PathVariable(value="documentoId") Integer documentoId, Map<String, Object> model, RedirectAttributes flash) {
		Documento documento = null;
		if(documentoId > 0) {
			documento = documentoService.getByCodigo(documentoId);
			if(documento == null) {
				flash.addFlashAttribute("error", "Documento no encontrado");
				return "redirect:/documento/listar";
			}
		}
		else {
			flash.addFlashAttribute("error", "Documento no encontrado");
			return "redirect:/documento/listar";
		}
		model.put("titulo", "Editar Documento");
		model.put("documento", documento);
		return "documento/form";
	}
	
	@PostMapping("/form")
	public String save(@Valid Documento documento, SessionStatus status, RedirectAttributes flash) {
		String mensajeFlash = (documento.getDocumentoId() != null) ? "Documento Editado con exito" : "Documento Creado con exito";
		documentoService.saveOrUpdate(documento);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/documento/listar";
	}
	
	@GetMapping("/delete/{documentoId}")
	public String delete(@PathVariable(value="documentoId") Integer documentoId, RedirectAttributes flash) {
		documentoService.delete(documentoId);
		flash.addFlashAttribute("success", "Documento Deshabilitado con exito");
		return "redirect:/documento/listar";
	}
	
	@GetMapping("/enabled/{documentoId}")
	public String enabled(@PathVariable(value="documentoId") Integer documentoId, RedirectAttributes flash) {
		documentoService.enabled(documentoId);
		flash.addFlashAttribute("success", "Documento Habilitado con exito");
		return "redirect:/documento/listar";
	}
	
	@DeleteMapping("/eliminar/{documentoId}")
	public String eliminar(@PathVariable(value="documentoId") Integer documentoId) {
		documentoService.delete2(documentoId);
		return "redirect:/documento/listar";
	}
	
	@GetMapping("/detail/{documentoId}")
	public String detalle(@PathVariable(value="documentoId") Integer documentoId, Map<String, Object> model) {
		Documento documento = null;
		if(documentoId > 0)  {
			documento = documentoService.getByCodigo(documentoId);
		}
		
		model.put("titulo", "Documento: " + documento.getNombreDocumento());
		model.put("documento", documento);
		return "documento/ver";
	}
	
}
