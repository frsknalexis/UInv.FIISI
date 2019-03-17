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
import org.springframework.ui.Model;
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


import com.developer.UInvFISI.entity.Reglamento;
import com.developer.UInvFISI.service.ReglamentoService;

@Controller
@RequestMapping("/reglamento")
@SessionAttributes("reglamento")
public class ReglamentoController {

	private final static String UPLOAD_FOLDER = "C://Files//Reglamentos";
	@Autowired
	@Qualifier("reglamentoService")
	private ReglamentoService reglamentoService;
	
	@GetMapping("/listar")
	public String listarDocumentos(Model model) {
		List<Reglamento> reglamentos = reglamentoService.findAll();
		model.addAttribute("titulo", "Listado de Reglamentos");
		model.addAttribute("reglamentos", reglamentos);
		return "reglamento/listar";
	}
	@GetMapping("/form")
	public String crear(Map<String, Object> model) {
		Reglamento reglamento = new Reglamento();
		model.put("reglamento", reglamento);
		model.put("titulo", "Formulario De Reglamento");
		return "reglamento/form";
	}
	
	@GetMapping("/form/{reglamentoId}")
	public String updateReglamento(@PathVariable(value="reglamentoId") Integer reglamentoId, Map<String, Object> model, RedirectAttributes flash) {
		
		Reglamento reglamento = null;
		if(reglamentoId != null && reglamentoId > 0) {
			
			reglamento = reglamentoService.getByReglamentoId(reglamentoId);
			if(reglamento == null) {
				flash.addFlashAttribute("error", "Reglamento no encontrado");
				return "redirect:/reglamento/listar";
			}
		}
		else {
			flash.addFlashAttribute("error", "Reglamento no encontrado");
			return "redirect:/reglamento/listar";
		}
		
		model.put("titulo", "Editar Reglamento");
		model.put("reglamento", reglamento);
		return "reglamento/form";
	}
	
	@PostMapping("/form")
	public String save(@Valid Reglamento reglamento, SessionStatus status,@RequestParam("fileReglamento") MultipartFile file,  RedirectAttributes flash) {

		if(file.isEmpty()) {
			
			flash.addFlashAttribute("error", "Seleccione un archivo");
			return "redirect:/reglamento/form/" + reglamento.getReglamentoId();
		}
		
		try {
			
			
			byte[] bytes = file.getBytes();
			Path rutaCompleta = Paths.get(UPLOAD_FOLDER + "//" + file.getOriginalFilename());
			Files.write(rutaCompleta, bytes);
			flash.addFlashAttribute("info", "Ha subido correctamente el archivo: " + file.getOriginalFilename());
			
			reglamento.setNombreFichero(file.getOriginalFilename());
			reglamento.setTamanioFichero(Long.toString(file.getSize()));
			reglamento.setFormatoFichero(file.getContentType());
			
			
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
		
		reglamentoService.saveOrUpdate(reglamento);
		status.setComplete();
		flash.addFlashAttribute("success", "Archivo Cargado con exito");
		return "redirect:/reglamento/listar/" ;
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
