package com.developer.UInvFISI.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.UInvFISI.dao.EscuelaDAO;

import com.developer.UInvFISI.entity.Escuela;

import com.developer.UInvFISI.service.EscuelaService;

@Service("escuelaService")
public class EscuelaServiceImpl implements EscuelaService{

	
	
	@Autowired
	@Qualifier("escuelaRepository")
	private EscuelaDAO escuelaDAO;
	
	
	
	@Override
	@Transactional(readOnly=true)	
	public List<Escuela> findAll() {
		List<Escuela> escuelas = escuelaDAO.findAll();
		return escuelas;
	}

	@Override
	@Transactional
	public void saveOrUpdate(Escuela escuela) {
		if(escuela.getEscuelaId() != null && escuela.getEscuelaId() > 0) {
			escuela.setFechaModificacion(new Date());
			escuelaDAO.update(escuela);
		}
		else {
			escuela.setFechaRegistro(new Date());
			escuela.setHabilitado(true);
			escuelaDAO.save(escuela);
		}

	}

	@Override
	@Transactional
	public Escuela getByCodigo(Integer escuelaId) {
		Escuela escuela = escuelaDAO.findOne(escuelaId);
		return escuela;
	}

	@Override
	public void enabled(Integer escuelaId) {
		Escuela escuela = null;
		if(escuelaId != null && escuelaId > 0) {
			escuela = escuelaDAO.findOne(escuelaId);
			escuela.setFechaModificacion(new Date());
			escuela.setHabilitado(true);
		}
		escuelaDAO.disabled(escuela);
	}

	@Override
	public void delete(Integer escuelaId) {
		Escuela escuela = null;
		if(escuelaId != null && escuelaId > 0) {
			escuela = escuelaDAO.findOne(escuelaId);
			escuela.setFechaModificacion(new Date());
			escuela.setHabilitado(false);
		}
		escuelaDAO.disabled(escuela);
	}

	@Override
	@Transactional
	public void delete2(Integer escuelaId) {
		if(escuelaId != null && escuelaId > 0) {
			escuelaDAO.delete(escuelaId);
		}
	}

	@Override
	public List<Escuela> findAllEnabled() {
		List<Escuela> escuelas = escuelaDAO.findAllEnabled();
		return escuelas;
	}

}
