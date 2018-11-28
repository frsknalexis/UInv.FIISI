package com.developer.UInvFISI.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.UInvFISI.dao.ProgramaDAO;
import com.developer.UInvFISI.entity.Programa;
import com.developer.UInvFISI.service.ProgramaService;

@Service("programaService")
public class ProgramaServiceImpl implements ProgramaService {

	@Autowired
	@Qualifier("programaRepository")
	private ProgramaDAO programaDAO;
	
	@Override
	@Transactional(readOnly=true)
	public List<Programa> findAll() {
		List<Programa> programas = programaDAO.findAll();
		return programas;
	}

	@Override
	@Transactional
	public void saveOrUpdate(Programa programa) {
		if(programa.getProgramaId() != null && programa.getProgramaId() > 0) {
			programa.setFechaModificacion(new Date());
			programaDAO.update(programa);
		}
		else {
			programa.setFechaRegistro(new Date());
			programa.setHabilitado(true);
			programaDAO.save(programa);
		}
	}

	@Override
	@Transactional
	public Programa getByProgramaId(Integer programaId) {
		Programa programa = null;
		if(programaId != null && programaId > 0) {
			programa = programaDAO.findOne(programaId);
		}
		
		return programa;
	}

	@Override
	public void enabled(Integer programaId) {
		Programa programa = null;
		if(programaId != null && programaId > 0) {
			programa = programaDAO.findOne(programaId);
			programa.setFechaModificacion(new Date());
			programa.setHabilitado(true);
		}
		programaDAO.disabled(programa);
	}

	@Override
	public void delete(Integer programaId) {
		Programa programa = null;
		if(programaId != null && programaId > 0) {
			programa = programaDAO.findOne(programaId);
			programa.setFechaModificacion(new Date());
			programa.setHabilitado(false);
		}
		programaDAO.disabled(programa);
	}

	@Override
	@Transactional
	public void remove(Integer programaId) {
		if(programaId != null 
				&& programaId > 0) {
			programaDAO.delete(programaId);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<Programa> findAllEnabled() {
		
		List<Programa> programas = programaDAO.findAllEnabled();
		return programas;
		
	}

}
