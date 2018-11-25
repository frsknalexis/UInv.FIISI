package com.developer.UInvFISI.dao;

import java.util.List;

import com.developer.UInvFISI.entity.Facultad;

public interface FacultadDAO {

	public List<Facultad> findAll();
	
	public void save(Facultad facultad);
	
	public void update(Facultad facultad);
	
	public Facultad findOne(Integer facultadId);
	
	public void disabled(Facultad facultad);
	
	public void delete(Integer facultadId);
	
	List<Facultad> findAllEnabled();
}
