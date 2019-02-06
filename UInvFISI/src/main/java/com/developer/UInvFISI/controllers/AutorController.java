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
import com.developer.UInvFISI.entity.Autor;
import com.developer.UInvFISI.entity.Condicion;
import com.developer.UInvFISI.entity.Documento;
import com.developer.UInvFISI.service.AutorService;
import com.developer.UInvFISI.service.TrabajoService;
import com.developer.UInvFISI.service.CondicionService;
import com.developer.UInvFISI.service.DocumentoService;

@Controller
@RequestMapping("/autor")
@SessionAttributes("autor")
public class AutorController {

	@Autowired
	@Qualifier("trabajoService")
	private TrabajoService trabajoService;
	
	@Autowired
	@Qualifier("condicionService")
	private CondicionService condicionService;
	
	@Autowired
	@Qualifier("autorService")
	private AutorService autorService;
	@Autowired
	@Qualifier("documentoService")
	private DocumentoService documentoService;
	
	@GetMapping("/formAutorDetalle/{trabajoId}")
	public String createAutor(@PathVariable(value="trabajoId") Integer trabajoId, Map<String, Object> model,
			RedirectAttributes flash) {
		
		List<Autor> autores = autorService.findByTrabajoId(trabajoId);
		List<Condicion> condiciones = condicionService.findAllEnabled();
		List<Documento> documentos = documentoService.findAllEnabled();
		Trabajo trabajos = trabajoService.getByTrabajoId(trabajoId);
		
		if(trabajos == null) {
			
			flash.addFlashAttribute("error", "La Tesis no existe");
			return "redirect:/trabajo/list";
		}
		
		Autor autor = new Autor();
		autor.setTrabajo(trabajos);
		
		model.put("autor", autor);
		model.put("condiciones", condiciones);
		model.put("documento", documentos);
		model.put("autores", autores);
		model.put("titulo", "Formulario Asignacion Autor");
		return "trabajo/formTrabajo";
	}
	
	@GetMapping("/formAutorDetalle/{trabajoId}/{autorDetalleId}")
	public String updateAutor(@PathVariable(value="autorDetalleId") Integer autorDetalleId, @PathVariable(value="trabajoId") Integer trabajoId,
			Map<String, Object> model, RedirectAttributes flash) {
		
		List<Autor> autores = autorService.findByTrabajoId(trabajoId);
		List<Documento> documento = documentoService.findAllEnabled();
		List<Condicion> condiciones = condicionService.findAllEnabled();
		Autor autor = null;
		Trabajo trabajo = null;
		
		if(trabajoId != null && trabajoId > 0) {
			
			trabajo = trabajoService.getByTrabajoId(trabajoId);
			if(autorDetalleId != null && autorDetalleId > 0) {
				autor = autorService.findByTrabajoTrabajoIdAndAutorDetalleId(trabajoId, autorDetalleId);
			}
		}
		
		autor.setTrabajo(trabajo);
		
		model.put("autores", autores);
		model.put("documento", documento);
		model.put("condiciones", condiciones);
		model.put("autor", autor);
		model.put("titulo", "Editar Asignacion Autor");
		return "trabajo/formTrabajo";
	}
	
	@PostMapping("/formAutorDetalle")
	public String saveAutor(@Valid Autor autor, SessionStatus status,
			RedirectAttributes flash) {
		
		autorService.saveOrUpdate(autor);
		status.setComplete();
		flash.addFlashAttribute("success", "Autor Asignado con exito");
		return "redirect:/autor/formAutorDetalle/" + autor.getTrabajo().getTrabajoId();
	}
}
