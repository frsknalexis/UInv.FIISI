package com.developer.UInvFISI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.entity.AsignaturaAlumno;

@Repository("asignaturaAlmRepository")
public interface AsignaturaAlumnoRepository extends JpaRepository<AsignaturaAlumno, Integer>{

	
	List<AsignaturaAlumno> findByAsignaturaAsignaturaId(Integer asignaturaId);
	
	AsignaturaAlumno findByAsignaturaAsignaturaIdAndAsignaturaDetalleId(Integer asignaturaId, Integer asignaturaDetalleId);
	
}
