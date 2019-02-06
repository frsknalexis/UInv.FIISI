package com.developer.UInvFISI.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.dao.CondicionDAO;
import com.developer.UInvFISI.entity.Condicion;

@Repository("condicionRepository")
public class CondicionDAOImpl extends JdbcDaoSupport implements CondicionDAO {

	@Autowired
	public CondicionDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Condicion> findAll() {
		return em.createQuery("from Condicion").getResultList();
	}

	@Override
	public void save(Condicion condicion) {
		em.persist(condicion);
	}

	@Override
	public void update(Condicion condicion) {
		if(condicion.getCondicionId() != null && condicion.getCondicionId() > 0) {
			em.merge(condicion);
		}
	}

	@Override
	public Condicion findOne(Integer condicionId) {
		Condicion condicion = null;
		if(condicionId != null && condicionId > 0) {
			condicion = em.find(Condicion.class, condicionId);
		}
		return condicion;
	}

	@Override
	public void disabled(Condicion condicion) {
		try {
			StringBuilder builder = new StringBuilder();
			builder.append(" UPDATE tbl_condicion SET fecha_modificacion=?, habilitado=? WHERE condicion_id=?");
			getJdbcTemplate().update(builder.toString(), new Object[] {
				condicion.getFechaModificacion(),
				condicion.getHabilitado(),
				condicion.getCondicionId()
			});
		}
		catch(Exception ex) {
			ex.getMessage();
		}
	}

	@Override
	public void delete(Integer condicionId) {
		Condicion condicion = null;
		if(condicionId != null && condicionId > 0) {
			condicion = findOne(condicionId);
		}
		
		em.remove(condicion);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Condicion> findAllEnabled() {
		// TODO Auto-generated method stub
		return em.createQuery("from Condicion c where c.habilitado = true").getResultList();
	}
}
