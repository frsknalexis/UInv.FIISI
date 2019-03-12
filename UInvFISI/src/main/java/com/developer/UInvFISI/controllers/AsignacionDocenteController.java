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

import com.developer.UInvFISI.entity.Asignacion;
import com.developer.UInvFISI.entity.AsignacionDocente;
import com.developer.UInvFISI.entity.Condicion;
import com.developer.UInvFISI.entity.Facultad;
import com.developer.UInvFISI.entity.InformeTrimestral;
import com.developer.UInvFISI.service.AsignacionDocenteService;
import com.developer.UInvFISI.service.AsignacionService;
import com.developer.UInvFISI.service.CondicionService;
import com.developer.UInvFISI.service.FacultadService;
import com.developer.UInvFISI.service.InformeTrimestralService;

@Controller
@RequestMapping("/asignacionDocente")
@SessionAttributes("asignacionDocente")
public class AsignacionDocenteController {

	@Autowired
	@Qualifier("asignacionService")
	private AsignacionService asignacionService;
	
	@Autowired
	@Qualifier("condicionService")
	private CondicionService condicionService;
	
	@Autowired
	@Qualifier("facultadService")
	private FacultadService facultadService;
	
	@Autowired
	@Qualifier("asignacionDocenteService")
	private AsignacionDocenteService asignacionDocenteService;
	
	@Autowired
	@Qualifier("informeTrimestralService")
	private InformeTrimestralService informeTrimestralService;
	
	@GetMapping("/formAsignacionDetalle/{asignacionId}")
	public String createAsignacionDocente(@PathVariable(value="asignacionId") Integer asignacionId, Map<String, Object> model,
			RedirectAttributes flash) {
		
		List<Facultad> facultades = facultadService.findAllEnabled();
		List<AsignacionDocente> asignacionDocentes = asignacionDocenteService.findByAsignacionId(asignacionId);
		List<InformeTrimestral> informesTrimestrales = informeTrimestralService.findByAsignacionDetalleAsignacionAsignacionId(asignacionId);
		List<Condicion> condiciones = condicionService.findAllEnabled();
		Asignacion asignacion = asignacionService.getByAsignacionId(asignacionId);
		
		if(asignacion == null) {
			
			flash.addFlashAttribute("error", "La Investigacion no existe");
			return "redirect:/asignacion/list";
		}
		
		AsignacionDocente asignacionDocente = new AsignacionDocente();
		asignacionDocente.setAsignacion(asignacion);
		
		model.put("asignacionDocente", asignacionDocente);
		model.put("facultades", facultades);
		model.put("condiciones", condiciones);
		model.put("asignacionDocentes", asignacionDocentes);
		model.put("informesTrimestrales", informesTrimestrales);
		model.put("titulo", "Formulario Asignacion Investigador");
		return "asignacion/formAsignacion";
	}
	
	@GetMapping("/formAsignacionDetalle/{asignacionId}/{asignacionDetalleId}")
	public String updateAsignacionDocente(@PathVariable(value="asignacionDetalleId") Integer asignacionDetalleId, @PathVariable(value="asignacionId") Integer asignacionId,
			Map<String, Object> model, RedirectAttributes flash) {
		
		List<AsignacionDocente> asignacionDocentes = asignacionDocenteService.findByAsignacionId(asignacionId);
		List<InformeTrimestral> informesTrimestrales = informeTrimestralService.findByAsignacionDetalleAsignacionAsignacionId(asignacionId);
		List<Facultad> facultades = facultadService.findAllEnabled();
		List<Condicion> condiciones = condicionService.findAllEnabled();
		AsignacionDocente asignacionDocente = null;
		Asignacion asignacion = null;
		
		if(asignacionId != null && asignacionId > 0) {
			
			asignacion = asignacionService.getByAsignacionId(asignacionId);
			if(asignacionDetalleId != null && asignacionDetalleId > 0) {
				asignacionDocente = asignacionDocenteService.findByAsignacionAsignacionIdAndAsignacionDetalleId(asignacionId, asignacionDetalleId);
			}
		}
		
		asignacionDocente.setAsignacion(asignacion);
		
		model.put("asignacionDocentes", asignacionDocentes);
		model.put("informesTrimestrales", informesTrimestrales);
		model.put("facultades", facultades);
		model.put("condiciones", condiciones);
		model.put("asignacionDocente", asignacionDocente);
		model.put("titulo", "Editar Asignacion Investigador");
		return "asignacion/formAsignacion";
	}
	
	@PostMapping("/formAsignacionDetalle")
	public String saveAsignacionDocente(@Valid AsignacionDocente asignacionDocente, SessionStatus status,
			RedirectAttributes flash) {
		
		asignacionDocenteService.saveOrUpdate(asignacionDocente);
		status.setComplete();
		flash.addFlashAttribute("success", "Docente Asignado con exito");
		return "redirect:/asignacionDocente/formAsignacionDetalle/" + asignacionDocente.getAsignacion().getAsignacionId();
	}
}
