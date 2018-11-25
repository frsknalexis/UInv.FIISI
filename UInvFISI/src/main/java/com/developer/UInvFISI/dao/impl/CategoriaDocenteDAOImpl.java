package com.developer.UInvFISI.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.dao.CategoriaDocenteDAO;
import com.developer.UInvFISI.entity.CategoriaDocente;

@Repository("categoriaDocenteRepository")
public class CategoriaDocenteDAOImpl extends JdbcDaoSupport implements CategoriaDocenteDAO {

	@Autowired
	public CategoriaDocenteDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CategoriaDocente> findAll() {
		return em.createQuery("from CategoriaDocente").getResultList();
	}

	@Override
	public void save(CategoriaDocente categoriaDocente) {
		em.persist(categoriaDocente);
	}

	@Override
	public void update(CategoriaDocente categoriaDocente) {
		if(categoriaDocente.getCategoriaDocenteId() != null && categoriaDocente.getCategoriaDocenteId() > 0) {
			em.merge(categoriaDocente);
		}
	}

	@Override
	public CategoriaDocente findOne(Integer categoriaDocenteId) {
		CategoriaDocente categoriaDocente = em.find(CategoriaDocente.class, categoriaDocenteId);
		return categoriaDocente;
	}

	@Override
	public void disabled(CategoriaDocente categoriaDocente) {
		try {
			StringBuilder builder = new StringBuilder();
			builder.append(" UPDATE tbl_categoria_docentes SET fecha_modificacion=?, habilitado=? WHERE categoria_docente_id=?");
			getJdbcTemplate().update(builder.toString(), new Object[] {
					categoriaDocente.getFechaModificacion(),
					categoriaDocente.getHabilitado(),
					categoriaDocente.getCategoriaDocenteId()
			});
		}
		catch(Exception ex) {
			ex.getMessage();
		}
	}

	@Override
	public void delete(Integer categoriaDocenteId) {
		CategoriaDocente categoriaDocente = null;
		if(categoriaDocenteId != null && categoriaDocenteId > 0) {
			categoriaDocente = findOne(categoriaDocenteId);
		}
		em.remove(categoriaDocente);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoriaDocente> findAllEnabled() {
		return em.createQuery("from CategoriaDocente cd where cd.habilitado = true").getResultList();
	}
	
}
