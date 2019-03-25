package com.developer.UInvFISI.service;

import java.util.List;

import com.developer.UInvFISI.entity.Asignacion;

public interface AsignacionService {

	List<Asignacion> findAll();
	
	List<Asignacion> findAllEnabled();
	
	void saveOrUpdate(Asignacion asignacion);
	
	Asignacion getByAsignacionId(Integer asignacionId);
	
	Long obtenerTotalRegistrosAsignacion();
	
	void enabled(Integer asignacionId);
	
	void delete(Integer asignacionId);
	
	void remove(Integer asignacionId);
}
