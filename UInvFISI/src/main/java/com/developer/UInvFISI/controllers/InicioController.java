package com.developer.UInvFISI.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.developer.UInvFISI.entity.Usuario;
import com.developer.UInvFISI.security.services.UsuarioService;

@Controller
public class InicioController {

	@Autowired
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	@GetMapping("/inicio")
	public String inicio(Authentication authentication, Map<String, Object> model) {
		
		Usuario usuario = usuarioService.findByEmail(authentication.getName());
		model.put("usuario", usuario);
		return "inicio";
	}
}
