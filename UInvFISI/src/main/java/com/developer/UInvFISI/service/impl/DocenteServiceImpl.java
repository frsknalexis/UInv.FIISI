package com.developer.UInvFISI.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.UInvFISI.dao.DocenteDAO;
import com.developer.UInvFISI.entity.Docente;
import com.developer.UInvFISI.repository.DocenteRepository;
import com.developer.UInvFISI.service.DocenteService;

@Service("docenteService")
public class DocenteServiceImpl implements DocenteService {

	@Autowired
	@Qualifier("docenteRepository")
	private DocenteDAO docenteDAO;
	
	@Autowired
	@Qualifier("docenteRepository2")
	private DocenteRepository docenteRepository;
	
	@Override
	@Transactional(readOnly=true)
	public List<Docente> findAll() {
		List<Docente> docentes = docenteDAO.findAll();
		return docentes;
	}

	@Override
	@Transactional
	public void saveOrUpdate(Docente docente) {
		if(docente.getDocenteId() != null && docente.getDocenteId() > 0) {
			docente.setFechaModificacion(new Date());
			docenteDAO.update(docente);
		}
		else {
			docente.setFechaRegistro(new Date());
			docente.setHabilitado(true);
			docenteDAO.save(docente);
		}
	}

	@Override
	@Transactional
	public Docente getByDocenteId(Integer docenteId) {
		Docente docente = null;
		if(docenteId != null && docenteId > 0) {
			docente = docenteDAO.findOne(docenteId);
		}
		return docente;
	}

	@Override
	public void enabled(Integer docenteId) {
		Docente docente = null;
		if(docenteId != null && docenteId > 0) {
			docente = docenteDAO.findOne(docenteId);
			docente.setFechaModificacion(new Date());
			docente.setHabilitado(true);
		}
		
		docenteDAO.disabled(docente);
	}

	@Override
	public void delete(Integer docenteId) {
		Docente docente = null;
		if(docenteId != null && docenteId > 0) {
			docente = docenteDAO.findOne(docenteId);
			docente.setFechaModificacion(new Date());
			docente.setHabilitado(false);
		}
		
		docenteDAO.disabled(docente);
	}

	@Override
	@Transactional
	public void remove(Integer docenteId) {
		if(docenteId != null && docenteId > 0) {
			docenteDAO.delete(docenteId);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<Docente> findAllEnabled() {
		List<Docente> docentes = docenteDAO.findAllEnabled();
		return docentes;
	}

	@Override
	public List<Docente> findByNombresDocente(String termino) {
		
		List<Docente> docentes = docenteRepository.findByNombresDocente(termino);
		return docentes;
	}

	@Override
	public Long obtenerTotalRegistrosDocentes() {
		
		Long totalDocentes = docenteDAO.obtenerTotalRegistrosDocentes();
		return totalDocentes;
	}
}
