package com.developer.UInvFISI.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.dao.AsignaturaAlumnoDAO;

import com.developer.UInvFISI.entity.AsignaturaAlumno;


@Repository("asignaturaAlumnoRepository")
public class AsignaturaAlumnoDAOImpl implements AsignaturaAlumnoDAO{

	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AsignaturaAlumno> findAll() {
		return em.createQuery("from AsignaturaAlumno").getResultList();
	}

	@Override
	public void save(AsignaturaAlumno asignaturaAlumno) {
		em.persist(asignaturaAlumno);
	}

	@Override
	public void update(AsignaturaAlumno asignaturaAlumno) {
		if(asignaturaAlumno.getAsignaturaDetalleId() != null && asignaturaAlumno.getAsignaturaDetalleId() > 0) {
			em.merge(asignaturaAlumno);
		}
	}

	@Override
	public AsignaturaAlumno findOne(Integer asignaturaDetalleId) {
		AsignaturaAlumno asignaturaAlumno = null;
		
		if(asignaturaDetalleId != null && asignaturaDetalleId > 0) {
			
			asignaturaAlumno = em.find(AsignaturaAlumno.class, asignaturaDetalleId);
		}
		
		return asignaturaAlumno;
	}
}
