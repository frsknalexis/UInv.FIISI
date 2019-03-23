package com.developer.UInvFISI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.entity.InformeTrabajo;

@Repository("informeTrabRepository")
public interface InformeTrabajoRepository extends JpaRepository<InformeTrabajo, Integer> {

	List<InformeTrabajo> findByTrabajoTrabajoId(Integer trabajoId);
	
	InformeTrabajo findByTrabajoTrabajoIdAndInformeTrabajoId(Integer trabajoId, Integer informeTrabajoId);
}
