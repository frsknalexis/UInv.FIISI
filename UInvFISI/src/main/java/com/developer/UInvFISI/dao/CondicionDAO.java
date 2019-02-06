package com.developer.UInvFISI.dao;

import java.util.List;

import com.developer.UInvFISI.entity.Condicion;

public interface CondicionDAO {

	public List<Condicion> findAll();
	
	public void save(Condicion condicion);
	
	public void update(Condicion condicion);
	
	public Condicion findOne(Integer condicionId);
	
	public void disabled(Condicion condicion);
	
	public void delete(Integer condicionId);
	
	List<Condicion> findAllEnabled();
}
