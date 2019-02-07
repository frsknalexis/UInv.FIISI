package com.developer.UInvFISI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.entity.Autor;

@Repository("autorAsRepository")
public interface AutorRepository extends JpaRepository<Autor, Integer> {

	List<Autor> findByTrabajoTrabajoId(Integer trabajoId);
	
	Autor findByTrabajoTrabajoIdAndAutorId(Integer trabajoId, Integer autorId);
	
}
