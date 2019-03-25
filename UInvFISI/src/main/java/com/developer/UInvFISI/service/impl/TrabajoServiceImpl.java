package com.developer.UInvFISI.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.UInvFISI.dao.TrabajoDAO;
import com.developer.UInvFISI.entity.Trabajo;
import com.developer.UInvFISI.service.TrabajoService;

@Service("trabajoService")
public class TrabajoServiceImpl implements TrabajoService {

	@Autowired
	@Qualifier("trabajoRepository")
	private TrabajoDAO trabajoDAO;
	
	@Override
	@Transactional(readOnly=true)
	public List<Trabajo> findAll() {
		
		List<Trabajo> trabajo = trabajoDAO.findAll();
		return trabajo;
	}

	@Override
	@Transactional
	public void saveOrUpdate(Trabajo trabajo) {
		
		if(trabajo.getTrabajoId() != null && trabajo.getTrabajoId() > 0) {
			trabajo.setFechaModificacion(new Date());
			trabajoDAO.update(trabajo);
		}
		else {
			
			trabajo.setFechaRegistro(new Date());
			trabajo.setHabilitado(true);
			trabajoDAO.save(trabajo);
		}
	}

	@Override
	@Transactional
	public Trabajo getByTrabajoId(Integer trabajoId) {
		
		Trabajo trabajo = null;
		
		if(trabajoId != null && trabajoId > 0) {
			trabajo = trabajoDAO.findOne(trabajoId);
		}
		
		return trabajo;
	}

	@Override
	public void enabled(Integer trabajoId) {
		
		Trabajo trabajo = null;
		
		if(trabajoId != null && trabajoId > 0) {
			trabajo = trabajoDAO.findOne(trabajoId);
			trabajo.setFechaModificacion(new Date());
			trabajo.setHabilitado(true);
			
		}
		
		trabajoDAO.disabled(trabajo);
		
	}

	@Override
	public void delete(Integer trabajoId) {
		
		Trabajo trabajo = null;
		
		if(trabajoId != null && trabajoId > 0) {
			trabajo = trabajoDAO.findOne(trabajoId);
			trabajo.setFechaModificacion(new Date());
			trabajo.setHabilitado(false);
		}
		
		trabajoDAO.disabled(trabajo);
		
	}

	@Override
	@Transactional
	public void remove(Integer trabajoId) {
		
		if(trabajoId != null && trabajoId > 0) {
			
			trabajoDAO.delete(trabajoId);
		}
	}

	@Override
	public Long obtenerTotalTrabajos() {
		
		Long totalTrabajos = trabajoDAO.obtenerTotalTrabajos();
		return totalTrabajos;
	}
}
