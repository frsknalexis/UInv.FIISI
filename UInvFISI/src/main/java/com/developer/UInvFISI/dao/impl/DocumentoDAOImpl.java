package com.developer.UInvFISI.dao.impl;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.dao.DocumentoDAO;
import com.developer.UInvFISI.entity.Documento;

@Repository("documentoRepository")
public class DocumentoDAOImpl extends JdbcDaoSupport implements DocumentoDAO {
	
	@Autowired
	public DocumentoDAOImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> findAll() {
		return em.createQuery("from Documento").getResultList();
	}

	@Override
	public void save(Documento documento) {
		em.persist(documento);
	}
	
	@Override
	public void update(Documento documento) {
		if(documento.getDocumentoId() != null && documento.getDocumentoId() > 0) {
			em.merge(documento);
		}
		
	}

	@Override
	public Documento findOne(Integer documentoId) {
		Documento documento = em.find(Documento.class, documentoId);
		return documento;
	}

	@Override
	public void disabled(Documento documento) {
		try {
			StringBuilder builder = new StringBuilder();
			builder.append(" UPDATE tbl_documentos SET fecha_modificacion=?, habilitado=? WHERE documento_id = ?");
			getJdbcTemplate().update(builder.toString(), new Object[] {
					documento.getFechaModificacion(),
					documento.getHabilitado(),
					documento.getDocumentoId()});
		}
		catch(Exception ex) {
			ex.getMessage();
		}
	}

	@Override
	public void delete(Integer documentoId) {
		Documento documento = findOne(documentoId);
		em.remove(documento);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> findAllEnabled() {
		return em.createQuery("from Documento d where d.habilitado = true").getResultList();
	}
	
}
