package com.developer.UInvFISI.service;

import java.util.List;

import com.developer.UInvFISI.entity.Documento;

public interface DocumentoService {

	public List<Documento> findAll();
	
	public void saveOrUpdate(Documento documento);
	
	public Documento getByCodigo(Integer documentoId);
	
	public void enabled(Integer documentoId);
	
	public void delete(Integer documentoId);
	
	public void delete2(Integer documentoId);
	
	List<Documento> findAllEnabled();
}
