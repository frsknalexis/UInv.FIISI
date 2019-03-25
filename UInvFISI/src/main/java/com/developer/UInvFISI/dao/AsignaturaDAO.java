package com.developer.UInvFISI.dao;

import java.util.List;

import com.developer.UInvFISI.entity.Asignatura;

public interface AsignaturaDAO {

	List<Asignatura> findAll();
	
	void save(Asignatura asignatura);
	
	void update(Asignatura asignatura);
	
	Long obtenerTotalAsignaturas();
	
	Asignatura findOne(Integer asignaturaId);
	
	void disabled(Asignatura asignatura);
	
	void delete(Integer asignaturaId);
}
