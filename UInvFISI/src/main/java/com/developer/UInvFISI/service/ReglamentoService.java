package com.developer.UInvFISI.service;

import java.util.List;

import com.developer.UInvFISI.entity.Reglamento;

public interface ReglamentoService {

	List<Reglamento> findAll();
	
	Reglamento getByReglamentoId(Integer reglamentoId);
	
	void saveOrUpdate(Reglamento reglamento);
	
	
}
