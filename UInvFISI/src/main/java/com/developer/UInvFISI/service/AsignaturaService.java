package com.developer.UInvFISI.service;

import java.util.List;

import com.developer.UInvFISI.entity.Asignatura;

public interface AsignaturaService {

	List<Asignatura> findAll();
	
	void saveOrUpdate(Asignatura asignatura);
	
	Asignatura getByAsignaturaId(Integer asignaturaId);
	
	Long obtenerTotalAsignaturas();
	
	void enabled(Integer asignaturaId);
	
	void delete(Integer asignaturaId);
	
	void remove(Integer asignaturaId);
}
