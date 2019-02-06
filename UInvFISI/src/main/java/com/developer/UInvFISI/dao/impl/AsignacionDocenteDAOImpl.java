package com.developer.UInvFISI.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.dao.AsignacionDocenteDAO;
import com.developer.UInvFISI.entity.AsignacionDocente;

@Repository("asignacionDocenteRepository")
public class AsignacionDocenteDAOImpl implements AsignacionDocenteDAO {

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AsignacionDocente> findAll() {
		
		return em.createQuery("from AsignacionDocente").getResultList();
	}

	@Override
	public void save(AsignacionDocente asignacionDocente) {
		
		em.persist(asignacionDocente);
	}

	@Override
	public void update(AsignacionDocente asignacionDocente) {
		
		if(asignacionDocente.getAsignacionDetalleId() != null && asignacionDocente.getAsignacionDetalleId() > 0) {
			em.merge(asignacionDocente);
		}
	}

	@Override
	public AsignacionDocente findOne(Integer asignacionDetalleId) {
		
		AsignacionDocente asignacionDocente = null;
		
		if(asignacionDetalleId != null && asignacionDetalleId > 0) {
			
			asignacionDocente = em.find(AsignacionDocente.class, asignacionDetalleId);
		}
		
		return asignacionDocente;
	}
}
