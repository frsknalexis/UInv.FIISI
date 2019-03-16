package com.developer.UInvFISI.dao;

import java.util.List;

import com.developer.UInvFISI.entity.AsignaturaAlumno;


public interface AsignaturaAlumnoDAO {

	List<AsignaturaAlumno> findAll();
	
	void save(AsignaturaAlumno asignaturaAlumno);
	
	void update(AsignaturaAlumno  asignaturaAlumno);
	
	AsignaturaAlumno findOne(Integer asignaturaDetalleId);
}
