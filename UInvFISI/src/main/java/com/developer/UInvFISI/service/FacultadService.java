package com.developer.UInvFISI.service;

import java.util.List;

import com.developer.UInvFISI.entity.Facultad;;

public interface FacultadService {
	
	public List<Facultad> findAll();
	
	public void saveOrUpdate(Facultad facultad);
	
	public Facultad getByCodigo(Integer facultadId);
	
	public void enabled(Integer facultadId);
	
	public void delete(Integer facultadId);
	
	public void delete2(Integer facultadId);
	
	List<Facultad> findAllEnabled();
}
