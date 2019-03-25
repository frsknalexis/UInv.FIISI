package com.developer.UInvFISI.service;

import java.util.List;

import com.developer.UInvFISI.entity.Docente;

public interface DocenteService {

	List<Docente> findAll();
	
	void saveOrUpdate(Docente docente);
	
	Docente getByDocenteId(Integer docenteId);
	
	void enabled(Integer docenteId);
	
	void delete(Integer docenteId);
	
	void remove(Integer docenteId);
	
	Long obtenerTotalRegistrosDocentes();
	
	List<Docente> findAllEnabled();
	
	List<Docente> findByNombresDocente(String termino);
}
