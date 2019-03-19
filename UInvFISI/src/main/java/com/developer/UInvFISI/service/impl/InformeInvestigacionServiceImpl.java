package com.developer.UInvFISI.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.UInvFISI.dao.InformeInvestigacionDAO;
import com.developer.UInvFISI.entity.InformeInvestigacion;
import com.developer.UInvFISI.repository.InformeInvestigacionRepository;
import com.developer.UInvFISI.service.InformeInvestigacionService;

@Service("informeInvestigacionService")
public class InformeInvestigacionServiceImpl implements InformeInvestigacionService {

	@Autowired
	@Qualifier("informeInvestigacionRepository")
	private InformeInvestigacionDAO informeInvestigacionDAO;
	
	@Autowired
	@Qualifier("informeInvestigRepository")
	private InformeInvestigacionRepository informeInvestigacionRepository;
	
	@Override
	@Transactional(readOnly=true)
	public List<InformeInvestigacion> findAll() {
		List<InformeInvestigacion> informesInvestigacion = informeInvestigacionDAO.findAll();
		return informesInvestigacion;
	}

	@Override
	@Transactional
	public void saveOrUpdate(InformeInvestigacion informeInvestigacion) {
		
		if(informeInvestigacion.getInformeAsignacionId() != null && informeInvestigacion.getInformeAsignacionId() > 0) {
			
			informeInvestigacion.setFechaModificacion(new Date());
			informeInvestigacionDAO.update(informeInvestigacion);
		}
		else {
			
			informeInvestigacion.setFechaRegistro(new Date());
			informeInvestigacion.setHabilitado(true);
			informeInvestigacionDAO.save(informeInvestigacion);
		}
	}

	@Override
	public List<InformeInvestigacion> findByAsignacionAsignacionId(Integer asignacionId) {
		
		List<InformeInvestigacion> informesInvestigacion = informeInvestigacionRepository.findByAsignacionAsignacionId(asignacionId);
		return informesInvestigacion;
	}

	@Override
	public InformeInvestigacion findByAsignacionAsignacionIdAndInformeAsignacionId(Integer asignacionId,
			Integer informeAsignacionId) {
		
		InformeInvestigacion informeInvestigacion = null;
		if(asignacionId != null && asignacionId > 0) {
			
			if(informeAsignacionId != null && informeAsignacionId > 0) {
				informeInvestigacion = informeInvestigacionRepository.findByAsignacionAsignacionIdAndInformeAsignacionId(asignacionId, informeAsignacionId);
			}
		}
		return informeInvestigacion;
	}

	@Override
	@Transactional
	public InformeInvestigacion getByInformeAsignacionId(Integer informeAsignacionId) {
		
		InformeInvestigacion informeInvestigacion = null;
		if(informeAsignacionId != null && informeAsignacionId > 0) {
			informeInvestigacion = informeInvestigacionDAO.findOne(informeAsignacionId);
		}
		return informeInvestigacion;
	}
}
