package com.developer.UInvFISI.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.dao.EscuelaDAO;
import com.developer.UInvFISI.entity.Escuela;


@Repository("escuelaRepository")
public class EscuelaDAOImpl extends JdbcDaoSupport implements EscuelaDAO{

	
	@Autowired
	public EscuelaDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Escuela> findAll() {
		return em.createQuery("from Escuela").getResultList();
	}

	@Override
	public void save(Escuela escuela) {
		em.persist(escuela);
		
	}

	@Override
	public void update(Escuela escuela) {
		if(escuela.getEscuelaId() != null && escuela.getEscuelaId() > 0) {
			em.merge(escuela);
		}
	}

	@Override
	public Escuela findOne(Integer escuelaId) {
		Escuela escuela = em.find(Escuela.class, escuelaId);
		return escuela;
	}

	@Override
	public void disabled(Escuela escuela) {
		
		try {
			StringBuilder builder = new StringBuilder();
			builder.append(" UPDATE tbl_escuela SET fecha_modificacion=?, habilitado=? WHERE escuela_id = ?");
			getJdbcTemplate().update(builder.toString(), new Object[] {
					escuela.getFechaModificacion(),
					escuela.getHabilitado(),
					escuela.getEscuelaId()});
		}
		catch(Exception ex) {
			ex.getMessage();
		}
		
	}

	@Override
	public void delete(Integer escuelaId) {
		Escuela escuela = findOne(escuelaId);
		em.remove(escuela);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Escuela> findAllEnabled() {
		// TODO Auto-generated method stub
		return em.createQuery("from Escuela f where f.habilitado = true").getResultList();
	}

}
