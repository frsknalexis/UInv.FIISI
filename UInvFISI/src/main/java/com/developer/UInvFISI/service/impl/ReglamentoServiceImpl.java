package com.developer.UInvFISI.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.UInvFISI.dao.ReglamentoDAO;

import com.developer.UInvFISI.entity.Reglamento;
import com.developer.UInvFISI.service.ReglamentoService;

@Service("reglamentoService")
public class ReglamentoServiceImpl  implements ReglamentoService{

	@Autowired
	@Qualifier("reglamentoRepository")
	private ReglamentoDAO reglamentoDAO;

	@Override
	@Transactional(readOnly=true)
	public List<Reglamento> findAll() {
		List<Reglamento> reglamento = reglamentoDAO.findAll();
		return reglamento;
	}

	@Override
	@Transactional
	public void saveOrUpdate(Reglamento reglamento) {

		if(reglamento.getReglamentoId() != null && reglamento.getReglamentoId() > 0) {
			
			reglamento.setFechaModificacion(new Date());
			reglamentoDAO.update(reglamento);
		}
		else {
			
			reglamento.setFechaRegistro(new Date());
			reglamento.setHabilitado(true);
			reglamentoDAO.save(reglamento);
		}
	}

	@Override
	@Transactional
	public Reglamento getByReglamentoId(Integer reglamentoId) {
		
		Reglamento reglamento = null;
		if(reglamentoId != null && reglamentoId > 0) {
			
			reglamento = reglamentoDAO.findOne(reglamentoId);
		}
		return reglamento;
	}
}
