package com.developer.UInvFISI.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.dao.ReglamentoDAO;
import com.developer.UInvFISI.entity.Reglamento;

@Repository("reglamentoRepository")
public class ReglamentoDAOImpl implements ReglamentoDAO{

	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Reglamento> findAll() {
		return em.createQuery("from Reglamento").getResultList();
	}

	@Override
	public void save(Reglamento reglamento) {
		em.persist(reglamento);
		
	}

	@Override
	public void update(Reglamento reglamento) {
		if(reglamento.getReglamentoId() != null && reglamento.getReglamentoId() > 0) {
			em.merge(reglamento);
		}
	}

	@Override
	public Reglamento findOne(Integer reglamentoId) {
		
		Reglamento reglamento = null;
		if(reglamentoId != null && reglamentoId > 0) {
			reglamento =  em.find(Reglamento.class, reglamentoId);
		}
		return reglamento;
	}

}
