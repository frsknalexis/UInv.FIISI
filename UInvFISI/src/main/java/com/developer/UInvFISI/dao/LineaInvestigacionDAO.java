package com.developer.UInvFISI.dao;

import java.util.List;

import com.developer.UInvFISI.entity.LineaInvestigacion;

public interface LineaInvestigacionDAO {

	List<LineaInvestigacion> findAll();
	
	List<LineaInvestigacion> findAllEnabled();
	
	void save(LineaInvestigacion lineaInvestigacion);
	
	void update(LineaInvestigacion lineaInvestigacion);
	
	LineaInvestigacion findOne(Integer lineaInvestigacionId);
	
	void disabled(LineaInvestigacion lineaInvestigacion);
	
	void delete(Integer lineaInvestigacionId);
	
}
