package com.developer.UInvFISI.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.UInvFISI.dao.AsignaturaDAO;

import com.developer.UInvFISI.entity.Asignatura;
import com.developer.UInvFISI.service.AsignaturaService;

@Service("asignaturaService")
public class AsignaturaServiceImpl implements AsignaturaService {

	
	@Autowired
	@Qualifier("asignaturaRepository")
	private AsignaturaDAO asignaturaDAO;
	
	
	@Override
	@Transactional(readOnly=true)
	public List<Asignatura> findAll() {
		List<Asignatura> asignaturas = asignaturaDAO.findAll();
		return asignaturas;
	}

	@Override
	@Transactional
	public void saveOrUpdate(Asignatura asignatura) {
		if(asignatura.getAsignaturaId() != null && asignatura.getAsignaturaId() > 0) {
			asignatura.setFechaModificacion(new Date());
			asignaturaDAO.update(asignatura);
		}
		else {
			
			asignatura.setFechaRegistro(new Date());
			asignatura.setHabilitado(true);
			asignaturaDAO.save(asignatura);
		}	}

	
	@Override
	@Transactional
	public Asignatura getByAsignaturaId(Integer asignaturaId) {
		Asignatura asignatura = null;
		
		if(asignaturaId != null && asignaturaId > 0) {
			asignatura = asignaturaDAO.findOne(asignaturaId);
		}
		
		return asignatura;
	}
	
	@Override
	public void enabled(Integer asignaturaId) {
		
		Asignatura asignatura = null;
		
		if(asignaturaId != null && asignaturaId > 0) {
			asignatura = asignaturaDAO.findOne(asignaturaId);
			asignatura.setFechaModificacion(new Date());
			asignatura.setHabilitado(true);
			
		}
		
		asignaturaDAO.disabled(asignatura);
		
	}

	@Override
	public void delete(Integer asignaturaId) {

		Asignatura asignatura = null;
		
		if(asignaturaId != null && asignaturaId > 0) {
			asignatura = asignaturaDAO.findOne(asignaturaId);
			asignatura.setFechaModificacion(new Date());
			asignatura.setHabilitado(false);
		}
		
		asignaturaDAO.disabled(asignatura);
		
	}

	@Override
	@Transactional
	public void remove(Integer asignaturaId) {
		
		if(asignaturaId != null && asignaturaId > 0) {
			
			asignaturaDAO.delete(asignaturaId);
		}
	}

	@Override
	public Long obtenerTotalAsignaturas() {
		
		Long totalAsignaturas = asignaturaDAO.obtenerTotalAsignaturas();
		return totalAsignaturas;
	}
}
