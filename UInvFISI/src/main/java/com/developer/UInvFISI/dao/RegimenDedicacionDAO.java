package com.developer.UInvFISI.dao;

import java.util.List;

import com.developer.UInvFISI.entity.RegimenDedicacion;

public interface RegimenDedicacionDAO {

	public List<RegimenDedicacion> findAll();
	
	public void save(RegimenDedicacion regimenDedicacion);
	
	public  void update(RegimenDedicacion regimenDedicacion);
	
	public RegimenDedicacion findOne(Integer regimenDedicacionId);
	
	public void disabled(RegimenDedicacion regimenDedicacion);
	
	public void delete(Integer regimenDedicacionId);
	
	List<RegimenDedicacion> findAllEnabled();
	
}
