package com.developer.UInvFISI.dao;

import java.util.List;

import com.developer.UInvFISI.entity.Escuela;;

public interface EscuelaDAO {

	public List<Escuela> findAll();
	
	public void save(Escuela escuela);
	
	public void update(Escuela escuela);
	
	public Escuela findOne(Integer escuelaId);
	
	public void disabled(Escuela escuela);
	
	public void delete(Integer escuelaId);
	
	List<Escuela> findAllEnabled();
}
