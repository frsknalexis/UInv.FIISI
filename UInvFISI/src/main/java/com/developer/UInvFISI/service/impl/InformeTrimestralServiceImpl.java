package com.developer.UInvFISI.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.UInvFISI.dao.InformeTrimestralDAO;
import com.developer.UInvFISI.entity.InformeTrimestral;
import com.developer.UInvFISI.repository.InformeTrimestralRepository;
import com.developer.UInvFISI.service.InformeTrimestralService;

@Service("informeTrimestralService")
public class InformeTrimestralServiceImpl implements InformeTrimestralService {

	@Autowired
	@Qualifier("informeTrimestralRpty")
	private InformeTrimestralRepository informeTrimestralRepository;
	
	@Autowired
	@Qualifier("informeTrimestralRepository")
	private InformeTrimestralDAO informeTrimestralDAO;
	
	@Override
	@Transactional(readOnly=true)
	public List<InformeTrimestral> findAll() {
		
		List<InformeTrimestral> informesTrimestrales = informeTrimestralDAO.findAll();
		return informesTrimestrales;
	}

	@Override
	@Transactional
	public void saveOrUpdate(InformeTrimestral informeTrimestral) {
		
		if(informeTrimestral.getInformeTrimestralId() != null && informeTrimestral.getInformeTrimestralId() > 0) {
			
			informeTrimestral.setFechaModificacion(new Date());
			informeTrimestralDAO.update(informeTrimestral);
		}
		else {
			
			informeTrimestral.setFechaRegistro(new Date());
			informeTrimestral.setHabilitado(true);
			informeTrimestralDAO.save(informeTrimestral);
		}
		
	}

	@Override
	public List<InformeTrimestral> findByAsignacionDetalleAsignacionAsignacionId(Integer asignacionId) {
		
		List<InformeTrimestral> informesTrimestrales = null;
		if(asignacionId != null && asignacionId > 0) {
			
			informesTrimestrales = informeTrimestralRepository.findByAsignacionDetalleAsignacionAsignacionId(asignacionId);
		}
		return informesTrimestrales;
	}

	@Override
	public List<InformeTrimestral> findByAsignacionDetalleAsignacionDetalleId(Integer asignacionDetalleId) {
		
		List<InformeTrimestral> informesTrimestrales = null;
		if(asignacionDetalleId != null && asignacionDetalleId > 0) {
			
			informesTrimestrales = informeTrimestralRepository.findByAsignacionDetalleAsignacionDetalleId(asignacionDetalleId);
		}
		return informesTrimestrales;
	}

	@Override
	public InformeTrimestral findByAsignacionDetalleAsignacionDetalleIdAndInformeTrimestralId(
			Integer asignacionDetalleId, Integer informeTrimestralId) {
		
		InformeTrimestral informeTrimestral = null;
		if(asignacionDetalleId != null && asignacionDetalleId > 0) {
			
			if(informeTrimestralId != null && informeTrimestralId > 0) {
				informeTrimestral = informeTrimestralRepository.findByAsignacionDetalleAsignacionDetalleIdAndInformeTrimestralId(asignacionDetalleId, informeTrimestralId);
			}
		}
		return informeTrimestral;
	}

	@Override
	public InformeTrimestral getInformeTrimestralById(Integer informeTrimestralId) {
		
		InformeTrimestral informeTrimestral = null;
		if(informeTrimestralId != null && informeTrimestralId > 0) {
			
			informeTrimestral = informeTrimestralDAO.findOne(informeTrimestralId);
		}
		return informeTrimestral;
	}	
}
