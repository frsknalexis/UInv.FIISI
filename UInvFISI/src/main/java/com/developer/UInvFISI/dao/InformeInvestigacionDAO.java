package com.developer.UInvFISI.dao;

import java.util.List;

import com.developer.UInvFISI.entity.InformeInvestigacion;

public interface InformeInvestigacionDAO {

	List<InformeInvestigacion> findAll();
	
	void save(InformeInvestigacion informeInvestigacion);
	
	void update(InformeInvestigacion informeInvestigacion);
}
