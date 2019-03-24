package com.developer.UInvFISI.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.UInvFISI.dao.InformeTrabajoDAO;
import com.developer.UInvFISI.entity.InformeTrabajo;
import com.developer.UInvFISI.repository.InformeTrabajoRepository;
import com.developer.UInvFISI.service.InformeTrabajoService;

@Service("informeTrabajoService")
public class InformeTrabajoServiceImpl implements InformeTrabajoService {

	@Autowired
	@Qualifier("informeTrabajoRepository")
	private InformeTrabajoDAO informeTrabajoDAO;
	
	@Autowired
	@Qualifier("informeTrabRepository")
	private InformeTrabajoRepository informeTrabajoRepository;
	
	@Override
	@Transactional(readOnly=true)
	public List<InformeTrabajo> findAll() {
		List<InformeTrabajo> informeTrabajo = informeTrabajoDAO.findAll();
		return informeTrabajo;
	}

	@Override
	@Transactional
	public void saveOrUpdate(InformeTrabajo informeTrabajo) {
		
		if(informeTrabajo.getInformeTrabajoId() != null && informeTrabajo.getInformeTrabajoId() > 0) {
			
			informeTrabajo.setFechaModificacion(new Date());
			informeTrabajoDAO.update(informeTrabajo);
		}
		else {
			
			informeTrabajo.setFechaRegistro(new Date());
			informeTrabajo.setHabilitado(true);
			informeTrabajoDAO.save(informeTrabajo);
		}
	}

	@Override
	public List<InformeTrabajo> findByTrabajoTrabajoId(Integer trabajoId) {
		
		List<InformeTrabajo> informesTrabajo = informeTrabajoRepository.findByTrabajoTrabajoId(trabajoId);
		return informesTrabajo;
	}

	@Override
	public InformeTrabajo findByTrabajoTrabajoIdAndInformeTrabajoId(Integer trabajoId, Integer informeTrabajoId) {
		
		InformeTrabajo informeTrabajo = null;
		if(trabajoId != null && trabajoId > 0) {
			
			if(informeTrabajoId != null && informeTrabajoId > 0) {
				
				informeTrabajo = informeTrabajoRepository.findByTrabajoTrabajoIdAndInformeTrabajoId(trabajoId, informeTrabajoId);
			}
		}
		return informeTrabajo;
	}

	@Override
	public InformeTrabajo getByInformeTrabajoId(Integer informeTrabajoId) {
	
		InformeTrabajo informeTrabajo = null;
		if(informeTrabajoId != null && informeTrabajoId > 0) {
			
			informeTrabajo = informeTrabajoDAO.findOne(informeTrabajoId);
		}
		
		return informeTrabajo;
	}

}
