package com.developer.UInvFISI.dao;

import java.util.List;

import com.developer.UInvFISI.entity.InformeTrabajo;

public interface InformeTrabajoDAO {

	List<InformeTrabajo> findAll();
	
	void save(InformeTrabajo informeTrabajo);
	
	void update(InformeTrabajo informeTrabajo);
}
