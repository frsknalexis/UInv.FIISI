package com.developer.UInvFISI.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.developer.UInvFISI.dao.AsignaturaAlumnoDAO;

import com.developer.UInvFISI.entity.AsignaturaAlumno;

import com.developer.UInvFISI.repository.AsignaturaAlumnoRepository;
import com.developer.UInvFISI.service.AsignaturaAlumnoService;

@Service("asignaturaAlumnoService")
public class AsignaturaAlumnoServiceImpl implements AsignaturaAlumnoService{

	
	@Autowired
	@Qualifier("asignaturaAlumnoRepository")
	private AsignaturaAlumnoDAO asignaturaAlumnoDAO;
	
	@Autowired
	@Qualifier("asignaturaAlmRepository")
	private AsignaturaAlumnoRepository asignaturaAlumnoRepository;

	
	
	
	
	@Override
	@Transactional(readOnly=true)
	public List<AsignaturaAlumno> findAll() {
		List<AsignaturaAlumno> asignaturaAlumnos = asignaturaAlumnoDAO.findAll();
		return asignaturaAlumnos;
	}
	
	@Override
	public List<AsignaturaAlumno> findByAsignaturaId(Integer asignaturaId) {
		List<AsignaturaAlumno> asignaturaAlumnos = asignaturaAlumnoRepository.findByAsignaturaAsignaturaId(asignaturaId);
		return asignaturaAlumnos;
	}

	@Override
	@Transactional
	public void saveOrUpdate(AsignaturaAlumno asignaturaAlumno) {
		if(asignaturaAlumno.getAsignaturaDetalleId() != null &&
				asignaturaAlumno.getAsignaturaDetalleId() > 0) {
			
			asignaturaAlumno.setFechaModificacion(new Date());
			asignaturaAlumnoDAO.update(asignaturaAlumno);
		}
		
		else {
			
			asignaturaAlumno.setFechaRegistro(new Date());
			asignaturaAlumno.setHabilitado(true);
			asignaturaAlumnoDAO.save(asignaturaAlumno);
		}
	}

	
	@Override
	@Transactional
	public AsignaturaAlumno getAsignaturaAlumnoById(Integer asignaturaDetalleId) {
		AsignaturaAlumno asignaturaAlumno = null;
		
		if(asignaturaDetalleId != null && asignaturaDetalleId > 0) {
			
			asignaturaAlumno = asignaturaAlumnoDAO.findOne(asignaturaDetalleId);
		}
		
		return asignaturaAlumno;
	}

	@Override
	public AsignaturaAlumno findByAsignaturaAsignaturaIdAndAsignaturaDetalleId(Integer asignaturaId,
			Integer asignaturaDetalleId) {
		
		AsignaturaAlumno asignaturaAlumno = null;
		
		if(asignaturaId != null && asignaturaId > 0) {
			
			if(asignaturaDetalleId > 0 && asignaturaDetalleId != null) {
				
				asignaturaAlumno = asignaturaAlumnoRepository.findByAsignaturaAsignaturaIdAndAsignaturaDetalleId(asignaturaId, asignaturaDetalleId);
			}
		}
		
		return asignaturaAlumno;
	}
	
	@Override
	public List<AsignaturaAlumno> findByAsignaturaAsignaturaId(Integer asignaturaId) {
		List<AsignaturaAlumno> asignaturaAlumno = asignaturaAlumnoRepository.findByAsignaturaAsignaturaId(asignaturaId);
		return asignaturaAlumno;
	}
}
