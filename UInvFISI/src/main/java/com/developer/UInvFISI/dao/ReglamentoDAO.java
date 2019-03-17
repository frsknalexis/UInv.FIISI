package com.developer.UInvFISI.dao;

import java.util.List;


import com.developer.UInvFISI.entity.Reglamento;

public interface ReglamentoDAO {

	
	List<Reglamento> findAll();
	
	Reglamento findOne(Integer reglamentoId);
	
	void save(Reglamento reglamento);
	
	void update(Reglamento reglamento);
}
