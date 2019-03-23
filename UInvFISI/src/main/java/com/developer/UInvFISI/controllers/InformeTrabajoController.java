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

import com.developer.UInvFISI.entity.Trabajo;
import com.developer.UInvFISI.entity.InformeTrabajo;
import com.developer.UInvFISI.service.TrabajoService;
import com.developer.UInvFISI.service.InformeTrabajoService;

@Controller
@RequestMapping("/archivo")
@SessionAttributes("informeTrabajo")
public class InformeTrabajoController {

	private final static String UPLOAD_FOLDER = "C://Files//tesis";
	
	@Autowired
	@Qualifier("informeTrabajoService")
	private InformeTrabajoService informeTrabajoService;
	
	@Autowired
	@Qualifier("trabajoService")
	private TrabajoService trabajoService;
	
	@GetMapping("/formInformeTrabajo/{trabajoId}")
	public String createInformeTrabajo(@PathVariable(value="trabajoId") Integer trabajoId, Map<String, Object> model,
			RedirectAttributes flash) {
		
		List<InformeTrabajo> informesTrabajo = informeTrabajoService.findByTrabajoTrabajoId(trabajoId);
		Trabajo trabajo = trabajoService.getByTrabajoId(trabajoId);
		if(trabajo == null) {
			
			flash.addFlashAttribute("error", "La Tesis no existe");
			return "redirect:/trabajo/list";
		}
		
		InformeTrabajo informeTrabajo = new InformeTrabajo();
		informeTrabajo.setTrabajo(trabajo);
		
		model.put("informesTrabajo", informesTrabajo);
		model.put("informeTrabajo", informeTrabajo);
		model.put("titulo", "Cargar Tesis");
		return "archivo/form";
	}
	
	@GetMapping("/formInformeTrabajo/{trabajoId}/{informeTrabajoId}")
	public String updateInformeTrabajo(@PathVariable(value="trabajoId") Integer trabajoId, 
			@PathVariable(value="informeTrabajoId") Integer informeTrabajoId, Map<String, Object> model) {
		
		List<InformeTrabajo> informesTrabajo = informeTrabajoService.findByTrabajoTrabajoId(trabajoId);
		Trabajo trabajo = null;
		InformeTrabajo informeTrabajo = null;
		
		if(trabajoId != null && trabajoId > 0) {
			
			trabajo = trabajoService.getByTrabajoId(trabajoId);
			if(informeTrabajoId != null && informeTrabajoId > 0) {
				
				informeTrabajo = informeTrabajoService.findByTrabajoTrabajoIdAndInformeTrabajoId(trabajoId, informeTrabajoId);
			}
		}
		
		informeTrabajo.setTrabajo(trabajo);
		
		model.put("informesTrabajo", informesTrabajo);
		model.put("informeTrabajo", informeTrabajo);
		model.put("titulo", "Editar Informe");
		return "archivo/form";
	}
	
	@PostMapping("/formInformeTrabajo")
	public String saveInformeTrabajo(@Valid InformeTrabajo informeTrabajo, @RequestParam("fileArchivo") MultipartFile file, 
			SessionStatus status, RedirectAttributes flash) {
		
		if(file.isEmpty()) {
			
			flash.addFlashAttribute("error", "Seleccione un archivo");
			return "redirect:/archivo/formInformeTrabajo/" + informeTrabajo.getTrabajo().getTrabajoId();
		}
		
		try {
			
			
			byte[] bytes = file.getBytes();
			Path rutaCompleta = Paths.get(UPLOAD_FOLDER + "//" + file.getOriginalFilename());
			Files.write(rutaCompleta, bytes);
			flash.addFlashAttribute("info", "Ha subido correctamente el archivo: " + file.getOriginalFilename());
			
			informeTrabajo.setNombreFichero(file.getOriginalFilename());
			informeTrabajo.setTamanioFichero(Long.toString(file.getSize()));
			informeTrabajo.setFormatoFichero(file.getContentType());
			
			
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
		
		informeTrabajoService.saveOrUpdate(informeTrabajo);
		status.setComplete();
		flash.addFlashAttribute("success", "Archivo Cargado con exito");
		return "redirect:/archivo/formInformeTrabajo/" + informeTrabajo.getTrabajo().getTrabajoId();
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
