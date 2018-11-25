package com.developer.UInvFISI.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.UInvFISI.dao.DocumentoDAO;
import com.developer.UInvFISI.entity.Documento;
import com.developer.UInvFISI.service.DocumentoService;

@Service("documentoService")
public class DocumentoServiceImpl implements DocumentoService {

	@Autowired
	@Qualifier("documentoRepository")
	private DocumentoDAO documentoDAO;
	
	@Override
	@Transactional(readOnly=true)
	public List<Documento> findAll() {
		List<Documento> documentos = documentoDAO.findAll();
		return documentos;
	}

	@Override
	@Transactional
	public void saveOrUpdate(Documento documento) {
		if(documento.getDocumentoId() != null && documento.getDocumentoId() > 0) {
			documento.setFechaModificacion(new Date());
			documentoDAO.update(documento);
		}
		else {
			documento.setFechaRegistro(new Date());
			documento.setHabilitado(true);
			documentoDAO.save(documento);
		}
	}

	@Override
	@Transactional
	public Documento getByCodigo(Integer documentoId) {
		Documento documento = documentoDAO.findOne(documentoId);
		return documento;
	}
	
	@Override
	public void enabled(Integer documentoId) {
		Documento documento = null;
		if(documentoId != null && documentoId > 0) {
			documento = documentoDAO.findOne(documentoId);
			documento.setFechaModificacion(new Date());
			documento.setHabilitado(true);
		}
		documentoDAO.disabled(documento);
	}

	@Override
	public void delete(Integer documentoId) {
		Documento documento = null;
		if(documentoId != null && documentoId > 0) {
			documento = documentoDAO.findOne(documentoId);
			documento.setFechaModificacion(new Date());
			documento.setHabilitado(false);
		}
		documentoDAO.disabled(documento);
	}

	@Override
	@Transactional
	public void delete2(Integer documentoId) {
		if(documentoId != null && documentoId > 0) {
			documentoDAO.delete(documentoId);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<Documento> findAllEnabled() {
		List<Documento> documentos = documentoDAO.findAllEnabled();
		return documentos;
	}
	
}
