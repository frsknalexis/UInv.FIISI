package com.developer.UInvFISI.service;

import java.util.List;

import com.developer.UInvFISI.entity.Escuela;


public interface EscuelaService {

	public List<Escuela> findAll();
	
	public void saveOrUpdate(Escuela escuela);
	
	public Escuela getByCodigo(Integer escuelaId);
	
	public void enabled(Integer escuelaId);
	
	public void delete(Integer escuelaId);
	
	public void delete2(Integer escuelaId);
	
	List<Escuela> findAllEnabled();
}
