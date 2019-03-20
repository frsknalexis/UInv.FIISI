package com.developer.UInvFISI.service.impl;

import java.util.Date;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.UInvFISI.dao.AsignacionDocenteDAO;
import com.developer.UInvFISI.entity.AsignacionDocente;
import com.developer.UInvFISI.repository.AsignacionDocenteRepository;
import com.developer.UInvFISI.service.AsignacionDocenteService;

@Service("asignacionDocenteService")
public class AsignacionDocenteServiceImpl implements AsignacionDocenteService {
	
	@Autowired
	@Qualifier("asignacionDocenteRepository")
	private AsignacionDocenteDAO asignacionDocenteDAO;
	
	@Autowired
	@Qualifier("asignacionDctRepository")
	private AsignacionDocenteRepository asignacionDocenteRepository;

	@Override
	@Transactional(readOnly=true)
	public List<AsignacionDocente> findAll() {
		
		List<AsignacionDocente> asignacionDocentes = asignacionDocenteDAO.findAll();
		return asignacionDocentes;
	}

	@Override
	@Transactional
	public void saveOrUpdate(AsignacionDocente asignacionDocente) {
		
		if(asignacionDocente.getAsignacionDetalleId() != null &&
				asignacionDocente.getAsignacionDetalleId() > 0) {
			
			asignacionDocente.setFechaModificacion(new Date());
			asignacionDocenteDAO.update(asignacionDocente);
		}
		
		else {
			
			asignacionDocente.setFechaRegistro(new Date());
			asignacionDocente.setHabilitado(true);
			asignacionDocenteDAO.save(asignacionDocente);
		}
	}

	@Override
	@Transactional
	public AsignacionDocente getAsignacionDocenteById(Integer asignacionDetalleId) {
		
		AsignacionDocente asignacionDocente = null;
		
		if(asignacionDetalleId != null && asignacionDetalleId > 0) {
			
			asignacionDocente = asignacionDocenteDAO.findOne(asignacionDetalleId);
		}
		
		return asignacionDocente;
	}

	@Override
	public List<AsignacionDocente> findByAsignacionId(Integer asignacionId) {
		
		List<AsignacionDocente> asignacionDocentes = asignacionDocenteRepository.findByAsignacionAsignacionId(asignacionId);
		return asignacionDocentes;
	}

	@Override
	public AsignacionDocente findByAsignacionAsignacionIdAndAsignacionDetalleId(Integer asignacionId,
			Integer asignacionDetalleId) {
		
		AsignacionDocente asignacionDocente = null;
		
		if(asignacionId != null && asignacionId > 0) {
			
			if(asignacionDetalleId > 0 && asignacionDetalleId != null) {
				
				asignacionDocente = asignacionDocenteRepository.findByAsignacionAsignacionIdAndAsignacionDetalleId(asignacionId, asignacionDetalleId);
			}
		}
		
		return asignacionDocente;
	}

	@Override
	public void disabled(Integer asignacionDetalleId) {
		
		AsignacionDocente asignacionDocente = null;
		if(asignacionDetalleId != null && asignacionDetalleId > 0) {
			
			asignacionDocente = asignacionDocenteDAO.findOne(asignacionDetalleId);
			asignacionDocente.setFechaModificacion(new Date());
			asignacionDocente.setHabilitado(false);
		}
		
		asignacionDocenteDAO.disabled(asignacionDocente);
	}
	
	@Override
	public void enabled(Integer asignacionDetalleId) {
		
		AsignacionDocente asignacionDocente = null;
		if(asignacionDetalleId != null && asignacionDetalleId > 0) {
			
			asignacionDocente = asignacionDocenteDAO.findOne(asignacionDetalleId);
			asignacionDocente.setFechaModificacion(new Date());
			asignacionDocente.setHabilitado(true);
		}
		
		asignacionDocenteDAO.disabled(asignacionDocente);
	}

	@Override
	public List<AsignacionDocente> findByAsignacionIdAndHabilitado(Integer asignacionId) {
		
		List<AsignacionDocente> investigadores = null;
		if(asignacionId != null && asignacionId > 0) {
			
			 investigadores = asignacionDocenteDAO.findByAsignacionIdAndHabilitado(asignacionId);
		}
		return investigadores;
	} 

}
