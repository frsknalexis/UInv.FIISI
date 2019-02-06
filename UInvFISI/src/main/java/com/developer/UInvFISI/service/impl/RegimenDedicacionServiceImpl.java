package com.developer.UInvFISI.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.UInvFISI.dao.RegimenDedicacionDAO;
import com.developer.UInvFISI.entity.RegimenDedicacion;
import com.developer.UInvFISI.service.RegimenDedicacionService;

@Service("regimenDedicacionService")
public class RegimenDedicacionServiceImpl implements RegimenDedicacionService {

	@Autowired
	@Qualifier("regimenDedicacionRepository")
	private RegimenDedicacionDAO regimenDedicacionDAO;
	
	@Override
	@Transactional(readOnly=true)
	public List<RegimenDedicacion> findAll() {
		List<RegimenDedicacion> regimenesDedicacion = regimenDedicacionDAO.findAll();
		return regimenesDedicacion;
	}

	@Override
	@Transactional
	public void saveOrUpdate(RegimenDedicacion regimenDedicacion) {
		if(regimenDedicacion.getRegimenDedicacionId() != null && regimenDedicacion.getRegimenDedicacionId() > 0) {
			regimenDedicacion.setFechaModificacion(new Date());
			regimenDedicacionDAO.update(regimenDedicacion);
		}
		else {
			regimenDedicacion.setFechaRegistro(new Date());
			regimenDedicacion.setHabilitado(true);
			regimenDedicacionDAO.save(regimenDedicacion);
		}
	}

	@Override
	@Transactional
	public RegimenDedicacion getByRegimenDedicacionId(Integer regimenDedicacionId) {
		RegimenDedicacion regimenDedicacion = null;
		if(regimenDedicacionId != null && regimenDedicacionId > 0) {
			regimenDedicacion = regimenDedicacionDAO.findOne(regimenDedicacionId);
		}
		return regimenDedicacion;
	}

	@Override
	public void enabled(Integer regimenDedicacionId) {
		RegimenDedicacion regimenDedicacion = null;
		if(regimenDedicacionId != null && regimenDedicacionId > 0) {
			regimenDedicacion = regimenDedicacionDAO.findOne(regimenDedicacionId);
			regimenDedicacion.setFechaModificacion(new Date());
			regimenDedicacion.setHabilitado(true);
		}
		
		regimenDedicacionDAO.disabled(regimenDedicacion);
		
	}

	@Override
	public void delete(Integer regimenDedicacionId) {
		
		RegimenDedicacion regimenDedicacion = null;
		if(regimenDedicacionId != null &&
				regimenDedicacionId > 0) {
			regimenDedicacion = regimenDedicacionDAO.findOne(regimenDedicacionId);
			regimenDedicacion.setFechaModificacion(new Date());
			regimenDedicacion.setHabilitado(false);
		}
		
		regimenDedicacionDAO.disabled(regimenDedicacion);
	}

	@Override
	@Transactional
	public void remove(Integer regimenDedicacionId) {
		
		if(regimenDedicacionId != null && 
				regimenDedicacionId > 0) {
			regimenDedicacionDAO.delete(regimenDedicacionId);
		}
	}

	@Override
	public List<RegimenDedicacion> findAllEnabled() {
		List<RegimenDedicacion> regimenes = regimenDedicacionDAO.findAllEnabled();
		return regimenes;
	}

}
