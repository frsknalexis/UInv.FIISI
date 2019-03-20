package com.developer.UInvFISI.dao;

import java.util.List;

import com.developer.UInvFISI.entity.AsignacionDocente;

public interface AsignacionDocenteDAO {

	List<AsignacionDocente> findAll();
	
	List<AsignacionDocente> findByAsignacionIdAndHabilitado(Integer asignacionId);
	
	void save(AsignacionDocente asignacionDocente);
	
	void  update(AsignacionDocente asignacionDocente);
	
	AsignacionDocente findOne(Integer asignacionDetalleId);
	
	void disabled (AsignacionDocente asignacionDocente);
}
