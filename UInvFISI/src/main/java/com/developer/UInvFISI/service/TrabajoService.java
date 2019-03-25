package com.developer.UInvFISI.service;

import java.util.List;

import com.developer.UInvFISI.entity.Trabajo;

public interface TrabajoService {

	List<Trabajo> findAll();
	
	void saveOrUpdate(Trabajo trabajo);
	
	Trabajo getByTrabajoId(Integer trabajoId);
	
	Long obtenerTotalTrabajos();
	
	void enabled(Integer trabajoId);
	
	void delete(Integer trabajoId);
	
	void remove(Integer trabajoId);
}
