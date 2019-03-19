package com.developer.UInvFISI.dao;

import java.util.List;

import com.developer.UInvFISI.entity.InformeInvestigacion;

public interface InformeInvestigacionDAO {

	List<InformeInvestigacion> findAll();
	
	InformeInvestigacion findOne(Integer informeAsignacionId);
	
	void save(InformeInvestigacion informeInvestigacion);
	
	void update(InformeInvestigacion informeInvestigacion);
}
