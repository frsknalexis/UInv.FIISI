package com.developer.UInvFISI.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.dao.InformeInvestigacionDAO;
import com.developer.UInvFISI.entity.InformeInvestigacion;

@Repository("informeInvestigacionRepository")
public class InformeInvestigacionDAOImpl implements InformeInvestigacionDAO {

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InformeInvestigacion> findAll() {
		
		return em.createQuery("from InformeInvestigacion").getResultList();
	}

	@Override
	public void save(InformeInvestigacion informeInvestigacion) {
		em.persist(informeInvestigacion);	
	}

	@Override
	public void update(InformeInvestigacion informeInvestigacion) {
		
		if(informeInvestigacion.getInformeAsignacionId() != null && informeInvestigacion.getInformeAsignacionId() > 0) {
			em.merge(informeInvestigacion);
		}
	}

}
