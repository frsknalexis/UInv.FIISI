package com.developer.UInvFISI.service;

import java.util.List;

import com.developer.UInvFISI.entity.Autor;

public interface AutorService {

	List<Autor> findAll();
	
	List<Autor> findByTrabajoId(Integer trabajoId);
	
	void saveOrUpdate(Autor autor);
	
	void disabled(Integer autorId);
	
	void enabled(Integer autorId);
	
	Autor getAutorById(Integer autorDetalleId);
	
	Autor findByTrabajoTrabajoIdAndAutorDetalleId(Integer TrabajoId, Integer autorDetalleId);
}
