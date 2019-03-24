package com.developer.UInvFISI.service.impl;

import java.util.Date;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.UInvFISI.dao.AutorDAO;
import com.developer.UInvFISI.entity.Autor;
import com.developer.UInvFISI.repository.AutorRepository;
import com.developer.UInvFISI.service.AutorService;

@Service("autorService")
public class AutorServiceImpl implements AutorService {
	
	@Autowired
	@Qualifier("autorRepository")
	private AutorDAO autorDAO;
	
	@Autowired
	@Qualifier("autorAsRepository")
	private AutorRepository autorRepository;

	@Override
	@Transactional(readOnly=true)
	public List<Autor> findAll() {
		
		List<Autor> autor = autorDAO.findAll();
		return autor;
	}

	@Override
	@Transactional
	public void saveOrUpdate(Autor autor) {
		
		if(autor.getAutorId() != null &&
				autor.getAutorId() > 0) {
			
			autor.setFechaModificacion(new Date());
			autorDAO.update(autor);
		}
		
		else {
			
			autor.setFechaRegistro(new Date());
			autor.setHabilitado(true);
			autorDAO.save(autor);
		}
	}

	@Override
	@Transactional
	public Autor getAutorById(Integer autorId) {
		
		Autor autor = null;
		
		if(autorId != null && autorId > 0) {
			
			autor = autorDAO.findOne(autorId);
		}
		
		return autor;
	}

	@Override
	public List<Autor> findByTrabajoId(Integer trabajoId) {
		
		List<Autor> autor = autorRepository.findByTrabajoTrabajoId(trabajoId);
		return autor;
	}

	@Override
	public Autor findByTrabajoTrabajoIdAndAutorDetalleId(Integer trabajoId,
			Integer autorDetalleId) {
		
		Autor autor = null;
		
		if(trabajoId != null && trabajoId > 0) {
			
			if(autorDetalleId > 0 && autorDetalleId != null) {
				
				autor = autorRepository.findByTrabajoTrabajoIdAndAutorId(trabajoId, autorDetalleId);
			}
		}
		
		return autor;
	}

	@Override
	public void disabled(Integer autorId) {
		
		Autor autor = null;
		if(autorId != null && autorId > 0) {
			
			autor = autorDAO.findOne(autorId);
			autor.setFechaModificacion(new Date());
			autor.setHabilitado(false);
		}
		
		autorDAO.disabled(autor);
	}

	@Override
	public void enabled(Integer autorId) {
		
		Autor autor = null;
		if(autorId != null && autorId > 0) {
			
			autor = autorDAO.findOne(autorId);
			autor.setFechaModificacion(new Date());
			autor.setHabilitado(true);
		}
		
		autorDAO.disabled(autor);
	} 

}
