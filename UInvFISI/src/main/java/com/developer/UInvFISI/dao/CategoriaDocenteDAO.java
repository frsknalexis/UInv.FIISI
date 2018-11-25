package com.developer.UInvFISI.dao;

import java.util.List;

import com.developer.UInvFISI.entity.CategoriaDocente;

public interface CategoriaDocenteDAO {
	
	public List<CategoriaDocente> findAll();
	
	public void save(CategoriaDocente categoriaDocente);
	
	public void update(CategoriaDocente categoriaDocente);
	
	public CategoriaDocente findOne(Integer categoriaDocenteId);
	
	public void disabled(CategoriaDocente categoriaDocente);
	
	public void delete(Integer categoriaDocenteId);
	
	List<CategoriaDocente> findAllEnabled();

}
