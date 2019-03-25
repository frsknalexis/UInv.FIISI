package com.developer.UInvFISI.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.dao.TrabajoDAO;
import com.developer.UInvFISI.entity.Trabajo;

@Repository("trabajoRepository")
public class TrabajoDAOImpl extends JdbcDaoSupport implements TrabajoDAO {
	
	@Autowired
	public TrabajoDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Trabajo> findAll() {
		
		return em.createQuery("from Trabajo").getResultList();
	}

	@Override
	public void save(Trabajo trabajo) {
		
		em.persist(trabajo);
	}

	@Override
	public void update(Trabajo trabajo) {
		
		if(trabajo.getTrabajoId() != null && trabajo.getTrabajoId() > 0) {
			em.merge(trabajo);
		}
	}

	@Override
	public Trabajo findOne(Integer trabajoId) {
		
		Trabajo trabajo = null;
		
		if(trabajoId != null && trabajoId > 0) {
			
			trabajo = em.find(Trabajo.class, trabajoId);
		}
		
		return trabajo;
	}

	@Override
	public void disabled(Trabajo trabajo) {
		
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("UPDATE tbl_trabajo SET fecha_modificacion=?, habilitado=? WHERE trabajo_id=?");
			getJdbcTemplate().update(builder.toString(), new Object[] {
					trabajo.getFechaModificacion(),
					trabajo.getHabilitado(),
					trabajo.getTrabajoId()
			});
		}
		catch(Exception ex) {
			ex.getMessage();
		}
	}

	@Override
	public void delete(Integer trabajoId) {
		
		Trabajo trabajo = null;
		
		if(trabajoId != null && trabajoId > 0) {
			trabajo = findOne(trabajoId);
		}
		
		em.remove(trabajo);
	}

	@Override
	public Long obtenerTotalTrabajos() {
		
		Query query = em.createQuery("select count(t) from Trabajo t");
		return (Long) query.getSingleResult();
	}

}
