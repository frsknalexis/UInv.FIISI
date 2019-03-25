package com.developer.UInvFISI.dao;

import java.util.List;

import com.developer.UInvFISI.entity.Docente;

public interface DocenteDAO {

	List<Docente> findAll();
	
	void save(Docente docente);
	
	void update(Docente docente);
	
	Docente findOne(Integer docenteId);
	
	Long obtenerTotalRegistrosDocentes();
	
	void disabled(Docente docente);
	
	void delete(Integer docenteId);
	
	List<Docente> findAllEnabled();
}
