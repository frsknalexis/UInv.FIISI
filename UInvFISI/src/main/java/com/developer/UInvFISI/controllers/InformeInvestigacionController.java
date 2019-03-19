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

import com.developer.UInvFISI.entity.Asignacion;
import com.developer.UInvFISI.entity.InformeInvestigacion;
import com.developer.UInvFISI.service.AsignacionService;
import com.developer.UInvFISI.service.InformeInvestigacionService;

@Controller
@RequestMapping("/informe")
@SessionAttributes("informeInvestigacion")
public class InformeInvestigacionController {

	private final static String UPLOAD_FOLDER = "C://Files//uploads";
	
	@Autowired
	@Qualifier("informeInvestigacionService")
	private InformeInvestigacionService informeInvestigacionService;
	
	@Autowired
	@Qualifier("asignacionService")
	private AsignacionService asignacionService;
	
	@GetMapping("/formInformeInvestigacion/{asignacionId}")
	public String createInformeInvestigacion(@PathVariable(value="asignacionId") Integer asignacionId, Map<String, Object> model,
			RedirectAttributes flash) {
		
		List<InformeInvestigacion> informesInvestigacion = informeInvestigacionService.findByAsignacionAsignacionId(asignacionId);
		Asignacion asignacion = asignacionService.getByAsignacionId(asignacionId);
		if(asignacion == null) {
			
			flash.addFlashAttribute("error", "La investigacion no existe");
			return "redirect:/asignacion/list";
		}
		
		InformeInvestigacion informeInvestigacion = new InformeInvestigacion();
		informeInvestigacion.setAsignacion(asignacion);
		
		model.put("informesInvestigacion", informesInvestigacion);
		model.put("informeInvestigacion", informeInvestigacion);
		model.put("titulo", "Cargar Informe");
		return "informe/form";
	}
	
	@GetMapping("/formInformeInvestigacion/{asignacionId}/{informeAsignacionId}")
	public String updateInformeInvestigacion(@PathVariable(value="asignacionId") Integer asignacionId, 
			@PathVariable(value="informeAsignacionId") Integer informeAsignacionId, Map<String, Object> model, RedirectAttributes flash) {
		
		List<InformeInvestigacion> informesInvestigacion = informeInvestigacionService.findByAsignacionAsignacionId(asignacionId);
		Asignacion asignacion = null;
		InformeInvestigacion informeInvestigacion = null;
		
		if(asignacionId != null && asignacionId > 0) {
			
			asignacion = asignacionService.getByAsignacionId(asignacionId);
			if(informeAsignacionId != null && informeAsignacionId > 0) {
				informeInvestigacion = informeInvestigacionService.findByAsignacionAsignacionIdAndInformeAsignacionId(asignacionId, informeAsignacionId);
			}
		}
		
		informeInvestigacion.setAsignacion(asignacion);
		
		model.put("informesInvestigacion", informesInvestigacion);
		model.put("informeInvestigacion", informeInvestigacion);
		model.put("titulo", "Editar Informe");
		return "informe/form";
	}
	@PostMapping("/formInformeInvestigacion")
	public String saveInformeInvestigacion(@Valid InformeInvestigacion informeInvestigacion, @RequestParam("fileInforme") MultipartFile file, 
			SessionStatus status, RedirectAttributes flash) {
		
		if(file.isEmpty()) {
			
			flash.addFlashAttribute("error", "Seleccione un archivo");
			return "redirect:/informe/formInformeInvestigacion/" + informeInvestigacion.getAsignacion().getAsignacionId();
		}
		
		try {
			
			
			byte[] bytes = file.getBytes();
			Path rutaCompleta = Paths.get(UPLOAD_FOLDER + "//" + file.getOriginalFilename());
			Files.write(rutaCompleta, bytes);
			flash.addFlashAttribute("info", "Ha subido correctamente el archivo: " + file.getOriginalFilename());
			
			informeInvestigacion.setNombreFichero(file.getOriginalFilename());
			informeInvestigacion.setTamanioFichero(Long.toString(file.getSize()));
			informeInvestigacion.setFormatoFichero(file.getContentType());
			
			
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
		
		informeInvestigacionService.saveOrUpdate(informeInvestigacion);
		status.setComplete();
		flash.addFlashAttribute("success", "Informe Cargado con exito");
		return "redirect:/informe/formInformeInvestigacion/" + informeInvestigacion.getAsignacion().getAsignacionId();
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
