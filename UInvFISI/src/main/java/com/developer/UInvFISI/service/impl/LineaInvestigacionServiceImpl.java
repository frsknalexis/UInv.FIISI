package com.developer.UInvFISI.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.UInvFISI.dao.LineaInvestigacionDAO;
import com.developer.UInvFISI.entity.LineaInvestigacion;
import com.developer.UInvFISI.service.LineaInvestigacionService;

@Service("lineaInvestigacionService")
public class LineaInvestigacionServiceImpl implements LineaInvestigacionService {

	@Autowired
	@Qualifier("lineaInvestigacionRepository")
	private LineaInvestigacionDAO lineaInvestigacionDAO;
	
	@Override
	@Transactional(readOnly=true)
	public List<LineaInvestigacion> findAll() {
		
		List<LineaInvestigacion> lineasInvestigacion = lineaInvestigacionDAO.findAll();
		return lineasInvestigacion;
	}

	@Override
	@Transactional
	public void saveOrUpdate(LineaInvestigacion lineaInvestigacion) {
		
		if(lineaInvestigacion.getLineaInvestigacionId() != null 
				&& lineaInvestigacion.getLineaInvestigacionId() > 0) {
			
			lineaInvestigacion.setFechaModificacion(new Date());
			lineaInvestigacionDAO.update(lineaInvestigacion);
		}
		else {
			
			lineaInvestigacion.setFechaRegistro(new Date());
			lineaInvestigacion.setHabilitado(true);
			lineaInvestigacionDAO.save(lineaInvestigacion);
		}
	}

	@Override
	@Transactional
	public LineaInvestigacion getByLineaInvestigacionId(Integer lineaInvestigacionId) {
		
		LineaInvestigacion lineaInvestigacion = null;
		if(lineaInvestigacionId != null &&
				lineaInvestigacionId > 0) {
			
			lineaInvestigacion = lineaInvestigacionDAO.findOne(lineaInvestigacionId);
		}
		return lineaInvestigacion;
	}

	@Override
	public void enabled(Integer lineaInvestigacionId) {
		
		LineaInvestigacion lineaInvestigacion = null;
		if(lineaInvestigacionId != null 
				&& lineaInvestigacionId > 0) {
			
			lineaInvestigacion = lineaInvestigacionDAO.findOne(lineaInvestigacionId);
			lineaInvestigacion.setFechaModificacion(new Date());
			lineaInvestigacion.setHabilitado(true);
		}
		
		lineaInvestigacionDAO.disabled(lineaInvestigacion);
	}

	@Override
	public void delete(Integer lineaInvestigacionId) {
		
		LineaInvestigacion lineaInvestigacion = null;
		if(lineaInvestigacionId != null 
				&& lineaInvestigacionId > 0) {
			
			lineaInvestigacion = lineaInvestigacionDAO.findOne(lineaInvestigacionId);
			lineaInvestigacion.setFechaModificacion(new Date());
			lineaInvestigacion.setHabilitado(false);
			
		}
		
		lineaInvestigacionDAO.disabled(lineaInvestigacion);
	}

	@Override
	@Transactional
	public void remove(Integer lineaInvestigacionId) {
		
		if(lineaInvestigacionId != null 
				&& lineaInvestigacionId > 0) {
			
			lineaInvestigacionDAO.delete(lineaInvestigacionId);
		}
	}

}
