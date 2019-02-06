package com.developer.UInvFISI.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.dao.InformeTrabajoDAO;
import com.developer.UInvFISI.entity.InformeTrabajo;

@Repository("informeTrabajoRepository")
public class InformeTrabajoDAOImpl implements InformeTrabajoDAO {

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InformeTrabajo> findAll() {
		
		return em.createQuery("from InformeTrabajo").getResultList();
	}

	@Override
	public void save(InformeTrabajo informeTrabajo) {
		em.persist(informeTrabajo);	
	}

	@Override
	public void update(InformeTrabajo informeTrabajo) {
		
		if(informeTrabajo.getInformeTrabajoId() != null && informeTrabajo.getInformeTrabajoId() > 0) {
			em.merge(informeTrabajo);
		}
	}

}
