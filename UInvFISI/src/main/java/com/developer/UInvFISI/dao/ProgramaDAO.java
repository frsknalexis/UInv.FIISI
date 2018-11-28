package com.developer.UInvFISI.dao;

import java.util.List;

import com.developer.UInvFISI.entity.Programa;

public interface ProgramaDAO {

	List<Programa> findAll();
	
	void save(Programa programa);
	
	void update(Programa programa);
	
	Programa findOne(Integer programaId);
	
	void disabled(Programa programa);
	
	void delete(Integer programaId);
	
	List<Programa> findAllEnabled();
}
