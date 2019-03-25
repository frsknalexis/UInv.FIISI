package com.developer.UInvFISI.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.dao.AsignacionDAO;
import com.developer.UInvFISI.entity.Asignacion;

@Repository("asignacionRepository")
public class AsignacionDAOImpl extends JdbcDaoSupport implements AsignacionDAO {
	
	@Autowired
	public AsignacionDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Asignacion> findAll() {
		
		return em.createQuery("from Asignacion").getResultList();
	}

	@Override
	public void save(Asignacion asignacion) {
		
		em.persist(asignacion);
	}

	@Override
	public void update(Asignacion asignacion) {
		
		if(asignacion.getAsignacionId() != null && asignacion.getAsignacionId() > 0) {
			em.merge(asignacion);
		}
	}

	@Override
	public Asignacion findOne(Integer asignacionId) {
		
		Asignacion asignacion = null;
		
		if(asignacionId != null && asignacionId > 0) {
			
			asignacion = em.find(Asignacion.class, asignacionId);
		}
		
		return asignacion;
	}

	@Override
	public void disabled(Asignacion asignacion) {
		
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("UPDATE tbl_asignacion SET fecha_modificacion=?, habilitado=? WHERE asignacion_id=?");
			getJdbcTemplate().update(builder.toString(), new Object[] {
				asignacion.getFechaModificacion(),
				asignacion.getHabilitado(),
				asignacion.getAsignacionId()
			});
		}
		catch(Exception ex) {
			ex.getMessage();
		}
	}

	@Override
	public void delete(Integer asignacionId) {
		
		Asignacion asignacion = null;
		
		if(asignacionId != null && asignacionId > 0) {
			asignacion = findOne(asignacionId);
		}
		
		em.remove(asignacion);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Asignacion> findAllEnabled() {
		
		List<Asignacion> investigaciones = em.createQuery("from Asignacion a where a.habilitado = true").getResultList();
		return investigaciones;
	}

	@Override
	public Long obtenerTotalRegistrosAsignacion() {
		
		Query query = em.createQuery("select count(a) from Asignacion a");
		return (Long) query.getSingleResult();
	}

}
