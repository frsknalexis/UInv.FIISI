package com.developer.UInvFISI.dao;

import java.util.List;

import com.developer.UInvFISI.entity.InformeTrimestral;

public interface InformeTrimestralDAO {

	List<InformeTrimestral> findAll();
	
	InformeTrimestral findOne(Integer informeTrimestralId);
	
	void save(InformeTrimestral informeTrimestral);
	
	void update(InformeTrimestral informeTrimestral);
}
