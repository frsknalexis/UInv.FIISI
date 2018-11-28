package com.developer.UInvFISI.service;

import java.util.List;

import com.developer.UInvFISI.entity.LineaInvestigacion;

public interface LineaInvestigacionService {

	List<LineaInvestigacion> findAll();
	
	void saveOrUpdate(LineaInvestigacion lineaInvestigacion);
	
	LineaInvestigacion getByLineaInvestigacionId(Integer lineaInvestigacionId);
	
	void enabled(Integer lineaInvestigacionId);
	
	void delete(Integer lineaInvestigacionId);
	
	void remove(Integer lineaInvestigacionId);
	
	
}
