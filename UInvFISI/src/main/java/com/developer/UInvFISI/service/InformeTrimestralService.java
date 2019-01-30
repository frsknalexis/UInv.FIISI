package com.developer.UInvFISI.service;

import java.util.List;

import com.developer.UInvFISI.entity.InformeTrimestral;

public interface InformeTrimestralService {

	List<InformeTrimestral> findAll();
	
	void saveOrUpdate(InformeTrimestral informeTrimestral);
}
