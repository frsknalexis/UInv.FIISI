package com.developer.UInvFISI.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.entity.LineaInvestigacion;

@Repository("lineaInvRepository")
public interface LineaInvestigacionRepository extends JpaRepository<LineaInvestigacion, Integer>{

	@Query("SELECT li FROM LineaInvestigacion li WHERE li.nombreLineaInvestigacion like %?1%")
	List<LineaInvestigacion> findByNombreLineaInvestigacion(String termino);
	
	List<LineaInvestigacion> findByProgramaProgramaId(Integer programaId);
	
	LineaInvestigacion findByProgramaProgramaIdAndLineaInvestigacionId(Integer programaId, Integer lineaInvestigacionId);
}
