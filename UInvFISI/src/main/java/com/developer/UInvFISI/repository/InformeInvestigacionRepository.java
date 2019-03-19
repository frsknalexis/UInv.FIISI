package com.developer.UInvFISI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.entity.InformeInvestigacion;

@Repository("informeInvestigRepository")
public interface InformeInvestigacionRepository extends JpaRepository<InformeInvestigacion, Integer> {

	List<InformeInvestigacion> findByAsignacionAsignacionId(Integer asignacionId);
	
	InformeInvestigacion findByAsignacionAsignacionIdAndInformeAsignacionId(Integer asignacionId, Integer informeAsignacionId);
}
