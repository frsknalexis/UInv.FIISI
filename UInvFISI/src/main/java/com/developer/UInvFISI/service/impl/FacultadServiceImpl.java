package com.developer.UInvFISI.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.UInvFISI.dao.FacultadDAO;

import com.developer.UInvFISI.entity.Facultad;
import com.developer.UInvFISI.service.FacultadService;

@Service("facultadService")
public class FacultadServiceImpl implements FacultadService{

	@Autowired
	@Qualifier("facultadRepository")
	private FacultadDAO facultadDAO;
	
	
	@Override
	@Transactional(readOnly=true)	
	public List<Facultad> findAll() {
		List<Facultad> facultades = facultadDAO.findAll();
		return facultades;
	}

	@Override
	@Transactional
	public void saveOrUpdate(Facultad facultad) {
		if(facultad.getFacultadId() != null && facultad.getFacultadId() > 0) {
			facultad.setFechaModificacion(new Date());
			facultadDAO.update(facultad);
		}
		else {
			facultad.setFechaRegistro(new Date());
			facultad.setHabilitado(true);
			facultadDAO.save(facultad);
		}
	}

	@Override
	@Transactional
	public Facultad getByCodigo(Integer facultadId) {
		Facultad facultad = facultadDAO.findOne(facultadId);
		return facultad;
	}

	@Override
	public void enabled(Integer facultadId) {
		Facultad facultad = null;
		if(facultadId != null && facultadId > 0) {
			facultad = facultadDAO.findOne(facultadId);
			facultad.setFechaModificacion(new Date());
			facultad.setHabilitado(true);
		}
		facultadDAO.disabled(facultad);
	}

	@Override
	public void delete(Integer facultadId) {
		Facultad facultad = null;
		if(facultadId != null && facultadId > 0) {
			facultad = facultadDAO.findOne(facultadId);
			facultad.setFechaModificacion(new Date());
			facultad.setHabilitado(false);
		}
		facultadDAO.disabled(facultad);
	}

	@Override
	@Transactional
	public void delete2(Integer facultadId) {
		if(facultadId != null && facultadId > 0) {
			facultadDAO.delete(facultadId);
		}
	}

	@Override
	public List<Facultad> findAllEnabled() {
		List<Facultad> facultades = facultadDAO.findAllEnabled();
		return facultades;
	}

}
