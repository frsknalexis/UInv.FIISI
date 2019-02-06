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

import com.developer.UInvFISI.entity.LineaInvestigacion;
import com.developer.UInvFISI.entity.Programa;
import com.developer.UInvFISI.service.LineaInvestigacionService;
import com.developer.UInvFISI.service.ProgramaService;


@Controller
@RequestMapping("/linea")
@SessionAttributes("lineaInvestigacion")
public class LineaInvestigacionController {

	@Autowired
	@Qualifier("lineaInvestigacionService")
	private LineaInvestigacionService lineaInvestigacionService;
	
	@Autowired
	@Qualifier("programaService")
	private ProgramaService programaService;
	
	@GetMapping("/listar")
	public String listarLineas(Map<String, Object> model) {
		
		List<LineaInvestigacion> lineas = lineaInvestigacionService.findAll();
		model.put("lineas", lineas);
		model.put("titulo", "Listado Lineas Investigacion");
		return "linea/listar";
		
	}
	
	@GetMapping("/formLineaInvestigacion/{programaId}")
	public String createLineaInvestigacion(@PathVariable(value="programaId") Integer programaId, Map<String, Object> model,
			RedirectAttributes flash) {
		
		List<LineaInvestigacion> lineasInvestigacion = lineaInvestigacionService.findByProgramaProgramaId(programaId);
		Programa programa = programaService.getByProgramaId(programaId);
		if(programa == null) {
			
			flash.addFlashAttribute("error", "El Programa no existe");
			return "redirect:/programa/listar";
		}
		
		LineaInvestigacion lineaInvestigacion = new LineaInvestigacion();
		lineaInvestigacion.setPrograma(programa);
		
		model.put("lineasInvestigacion", lineasInvestigacion);
		model.put("lineaInvestigacion", lineaInvestigacion);
		model.put("titulo", "Formulario Linea Investigacion");
		return "programa/formLinea";
	}
	
	@GetMapping("/formLineaInvestigacion/{programaId}/{lineaInvestigacionId}")
	public String updateLineaInvestigacion(@PathVariable(value="programaId") Integer programaId, @PathVariable(value="lineaInvestigacionId") Integer lineaInvestigacionId,
			Map<String, Object> model) {
		
		List<LineaInvestigacion> lineasInvestigacion = lineaInvestigacionService.findByProgramaProgramaId(programaId);
		LineaInvestigacion lineaInvestigacion = null;
		Programa programa = null;
		
		if(programaId != null && programaId > 0) {
			programa = programaService.getByProgramaId(programaId);
			if(lineaInvestigacionId != null && lineaInvestigacionId > 0) {
				lineaInvestigacion = lineaInvestigacionService.findByProgramaProgramaIdAndLineaInvestigacionId(programaId, lineaInvestigacionId);
			}
		}
		
		lineaInvestigacion.setPrograma(programa);
		model.put("lineaInvestigacion", lineaInvestigacion);
		model.put("lineasInvestigacion", lineasInvestigacion);
		model.put("titulo", "Editar Linea Investigacion");
		return "programa/formLinea";
	}
	
	@GetMapping("/form/{lineaInvestigacionId}")
	public String update(@PathVariable(value="lineaInvestigacionId") Integer lineaInvestigacionId, Map<String, Object> model,
			RedirectAttributes flash) {
		
		LineaInvestigacion lineaInvestigacion = null;
		List<Programa> programas = programaService.findAllEnabled();
		
		if(lineaInvestigacionId != null && lineaInvestigacionId > 0) {
			
			lineaInvestigacion = lineaInvestigacionService.getByLineaInvestigacionId(lineaInvestigacionId);
			if(lineaInvestigacion == null) {
			
				flash.addFlashAttribute("error", "Linea Investigacion no encontrado");
				return "redirect:/linea/listar";
			}
		}
		else {
			
			flash.addFlashAttribute("error", "Linea de Investigacion no encontrado");
			return "redirect:/linea/listar";
		}
		
		model.put("programas", programas);
		model.put("lineaInvestigacion", lineaInvestigacion);
		model.put("titulo", "Editar Linea Investigacion");
		return "linea/form";
	}
	
	
	@GetMapping("/form")
	public String formLineaInvestigacion(Map<String, Object> model) {
		
		List<Programa> programas = programaService.findAllEnabled();
		LineaInvestigacion lineaInvestigacion = new LineaInvestigacion();
		model.put("programas", programas);
		model.put("titulo", "Formulario Linea Investigacion");
		model.put("lineaInvestigacion", lineaInvestigacion);
		return "linea/form";
		
	}
	
	@PostMapping("/formLineaInvestigacion")
	public String saveLineaInvestigacion(@Valid LineaInvestigacion lineaInvestigacion, SessionStatus status, 
			RedirectAttributes flash) {
		
		lineaInvestigacionService.saveOrUpdate(lineaInvestigacion);
		status.setComplete();
		flash.addFlashAttribute("success", "Linea Investigacion Creada con exito");
		return "redirect:/linea/formLineaInvestigacion/" + lineaInvestigacion.getPrograma().getProgramaId();
		
	}
	
	@GetMapping("/enabled/{lineaInvestigacionId}")
	public String enabled(@PathVariable(value="lineaInvestigacionId") Integer lineaInvestigacionId) {
		
		LineaInvestigacion lineaInvestigacion = null;
		if(lineaInvestigacionId != null && lineaInvestigacionId > 0) {
			
			lineaInvestigacionService.enabled(lineaInvestigacionId);
			lineaInvestigacion = lineaInvestigacionService.getByLineaInvestigacionId(lineaInvestigacionId);
		}
		
		return "redirect:/linea/formLineaInvestigacion/" + lineaInvestigacion.getPrograma().getProgramaId();
	}
	
	@GetMapping("/delete/{lineaInvestigacionId}")
	public String delete(@PathVariable(value="lineaInvestigacionId") Integer lineaInvestigacionId) {
		
		LineaInvestigacion lineaInvestigacion = null;
		if(lineaInvestigacionId != null && lineaInvestigacionId > 0) {
			
			lineaInvestigacionService.delete(lineaInvestigacionId);
			lineaInvestigacion = lineaInvestigacionService.getByLineaInvestigacionId(lineaInvestigacionId);
		}
		
		return "redirect:/linea/formLineaInvestigacion/" + lineaInvestigacion.getPrograma().getProgramaId();
	}
	
	
	@GetMapping("/detail/{lineaInvestigacionId}")
	public String detalleLineaInvestigacion(@PathVariable(value="lineaInvestigacionId") Integer lineaInvestigacionId, Map<String, Object> model) {
		
		LineaInvestigacion lineaInvestigacion = null;
		
		if(lineaInvestigacionId != null &&
				lineaInvestigacionId > 0) {
			lineaInvestigacion = lineaInvestigacionService.getByLineaInvestigacionId(lineaInvestigacionId);
		}
		
		model.put("titulo", "Linea Invetigacion: " + lineaInvestigacion.getNombreLineaInvestigacion());
		model.put("lineaInvestigacion", lineaInvestigacion);
		return "linea/ver";
		
	}
	
	@GetMapping("/cargar-lineas/{termino}")
	@ResponseBody List<LineaInvestigacion> cargarLineas(@PathVariable(value="termino") String termino) {
		
		List<LineaInvestigacion> lineasInvestigacion = lineaInvestigacionService.findByNombreLineaInvestigacion(termino);
		return lineasInvestigacion;
	}
}
