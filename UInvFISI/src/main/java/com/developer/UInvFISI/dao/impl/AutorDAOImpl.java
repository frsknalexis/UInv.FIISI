package com.developer.UInvFISI.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.dao.AutorDAO;
import com.developer.UInvFISI.entity.Autor;

@Repository("autorRepository")
public class AutorDAOImpl extends JdbcDaoSupport implements AutorDAO {

	@Autowired
	public AutorDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Autor> findAll() {
		
		return em.createQuery("from Autor").getResultList();
	}

	@Override
	public void save(Autor autor) {
		
		em.persist(autor);
	}

	@Override
	public void update(Autor autor) {
		
		if(autor.getAutorId() != null && autor.getAutorId() > 0) {
			em.merge(autor);
		}
	}

	@Override
	public Autor findOne(Integer autorId) {
		
		Autor autor = null;
		
		if(autorId != null && autorId > 0) {
			
			autor = em.find(Autor.class, autorId);
		}
		
		return autor;
	}

	@Override
	public void disabled(Autor autor) {
		
		try {
			
			StringBuilder builder = new StringBuilder();
			builder.append(" UPDATE tbl_autor SET fecha_modificacion = ?, habilitado = ? WHERE autor_id = ?");
			getJdbcTemplate().update(builder.toString(), new Object[] {
				
					autor.getFechaModificacion(),
					autor.getHabilitado(),
					autor.getAutorId()
			});
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
