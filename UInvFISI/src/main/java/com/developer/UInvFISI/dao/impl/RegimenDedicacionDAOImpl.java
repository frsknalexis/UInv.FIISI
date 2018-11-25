package com.developer.UInvFISI.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.dao.RegimenDedicacionDAO;
import com.developer.UInvFISI.entity.RegimenDedicacion;

@Repository("regimenDedicacionRepository")
public class RegimenDedicacionDAOImpl extends JdbcDaoSupport implements RegimenDedicacionDAO {
	
	@Autowired
	public RegimenDedicacionDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<RegimenDedicacion> findAll() {
		return em.createQuery("from RegimenDedicacion").getResultList();
	}

	@Override
	public void save(RegimenDedicacion regimenDedicacion) {
		em.persist(regimenDedicacion);
	}

	@Override
	public void update(RegimenDedicacion regimenDedicacion) {
		if(regimenDedicacion.getRegimenDedicacionId() != null && regimenDedicacion.getRegimenDedicacionId() > 0) {
			em.merge(regimenDedicacion);
		}
	}

	@Override
	public RegimenDedicacion findOne(Integer regimenDedicacionId) {
		RegimenDedicacion regimenDedicacion = null;
		if(regimenDedicacionId != null && regimenDedicacionId > 0) {
			regimenDedicacion = em.find(RegimenDedicacion.class, regimenDedicacionId);
		}
		
		return regimenDedicacion;
	}

	@Override
	public void disabled(RegimenDedicacion regimenDedicacion) {
		try {
			StringBuilder builder = new StringBuilder();
			builder.append(" UPDATE tbl_regimen_dedicacion SET fecha_modificacion=?, habilitado=? WHERE regimen_dedicacion_id=?");
			getJdbcTemplate().update(builder.toString(), new Object[] {
					regimenDedicacion.getFechaModificacion(),
					regimenDedicacion.getHabilitado(),
					regimenDedicacion.getRegimenDedicacionId()
			});
		}
		catch(Exception ex) {
			ex.getMessage();
		}
	}

	@Override
	public void delete(Integer regimenDedicacionId) {
		RegimenDedicacion regimenDedicacion = null;
		if(regimenDedicacionId != null && regimenDedicacionId > 0) {
			regimenDedicacion = findOne(regimenDedicacionId);
		}
		em.remove(regimenDedicacion);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegimenDedicacion> findAllEnabled() {
		// TODO Auto-generated method stub
		return em.createQuery("from RegimenDedicacion rd where rd.habilitado = true").getResultList();
	}

}
