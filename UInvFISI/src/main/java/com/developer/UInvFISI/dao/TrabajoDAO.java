package com.developer.UInvFISI.dao;

import java.util.List;

import com.developer.UInvFISI.entity.Trabajo;

public interface TrabajoDAO {
	
	List<Trabajo> findAll();
	
	void save(Trabajo trabajo);
	
	void update(Trabajo trabajo);
	
	Long obtenerTotalTrabajos();
	
	Trabajo findOne(Integer trabajoId);
	
	void disabled(Trabajo trabajo);
	
	void delete(Integer trabajoId);
	
}
