package com.developer.UInvFISI.service;

import java.util.List;

import com.developer.UInvFISI.entity.InformeTrimestral;

public interface InformeTrimestralService {

	List<InformeTrimestral> findAll();
	
	InformeTrimestral getInformeTrimestralById(Integer informeTrimestralId);
	
	void saveOrUpdate(InformeTrimestral informeTrimestral);
	
	List<InformeTrimestral> findByAsignacionDetalleAsignacionDetalleId(Integer asignacionDetalleId);
	
	List<InformeTrimestral> findByAsignacionDetalleAsignacionAsignacionId(Integer asignacionId);
	
	InformeTrimestral findByAsignacionDetalleAsignacionDetalleIdAndInformeTrimestralId(Integer asignacionDetalleId, Integer informeTrimestralId);
}
