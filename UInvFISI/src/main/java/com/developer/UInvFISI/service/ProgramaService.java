package com.developer.UInvFISI.service;

import java.util.List;

import com.developer.UInvFISI.entity.Programa;

public interface ProgramaService {

	List<Programa> findAll();
	
	void saveOrUpdate(Programa programa);
	
	Programa getByProgramaId(Integer programaId);
	
	void enabled(Integer programaId);
	
	void delete(Integer programaId);
	
	void remove(Integer programaId);
	
	List<Programa> findAllEnabled();
}
