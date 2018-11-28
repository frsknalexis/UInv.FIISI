package com.developer.UInvFISI.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.dao.ProgramaDAO;
import com.developer.UInvFISI.entity.Programa;

@Repository("programaRepository")
public class ProgramaDAOImpl extends JdbcDaoSupport implements ProgramaDAO {

	@Autowired
	public ProgramaDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Programa> findAll() {
		return em.createQuery("from Programa").getResultList();
	}

	@Override
	public void save(Programa programa) {
		em.persist(programa);
	}

	@Override
	public void update(Programa programa) {
		if(programa.getProgramaId() != null && programa.getProgramaId() > 0) {
			em.merge(programa);
		}
	}

	@Override
	public Programa findOne(Integer programaId) {
		Programa programa = null;
		if(programaId > 0 && programaId != null) {
			programa = em.find(Programa.class, programaId);
		}
		
		return programa;
	}

	@Override
	public void disabled(Programa programa) {
		
		try {
			
			StringBuilder builder = new StringBuilder();
			builder.append(" UPDATE tbl_programa SET fecha_modificacion=?, habilitado=? WHERE programa_id=?");
			getJdbcTemplate().update(builder.toString(), new Object[] {
					programa.getFechaModificacion(),
					programa.getHabilitado(),
					programa.getProgramaId()
			});
			
		}
		catch(Exception ex) {
			ex.getMessage();
		}
	}

	@Override
	public void delete(Integer programaId) {
		Programa programa = null;
		if(programaId != null && programaId > 0) {
			programa = findOne(programaId);
		}
		em.remove(programa);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Programa> findAllEnabled() {
		
		return em.createQuery("from Programa p where p.habilitado = true").getResultList();
	}

}
