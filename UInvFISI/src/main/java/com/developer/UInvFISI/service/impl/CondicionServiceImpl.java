package com.developer.UInvFISI.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.UInvFISI.dao.CondicionDAO;
import com.developer.UInvFISI.entity.Condicion;
import com.developer.UInvFISI.service.CondicionService;

@Service("condicionService")
public class CondicionServiceImpl implements CondicionService {

	@Autowired
	@Qualifier("condicionRepository")
	private CondicionDAO condicionDAO;
	
	@Override
	@Transactional(readOnly=true)
	public List<Condicion> findAll() {
		List<Condicion> condiciones = condicionDAO.findAll();
		return condiciones;
	}

	@Override
	@Transactional
	public void saveOrUpdate(Condicion condicion) {
		if(condicion.getCondicionId() != null && condicion.getCondicionId() > 0) {
			condicion.setFechaModificacion(new Date());
			condicionDAO.update(condicion);
		}
		else {
			condicion.setFechaRegistro(new Date());
			condicion.setHabilitado(true);
			condicionDAO.save(condicion);
		}
	}

	@Override
	@Transactional
	public Condicion getByCondicionId(Integer condicionId) {
		Condicion condicion = null;
		if(condicionId != null && condicionId > 0) {
			condicion = condicionDAO.findOne(condicionId);
		}
		return condicion;
	}

	@Override
	public void enabled(Integer condicionId) {
		Condicion condicion = null;
		if(condicionId != null && condicionId > 0) {
			condicion = condicionDAO.findOne(condicionId);
			condicion.setFechaModificacion(new Date());
			condicion.setHabilitado(true);
		}
		condicionDAO.disabled(condicion);
	}

	@Override
	public void delete(Integer condicionId) {
		Condicion condicion = null;
		if(condicionId != null && condicionId > 0) {
			condicion = condicionDAO.findOne(condicionId);
			condicion.setFechaModificacion(new Date());
			condicion.setHabilitado(false);
		}
		condicionDAO.disabled(condicion);
	}

	@Override
	@Transactional
	public void remove(Integer condicionId) {
		if(condicionId != null && condicionId > 0) {
			condicionDAO.delete(condicionId);
		}
	}

	@Override
	public List<Condicion> findAllEnabled() {
		List<Condicion> condiciones = condicionDAO.findAllEnabled();
		return condiciones;
	}	
}
