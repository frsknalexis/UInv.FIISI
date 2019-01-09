package com.developer.UInvFISI.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.UInvFISI.dao.CategoriaDocenteDAO;
import com.developer.UInvFISI.entity.CategoriaDocente;
import com.developer.UInvFISI.repository.CategoriaDocenteRepository;
import com.developer.UInvFISI.service.CategoriaDocenteService;

@Service("categoriaDocenteService")
public class CategoriaDocenteServiceImpl implements CategoriaDocenteService {

	@Autowired
	@Qualifier("categoriaDocenteRepository")
	private CategoriaDocenteDAO categoriaDocenteDAO;
	
	@Autowired
	@Qualifier("catRepository")
	private CategoriaDocenteRepository categoriaDocenteRepository;
	
	@Override
	@Transactional(readOnly=true)
	public List<CategoriaDocente> findAll() {
		List<CategoriaDocente> categoriasDocente = categoriaDocenteDAO.findAll();
		return categoriasDocente;
	}

	@Override
	@Transactional
	public void saveOrUpdate(CategoriaDocente categoriaDocente) {
		if(categoriaDocente.getCategoriaDocenteId() != null && categoriaDocente.getCategoriaDocenteId() > 0) {
			categoriaDocente.setFechaModificacion(new Date());
			categoriaDocenteDAO.update(categoriaDocente);
		}
		else {
			categoriaDocente.setFechaRegistro(new Date());
			categoriaDocente.setHabilitado(true);
			categoriaDocenteDAO.save(categoriaDocente);
		}
	}

	@Override
	@Transactional
	public CategoriaDocente getByCategoriaDocenteId(Integer categoriaDocenteId) {
		CategoriaDocente categoria = categoriaDocenteDAO.findOne(categoriaDocenteId);
		return categoria;
		
	}

	@Override
	public void enabled(Integer categoriaDocenteId) {
		CategoriaDocente categoriaDocente = null;
		if(categoriaDocenteId != null && categoriaDocenteId > 0) {
			categoriaDocente = categoriaDocenteDAO.findOne(categoriaDocenteId);
			categoriaDocente.setFechaModificacion(new Date());
			categoriaDocente.setHabilitado(true);
		}
		categoriaDocenteDAO.disabled(categoriaDocente);
	}

	@Override
	public void delete(Integer categoriaDocenteId) {
		CategoriaDocente categoriaDocente = null;
		if(categoriaDocenteId != null && categoriaDocenteId > 0) {
			categoriaDocente = categoriaDocenteDAO.findOne(categoriaDocenteId);
			categoriaDocente.setFechaModificacion(new Date());
			categoriaDocente.setHabilitado(false);
		}
		categoriaDocenteDAO.disabled(categoriaDocente);
	}

	@Override
	public void remove(Integer categoriaDocenteId) {
		if(categoriaDocenteId != null && categoriaDocenteId > 0) {
			categoriaDocenteDAO.delete(categoriaDocenteId);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<CategoriaDocente> findAllEnabled() {
		List<CategoriaDocente> categoriasDocente = categoriaDocenteDAO.findAllEnabled();
		return categoriasDocente;
	}

	@Override
	public List<CategoriaDocente> findByNombreCategoria(String termino) {
		List<CategoriaDocente> categorias = categoriaDocenteRepository.findByNombreCategoria(termino);
		return categorias;
	}

}
