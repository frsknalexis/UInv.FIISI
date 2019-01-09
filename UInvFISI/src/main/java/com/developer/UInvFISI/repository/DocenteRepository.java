package com.developer.UInvFISI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.entity.Docente;

@Repository("docenteRepository2")
public interface DocenteRepository extends JpaRepository<Docente, Integer> {
	
	@Query("SELECT d FROM Docente d WHERE d.nombresDocente like %?1%")
	List<Docente> findByNombresDocente(String termino);
}
