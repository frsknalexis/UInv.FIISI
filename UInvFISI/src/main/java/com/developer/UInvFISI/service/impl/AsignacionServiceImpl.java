package com.developer.UInvFISI.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.UInvFISI.dao.AsignacionDAO;
import com.developer.UInvFISI.entity.Asignacion;
import com.developer.UInvFISI.service.AsignacionService;

@Service("asignacionService")
public class AsignacionServiceImpl implements AsignacionService {

	@Autowired
	@Qualifier("asignacionRepository")
	private AsignacionDAO asignacionDAO;
	
	@Override
	@Transactional(readOnly=true)
	public List<Asignacion> findAll() {
		
		List<Asignacion> asignaciones = asignacionDAO.findAll();
		return asignaciones;
	}

	@Override
	@Transactional
	public void saveOrUpdate(Asignacion asignacion) {
		
		if(asignacion.getAsignacionId() != null && asignacion.getAsignacionId() > 0) {
			asignacion.setFechaModificacion(new Date());
			asignacionDAO.update(asignacion);
		}
		else {
			
			asignacion.setFechaRegistro(new Date());
			asignacion.setHabilitado(true);
			asignacionDAO.save(asignacion);
		}
	}

	@Override
	@Transactional
	public Asignacion getByAsignacionId(Integer asignacionId) {
		
		Asignacion asignacion = null;
		
		if(asignacionId != null && asignacionId > 0) {
			asignacion = asignacionDAO.findOne(asignacionId);
		}
		
		return asignacion;
	}

	@Override
	public void enabled(Integer asignacionId) {
		
		Asignacion asignacion = null;
		
		if(asignacionId != null && asignacionId > 0) {
			asignacion = asignacionDAO.findOne(asignacionId);
			asignacion.setFechaModificacion(new Date());
			asignacion.setHabilitado(true);
			
		}
		
		asignacionDAO.disabled(asignacion);
		
	}

	@Override
	public void delete(Integer asignacionId) {
		
		Asignacion asignacion = null;
		
		if(asignacionId != null && asignacionId > 0) {
			asignacion = asignacionDAO.findOne(asignacionId);
			asignacion.setFechaModificacion(new Date());
			asignacion.setHabilitado(false);
		}
		
		asignacionDAO.disabled(asignacion);
		
	}

	@Override
	@Transactional
	public void remove(Integer asignacionId) {
		
		if(asignacionId != null && asignacionId > 0) {
			
			asignacionDAO.delete(asignacionId);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<Asignacion> findAllEnabled() {
		
		List<Asignacion> asignaciones = asignacionDAO.findAllEnabled();
		return asignaciones;
	}

	@Override
	public Long obtenerTotalRegistrosAsignacion() {
		
		Long totalRegistros = asignacionDAO.obtenerTotalRegistrosAsignacion();
		return totalRegistros;
	}
}
