package com.developer.UInvFISI.controllers;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.developer.UInvFISI.entity.Usuario;
import com.developer.UInvFISI.security.services.UsuarioService;


@Controller
public class LoginController {

	@Autowired
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	@GetMapping("/")
	String index() {
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login(Principal principal, Map<String, Object> model) {
		
		if(principal != null) {
			
			Usuario usuario = usuarioService.findByEmail(principal.getName());
			model.put("usuario", usuario);
			return "redirect:/inicio";
		}
		
		return "login";
	}
}
