package com.developer.UInvFISI.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.dao.AsignaturaDAO;

import com.developer.UInvFISI.entity.Asignatura;

@Repository("asignaturaRepository")
public class AsignaturaDAOImpl extends JdbcDaoSupport implements AsignaturaDAO {

	
	@Autowired
	public AsignaturaDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Asignatura> findAll() {
		return em.createQuery("from Asignatura").getResultList();
	}

	@Override
	public void save(Asignatura asignatura) {
		em.persist(asignatura);
		
	}

	@Override
	public void update(Asignatura asignatura) {
		if(asignatura.getAsignaturaId() != null && asignatura.getAsignaturaId() > 0) {
			em.merge(asignatura);
		}
	}

	@Override
	public Asignatura findOne(Integer asignaturaId) {
		Asignatura asignatura = null;
		
		if(asignaturaId != null && asignaturaId > 0) {
			
			asignatura = em.find(Asignatura.class, asignaturaId);
		}
		
		return asignatura;
	}

	@Override
	public void disabled(Asignatura asignatura) {
		
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("UPDATE tbl_asignatura SET fecha_modificacion=?, habilitado=? WHERE asignatura_id=?");
			getJdbcTemplate().update(builder.toString(), new Object[] {
				asignatura.getFechaModificacion(),
				asignatura.getHabilitado(),
				asignatura.getAsignaturaId()
			});
		}
		catch(Exception ex) {
			ex.getMessage();
		}
	}

	@Override
	public void delete(Integer asignaturaId) {
	Asignatura asignatura = null;
		
		if(asignaturaId != null && asignaturaId > 0) {
			asignatura = findOne(asignaturaId);
		}
		
		em.remove(asignatura);
	}

	@Override
	public Long obtenerTotalAsignaturas() {
		
		Query query = em.createQuery("select count(a) from Asignatura a");
		return (Long) query.getSingleResult();
	}

}
