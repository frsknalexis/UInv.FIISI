package com.developer.UInvFISI.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.dao.DocenteDAO;
import com.developer.UInvFISI.entity.Docente;

@Repository("docenteRepository")
public class DocenteDAOImpl extends JdbcDaoSupport implements DocenteDAO {

	public DocenteDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Docente> findAll() {
		return em.createQuery("from Docente").getResultList();
	}

	@Override
	public void save(Docente docente) {
		em.persist(docente);
	}

	@Override
	public void update(Docente docente) {
		if(docente.getDocenteId() != null && docente.getDocenteId() > 0) {
			em.merge(docente);
		}
	}

	@Override
	public Docente findOne(Integer docenteId) {
		Docente docente = null;
		if(docenteId != null && docenteId > 0) {
			docente = em.find(Docente.class, docenteId);
		}
		return docente;
	}

	@Override
	public void disabled(Docente docente) {
		
		try {
			StringBuilder builder = new StringBuilder();
			builder.append(" UPDATE tbl_docentes SET fecha_modificacion=?, habilitado=? WHERE docente_id=?");
			getJdbcTemplate().update(builder.toString(), new Object[] {
					docente.getFechaModificacion(),
					docente.getHabilitado(),
					docente.getDocenteId()
			});
		}
		catch(Exception ex) {
			ex.getMessage();
		}
		
	}

	@Override
	public void delete(Integer docenteId) {
		Docente docente = null;
		if(docenteId != null && docenteId > 0) {
			docente = findOne(docenteId);
		}
		em.remove(docente);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Docente> findAllEnabled() {
		
		return em.createQuery("from Docente d where d.habilitado = true").getResultList();
	}

	@Override
	public Long obtenerTotalRegistrosDocentes() {
		
		Query query = em.createQuery("select count(d) from Docente d");
		return (Long) query.getSingleResult();
	}
	
}
