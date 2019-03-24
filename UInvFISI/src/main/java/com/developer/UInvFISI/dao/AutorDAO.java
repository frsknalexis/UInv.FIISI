package com.developer.UInvFISI.dao;

import java.util.List;

import com.developer.UInvFISI.entity.Autor;

public interface AutorDAO {

	List<Autor> findAll();
	
	void save(Autor autor);
	
	void  update(Autor autor);
	
	Autor findOne(Integer autorId);
	
	void disabled(Autor autor);
	
	
}
