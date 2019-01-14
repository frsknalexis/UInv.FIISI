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
import com.developer.UInvFISI.entity.Asignatura;
import com.developer.UInvFISI.entity.AsignaturaAlumno;
import com.developer.UInvFISI.entity.Condicion;
import com.developer.UInvFISI.entity.Documento;
import com.developer.UInvFISI.entity.Facultad;
import com.developer.UInvFISI.service.AsignacionDocenteService;
import com.developer.UInvFISI.service.AsignacionService;
import com.developer.UInvFISI.service.AsignaturaAlumnoService;
import com.developer.UInvFISI.service.AsignaturaService;
import com.developer.UInvFISI.service.CondicionService;
import com.developer.UInvFISI.service.DocumentoService;
import com.developer.UInvFISI.service.FacultadService;

@Controller
@RequestMapping("/asignaturaAlumno")
@SessionAttributes("asignaturaAlumno")
public class AsignaturaAlumnoController {

	
	@Autowired
	@Qualifier("asignaturaService")
	private AsignaturaService asignaturaService;
	
	@Autowired
	@Qualifier("documentoService")
	private DocumentoService documentoService;
	
	@Autowired
	@Qualifier("asignaturaAlumnoService")
	private AsignaturaAlumnoService asignaturaAlumnoService;
	
	@GetMapping("/formAsignaturaDetalle/{asignaturaId}")
	public String createAsignaturaAlumno(@PathVariable(value="asignaturaId") Integer asignaturaId, Map<String, Object> model,
			RedirectAttributes flash) {
		
		List<AsignaturaAlumno> asignaturaAlumnos = asignaturaAlumnoService.findByAsignaturaId(asignaturaId);
		
		List<Documento> documentos = documentoService.findAllEnabled();
		
		Asignatura asignatura = asignaturaService.getByAsignaturaId(asignaturaId);
		
		if(asignatura == null) {
			
			flash.addFlashAttribute("error", "La Asignatura no existe");
			return "redirect:/asignatura/list";
		}
		
		AsignaturaAlumno asignaturaAlumno = new AsignaturaAlumno();
		asignaturaAlumno.setAsignatura(asignatura);
		
		model.put("asignaturaAlumno", asignaturaAlumno);
		model.put("documentos", documentos);
		model.put("asignaturaAlumnos", asignaturaAlumnos);
		model.put("titulo", "Formulario Asignatura Alumno");
		model.put("subtitulo","LISTADO DE ALUMNOS ASIGNADOS A" + ' ' + asignatura.getNombreAsignatura());
		return "asignatura/formAsignatura";
	}
	
	@GetMapping("/formAsignaturaDetalle/{asignaturaId}/{asignaturaDetalleId}")
	public String updateAsignaturaAlumno(@PathVariable(value="asignaturaDetalleId") Integer asignaturaDetalleId, @PathVariable(value="asignaturaId") Integer asignaturaId,
			Map<String, Object> model, RedirectAttributes flash) {
		
		List<Documento> documentos = documentoService.findAllEnabled();
		AsignaturaAlumno asignaturaAlumno = null;
		Asignatura asignatura = null;
		
		if(asignaturaId != null && asignaturaId > 0) {
			
			asignatura = asignaturaService.getByAsignaturaId(asignaturaId);
			if(asignaturaDetalleId != null && asignaturaDetalleId > 0) {
				asignaturaAlumno = asignaturaAlumnoService.findByAsignaturaAsignaturaIdAndAsignaturaDetalleId(asignaturaId, asignaturaDetalleId);
			}
		}
		
		asignaturaAlumno.setAsignatura(asignatura);
		
		
		model.put("documentos", documentos);
		model.put("asignaturaAlumno", asignaturaAlumno);
		model.put("titulo", "Editar Asignatura Alumno");
		return "asignatura/formAsignatura";
	}
	
	@PostMapping("/formAsignaturaDetalle")
	public String saveAsignaturaAlumno(@Valid AsignaturaAlumno asignaturaAlumno, SessionStatus status,
			RedirectAttributes flash) {
		
		asignaturaAlumnoService.saveOrUpdate(asignaturaAlumno);
		status.setComplete();
		flash.addFlashAttribute("success", "Alumno Asignado con exito");
		return "redirect:/asignaturaAlumno/formAsignaturaDetalle/" + asignaturaAlumno.getAsignatura().getAsignaturaId();
	}
}
