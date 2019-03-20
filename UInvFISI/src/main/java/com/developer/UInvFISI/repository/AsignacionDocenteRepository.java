package com.developer.UInvFISI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.entity.AsignacionDocente;

@Repository("asignacionDctRepository")
public interface AsignacionDocenteRepository extends JpaRepository<AsignacionDocente, Integer> {
	
	List<AsignacionDocente> findByAsignacionAsignacionId(Integer asignacionId);
	
	AsignacionDocente findByAsignacionAsignacionIdAndAsignacionDetalleId(Integer asignacionId, Integer asignacionDetalleId);
	
}
