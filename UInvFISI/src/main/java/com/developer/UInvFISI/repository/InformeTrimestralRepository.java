package com.developer.UInvFISI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.entity.InformeTrimestral;

@Repository("informeTrimestralRpty")
public interface InformeTrimestralRepository extends JpaRepository<InformeTrimestral, Integer> {
	
	List<InformeTrimestral> findByAsignacionDetalleAsignacionDetalleId(Integer asignacionDetalleId);

	List<InformeTrimestral> findByAsignacionDetalleAsignacionAsignacionId(Integer asignacionId);
	
	InformeTrimestral findByAsignacionDetalleAsignacionDetalleIdAndInformeTrimestralId(Integer asignacionDetalleId, Integer informeTrimestralId);
}
