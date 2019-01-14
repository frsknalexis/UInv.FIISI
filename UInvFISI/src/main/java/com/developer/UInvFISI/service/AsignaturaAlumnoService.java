package com.developer.UInvFISI.service;

import java.util.List;

import com.developer.UInvFISI.entity.AsignaturaAlumno;;

public interface AsignaturaAlumnoService {

	List<AsignaturaAlumno> findAll();
	
	List<AsignaturaAlumno> findByAsignaturaId(Integer asignaturaId);
	
	void saveOrUpdate(AsignaturaAlumno asignaturaAlumno);
	
	AsignaturaAlumno getAsignaturaAlumnoById(Integer asignaturaDetalleId);
	
	AsignaturaAlumno findByAsignaturaAsignaturaIdAndAsignaturaDetalleId(Integer asignaturaId, Integer asignaturaDetalleId);
}
