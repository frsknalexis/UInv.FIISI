package com.developer.UInvFISI.dao;

import java.util.List;

import com.developer.UInvFISI.entity.Asignacion;

public interface AsignacionDAO {
	
	List<Asignacion> findAll();
	
	List<Asignacion> findAllEnabled();
	
	void save(Asignacion asignacion);
	
	void update(Asignacion asignacion);
	
	Long obtenerTotalRegistrosAsignacion();
	
	Asignacion findOne(Integer asignacionId);
	
	void disabled(Asignacion asignacion);
	
	void delete(Integer asignacionId);
	
}
