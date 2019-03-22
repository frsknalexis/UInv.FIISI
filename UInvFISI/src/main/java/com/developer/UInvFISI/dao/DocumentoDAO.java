package com.developer.UInvFISI.dao;

import java.util.List;

import com.developer.UInvFISI.entity.Documento;

public interface DocumentoDAO {

	public List<Documento> findAll();
	
	public void save(Documento documento);
	
	public void update(Documento documento);
	
	public Documento findOne(Integer documentoId);
	
	public void disabled(Documento documento);
	
	public void delete(Integer documentoId);
	
	public List<Documento> findAllEnabled();
	
}