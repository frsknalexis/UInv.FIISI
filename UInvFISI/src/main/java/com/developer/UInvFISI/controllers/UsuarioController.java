package com.developer.UInvFISI.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.developer.UInvFISI.entity.Usuario;
import com.developer.UInvFISI.security.services.UsuarioService;


@Controller
public class UsuarioController {
	
	@Autowired
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;

	@GetMapping("/register")
	String registerForm(Model model) {
		
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		return "usuario/registerForm";
	}
	
	@GetMapping("/perfil")
	String perfilUsuario(Authentication authentication, Map<String, Object> model) {
		
		Usuario usuario = usuarioService.findByEmail(authentication.getName());
		model.put("titulo", "Perfil Usuario");
		model.put("usuario", usuario);
		return "usuario/perfil";
	}
	
	@PostMapping("/register")
	String registerUser(@Valid Usuario usuario, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("bindingResult", bindingResult);
			return "usuario/registerForm";
		}
		
		if(usuarioService.isUserPresent(usuario.getEmail())) {
			model.addAttribute("exists", true);
			return "usuario/registerForm";
		}
		
		usuarioService.saveAdminUser(usuario);
		model.addAttribute("successMessage", "Registration success! Please sign in to Continue");
		return "usuario/registerForm";
	}
	
}
