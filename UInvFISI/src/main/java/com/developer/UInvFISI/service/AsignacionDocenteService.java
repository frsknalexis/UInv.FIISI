package com.developer.UInvFISI.service;

import java.util.List;

import com.developer.UInvFISI.entity.AsignacionDocente;

public interface AsignacionDocenteService {

	List<AsignacionDocente> findAll();
	
	List<AsignacionDocente> findByAsignacionId(Integer asignacionId);
	
	List<AsignacionDocente> findByAsignacionIdAndHabilitado(Integer asignacionId);
	
	void saveOrUpdate(AsignacionDocente asignacionDocente);
	
	void disabled(Integer asignacionDetalleId);
	
	void enabled(Integer asignacionDetalleId);
	
	AsignacionDocente getAsignacionDocenteById(Integer asignacionDetalleId);
	
	AsignacionDocente findByAsignacionAsignacionIdAndAsignacionDetalleId(Integer asignacionId, Integer asignacionDetalleId);
}
