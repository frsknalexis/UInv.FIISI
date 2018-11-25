package com.developer.UInvFISI.service;

import java.util.List;

import com.developer.UInvFISI.entity.Condicion;

public interface CondicionService {

	public List<Condicion> findAll();
	
	public void saveOrUpdate(Condicion condicion);
	
	Condicion getByCondicionId(Integer condicionId);
	
	void enabled(Integer condicionId);
	
	void delete(Integer condicionId);
	
	void remove(Integer condicionId);
	
	List<Condicion> findAllEnabled();
}
