package com.developer.UInvFISI.service;

import java.util.List;

import com.developer.UInvFISI.entity.InformeInvestigacion;

public interface InformeInvestigacionService {

	List<InformeInvestigacion> findAll();
	
	InformeInvestigacion getByInformeAsignacionId(Integer informeAsignacionId);
	
	void saveOrUpdate(InformeInvestigacion informeInvestigacion);
	
	List<InformeInvestigacion> findByAsignacionAsignacionId(Integer asignacionId);
	
	InformeInvestigacion findByAsignacionAsignacionIdAndInformeAsignacionId(Integer asignacionId, Integer informeAsignacionId);
}
