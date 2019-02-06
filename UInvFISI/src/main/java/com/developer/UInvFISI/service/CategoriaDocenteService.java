package com.developer.UInvFISI.service;

import java.util.List;

import com.developer.UInvFISI.entity.CategoriaDocente;

public interface CategoriaDocenteService {

	public List<CategoriaDocente> findAll();
	
	public void saveOrUpdate(CategoriaDocente categoriaDocente);
	
	public CategoriaDocente getByCategoriaDocenteId(Integer categoriaDocenteId);
	
	public void enabled(Integer categoriaDocenteId);
	
	public void delete(Integer categoriaDocenteId);
	
	public void remove(Integer categoriaDocenteId);
	
	List<CategoriaDocente> findAllEnabled();
	
	List<CategoriaDocente> findByNombreCategoria(String termino);
}
