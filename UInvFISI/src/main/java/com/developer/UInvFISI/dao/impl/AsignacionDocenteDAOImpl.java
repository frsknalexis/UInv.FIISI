package com.developer.UInvFISI.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.dao.AsignacionDocenteDAO;
import com.developer.UInvFISI.entity.AsignacionDocente;

@Repository("asignacionDocenteRepository")
public class AsignacionDocenteDAOImpl extends JdbcDaoSupport implements AsignacionDocenteDAO {

	@Autowired
	public AsignacionDocenteDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AsignacionDocente> findAll() {
		
		return em.createQuery("from AsignacionDocente").getResultList();
	}

	@Override
	public void save(AsignacionDocente asignacionDocente) {
		
		em.persist(asignacionDocente);
	}

	@Override
	public void update(AsignacionDocente asignacionDocente) {
		
		if(asignacionDocente.getAsignacionDetalleId() != null && asignacionDocente.getAsignacionDetalleId() > 0) {
			em.merge(asignacionDocente);
		}
	}

	@Override
	public AsignacionDocente findOne(Integer asignacionDetalleId) {
		
		AsignacionDocente asignacionDocente = null;
		
		if(asignacionDetalleId != null && asignacionDetalleId > 0) {
			
			asignacionDocente = em.find(AsignacionDocente.class, asignacionDetalleId);
		}
		
		return asignacionDocente;
	}

	@Override
	public void disabled(AsignacionDocente asignacionDocente) {
		
		try {
			
			StringBuilder builder = new StringBuilder();
			builder.append(" UPDATE tbl_asignacion_detalle SET fecha_modificacion = ?, habilitado = ? WHERE asignacion_detalle_id = ?");
			getJdbcTemplate().update(builder.toString(), new Object[] {
					asignacionDocente.getFechaModificacion(),
					asignacionDocente.getHabilitado(),
					asignacionDocente.getAsignacionDetalleId()
			});
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AsignacionDocente> findByAsignacionIdAndHabilitado(Integer asignacionId) {
		
		Query query = em.createQuery("select ad from AsignacionDocente ad where ad.habilitado = true and ad.asignacion.asignacionId = :asignacionId");
		query.setParameter("asignacionId", asignacionId);
		List<AsignacionDocente> investigadores = query.getResultList();
		return investigadores;
	}
}
