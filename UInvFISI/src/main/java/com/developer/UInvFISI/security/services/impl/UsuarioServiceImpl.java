package com.developer.UInvFISI.security.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.developer.UInvFISI.entity.Rol;
import com.developer.UInvFISI.entity.Usuario;
import com.developer.UInvFISI.repository.RolRepository;
import com.developer.UInvFISI.repository.UsuarioRepository;
import com.developer.UInvFISI.security.services.UsuarioService;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	BCryptPasswordEncoder encoder;
	
	
	@Autowired
	@Qualifier("usuarioRepository")
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	@Qualifier("rolRepository")
	private RolRepository rolRepository;
	
	public Usuario findByEmail(String email) {
		
		Usuario usuario = usuarioRepository.findByEmail(email);
		return usuario;
	}

	public boolean isUserPresent(String email) {
		
		Usuario user = usuarioRepository.findByEmail(email);
		if(user != null) {
			return true;
		}
		return false;
	}

	@Override
	public void saveAdminUser(Usuario usuario) {
		
		usuario.setPassword(encoder.encode(usuario.getPassword()));
		Rol rol = rolRepository.findByNombre("ADMIN_USER");
		rol.setFechaRegistro(new Date());
		rol.setHabilitado(true);
		List<Rol> roles = new ArrayList<>();
		roles.add(rol);
		usuario.setRoles(roles);
		usuario.setFechaRegistro(new Date());
		usuario.setHabilitado(true);
		usuarioRepository.save(usuario);
	}

	@Override
	public void saveSuperUser(Usuario usuario) {
		// TODO Auto-generated method stub
		
	}
}
