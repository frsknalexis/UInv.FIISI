package com.developer.UInvFISI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.entity.Rol;

@Repository("rolRepository")
public interface RolRepository extends JpaRepository<Rol, Integer> {

	Rol findByNombre(String nombre);
	
	List<Rol> findByUsuariosEmail(String email);
}
