package com.developer.UInvFISI.service;

import java.util.List;

import com.developer.UInvFISI.entity.AsignaturaAlumno;

public interface AsignaturaAlumnoService {

	
	List<AsignaturaAlumno> findByAsignaturaId(Integer asignaturaId);
	
	AsignaturaAlumno getAsignaturaAlumnoById(Integer asignaturaDetalleId);
		
	
	List<AsignaturaAlumno> findAll();
	
	void saveOrUpdate(AsignaturaAlumno asignaturaAlumno);
	
	List<AsignaturaAlumno> findByAsignaturaAsignaturaId(Integer asignaturaId);
	
	AsignaturaAlumno findByAsignaturaAsignaturaIdAndAsignaturaDetalleId(Integer asignaturaId, Integer asignaturaDetalleId);
}
