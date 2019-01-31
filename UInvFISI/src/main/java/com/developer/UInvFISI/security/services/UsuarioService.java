package com.developer.UInvFISI.security.services;

import com.developer.UInvFISI.entity.Usuario;

public interface UsuarioService {

	void saveAdminUser(Usuario usuario);
	
	void saveSuperUser(Usuario usuario);
	
	boolean isUserPresent(String email);
	
	Usuario findByEmail(String email);
}
