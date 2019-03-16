package com.developer.UInvFISI.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.developer.UInvFISI.entity.Asignatura;
import com.developer.UInvFISI.entity.AsignaturaAlumno;

import com.developer.UInvFISI.entity.Documento;

import com.developer.UInvFISI.service.AsignaturaAlumnoService;
import com.developer.UInvFISI.service.AsignaturaService;

import com.developer.UInvFISI.service.DocumentoService;


@Controller
@RequestMapping("/asignaturaAlumno")
@SessionAttributes("asignaturaAlumno")
public class AsignaturaAlumnoController {

	private final static String UPLOAD_FOLDER = "C://Files//ArchivosAlumnos";
	
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
		
		List<AsignaturaAlumno> asignaturasAlumno = asignaturaAlumnoService.findByAsignaturaAsignaturaId(asignaturaId);
		List<Documento> documentos = documentoService.findAllEnabled();
		Asignatura asignatura = asignaturaService.getByAsignaturaId(asignaturaId);
		
		if(asignatura == null) {
			
			flash.addFlashAttribute("error", "La Asignatura no existe");
			return "redirect:/asignatura/list";
		}
		
		AsignaturaAlumno asignaturaAlumno = new AsignaturaAlumno();
		asignaturaAlumno.setAsignatura(asignatura);
		
		model.put("asignaturasAlumno", asignaturasAlumno);
		model.put("asignaturaAlumno", asignaturaAlumno);
		model.put("documentos", documentos);
		model.put("titulo", "Formulario Asignatura Alumno");
		model.put("subtitulo", "LISTADO DE ALUMNOS ASIGNADOS");
		return "asignatura/formAsignatura";
	}
	
	@GetMapping("/formAsignaturaDetalle/{asignaturaId}/{asignaturaDetalleId}")
	public String updateAsignaturaAlumno(@PathVariable(value="asignaturaDetalleId") Integer asignaturaDetalleId, @PathVariable(value="asignaturaId") Integer asignaturaId,
			Map<String, Object> model, RedirectAttributes flash) {
		
		List<AsignaturaAlumno> asignaturasAlumno = asignaturaAlumnoService.findByAsignaturaAsignaturaId(asignaturaId);
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
		model.put("asignaturasAlumno", asignaturasAlumno);
		model.put("titulo", "Editar Asignatura Alumno");
		return "asignatura/formAsignatura";
	}
	
	@PostMapping("/formAsignaturaDetalle")
	public String AsignaturaAlumno(@Valid AsignaturaAlumno asignaturaAlumno, @RequestParam("fileAlumno") MultipartFile file, 
			SessionStatus status, RedirectAttributes flash) {
		
		if(file.isEmpty()) {
			
			flash.addFlashAttribute("error", "Seleccione un archivo");
			return "redirect:/asignaturaAlumno/formAsignaturaDetalle/" + asignaturaAlumno.getAsignatura().getAsignaturaId();
		}
		
		try {
			
			
			byte[] bytes = file.getBytes();
			Path rutaCompleta = Paths.get(UPLOAD_FOLDER + "//" + file.getOriginalFilename());
			Files.write(rutaCompleta, bytes);
			flash.addFlashAttribute("info", "Ha subido correctamente el archivo: " + file.getOriginalFilename());
			
			asignaturaAlumno.setNombreFichero(file.getOriginalFilename());
			asignaturaAlumno.setTamanioFichero(Long.toString(file.getSize()));
			asignaturaAlumno.setFormatoFichero(file.getContentType());
			
			
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
		
		asignaturaAlumnoService.saveOrUpdate(asignaturaAlumno);
		status.setComplete();
		flash.addFlashAttribute("success", "Archivo Cargado con exito");
		return "redirect:/asignaturaAlumno/formAsignaturaDetalle/" + asignaturaAlumno.getAsignatura().getAsignaturaId();
	}
	
	@GetMapping("/download/{filename:.+}")
	@ResponseBody
	ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request) {
		
		Path rutaCompleta = Paths.get(UPLOAD_FOLDER + "//" + filename);
		Resource resource = null;
		try {
			
			resource = new UrlResource(rutaCompleta.toUri());
			if(!resource.exists() && !resource.isReadable()) {
				throw new RuntimeException("Error: no se puede leer el archivo: " + rutaCompleta.toString());
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String contentType = null;
		
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(contentType == null) {
			contentType = "application/octet-stream";
		}
		
		return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
 
	}
	
	@GetMapping("/view/{filename:.+}")
	@ResponseBody
	void viewFile(@PathVariable String filename, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		File file = new File(UPLOAD_FOLDER + "//" + filename);
		if (file.exists()) {

			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);

			/**
			 * Here we have mentioned it to show inline
			 */
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

			 //Here we have mentioned it to show as attachment
			 //response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

			response.setContentLength((int) file.length());

			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}
	
}
