package com.developer.UInvFISI.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.dao.FacultadDAO;

import com.developer.UInvFISI.entity.Facultad;

@Repository("facultadRepository")
public class FacultadDAOImpl extends JdbcDaoSupport implements FacultadDAO{
	
	@Autowired
	public FacultadDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Facultad> findAll() {
		return em.createQuery("from Facultad").getResultList();
	}

	@Override
	public void save(Facultad facultad) {
		em.persist(facultad);
		
	}

	@Override
	public void update(Facultad facultad) {
		if(facultad.getFacultadId() != null && facultad.getFacultadId() > 0) {
			em.merge(facultad);
		}
	}

	@Override
	public Facultad findOne(Integer facultadId) {
		Facultad facultad = em.find(Facultad.class, facultadId);
		return facultad;
	}

	@Override
	public void disabled(Facultad facultad) {
		try {
			StringBuilder builder = new StringBuilder();
			builder.append(" UPDATE tbl_facultad SET fecha_modificacion=?, habilitado=? WHERE facultad_id = ?");
			getJdbcTemplate().update(builder.toString(), new Object[] {
					facultad.getFechaModificacion(),
					facultad.getHabilitado(),
					facultad.getFacultadId()});
		}
		catch(Exception ex) {
			ex.getMessage();
		}
	}

	@Override
	public void delete(Integer facultadId) {
		Facultad facultad = findOne(facultadId);
		em.remove(facultad);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Facultad> findAllEnabled() {
		// TODO Auto-generated method stub
		return em.createQuery("from Facultad f where f.habilitado = true").getResultList();
	}

	
}
