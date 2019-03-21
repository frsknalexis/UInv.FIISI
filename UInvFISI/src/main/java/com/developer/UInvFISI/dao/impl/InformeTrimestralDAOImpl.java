package com.developer.UInvFISI.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.dao.InformeTrimestralDAO;
import com.developer.UInvFISI.entity.InformeTrimestral;

@Repository("informeTrimestralRepository")
public class InformeTrimestralDAOImpl implements InformeTrimestralDAO {

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InformeTrimestral> findAll() {
		
		return em.createQuery("from InformeTrimestral").getResultList();
	}

	@Override
	public void save(InformeTrimestral informeTrimestral) {
		
		em.persist(informeTrimestral);
	}

	@Override
	public void update(InformeTrimestral informeTrimestral) {
		
		if(informeTrimestral.getInformeTrimestralId() != null && informeTrimestral.getInformeTrimestralId() > 0) {
			
			em.merge(informeTrimestral);
		}
		
	}

	@Override
	public InformeTrimestral findOne(Integer informeTrimestralId) {
		
		InformeTrimestral informeTrimestral = null;
		if(informeTrimestralId != null && informeTrimestralId > 0) {
			
			informeTrimestral = em.find(InformeTrimestral.class, informeTrimestralId);
		}
		return informeTrimestral;
	}

}
