package com.developer.UInvFISI.service;

import java.util.List;

import com.developer.UInvFISI.entity.RegimenDedicacion;

public interface RegimenDedicacionService {

	public List<RegimenDedicacion> findAll();
	
	public void saveOrUpdate(RegimenDedicacion regimenDedicacion);
	
	public RegimenDedicacion getByRegimenDedicacionId(Integer regimenDedicacionId);
	
	public void enabled(Integer regimenDedicacionId);
	
	public void delete(Integer regimenDedicacionId);
	
	public void remove(Integer regimenDedicacionId);
	
	List<RegimenDedicacion> findAllEnabled();
}
