package com.developer.UInvFISI.service;

import java.util.List;

import com.developer.UInvFISI.entity.InformeTrabajo;

public interface InformeTrabajoService {

	List<InformeTrabajo> findAll();
	
	void saveOrUpdate(InformeTrabajo informeTrabajo);
	
	List<InformeTrabajo> findByTrabajoTrabajoId(Integer trabajoId);
}
