package com.developer.UInvFISI.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.dao.LineaInvestigacionDAO;
import com.developer.UInvFISI.entity.LineaInvestigacion;

@Repository("lineaInvestigacionRepository")
public class LineaInvestigacionDAOImpl extends JdbcDaoSupport implements LineaInvestigacionDAO {

	@Autowired
	public LineaInvestigacionDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LineaInvestigacion> findAll() {
		return em.createQuery("from LineaInvestigacion").getResultList();
	}

	@Override
	public void save(LineaInvestigacion lineaInvestigacion) {
		em.persist(lineaInvestigacion);
	}

	@Override
	public void update(LineaInvestigacion lineaInvestigacion) {
		if(lineaInvestigacion.getLineaInvestigacionId() != null
				&& lineaInvestigacion.getLineaInvestigacionId() > 0) {
			em.merge(lineaInvestigacion);
		}
	}

	@Override
	public LineaInvestigacion findOne(Integer lineaInvestigacionId) {
		 LineaInvestigacion lineaInvestigacion = null;
		 if(lineaInvestigacionId != null &&
				 lineaInvestigacionId > 0) {
			 lineaInvestigacion = em.find(LineaInvestigacion.class, lineaInvestigacionId);
		 }
		return lineaInvestigacion;
	}

	@Override
	public void disabled(LineaInvestigacion lineaInvestigacion) {
		
		try {
			StringBuilder builder = new StringBuilder();
			builder.append(" UPDATE tbl_linea_investigacion SET fecha_modificacion=?, habilitado=? WHERE linea_investigacion_id=?");
			getJdbcTemplate().update(builder.toString(), new Object[] {
					lineaInvestigacion.getFechaModificacion(),
					lineaInvestigacion.getHabilitado(),
					lineaInvestigacion.getLineaInvestigacionId()
			});
		}
		catch(Exception ex) {
			ex.getMessage();
		}
	}

	@Override
	public void delete(Integer lineaInvestigacionId) {
		LineaInvestigacion lineaInvestigacion = null;
		if(lineaInvestigacionId != null &&
				lineaInvestigacionId > 0) {
			lineaInvestigacion = findOne(lineaInvestigacionId);
		}
		em.remove(lineaInvestigacion);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LineaInvestigacion> findAllEnabled() {
		
		List<LineaInvestigacion> lineasInvestigacion = em.createQuery("from LineaInvestigacion li where li.habilitado = true").getResultList();
		return lineasInvestigacion;
	}

}
