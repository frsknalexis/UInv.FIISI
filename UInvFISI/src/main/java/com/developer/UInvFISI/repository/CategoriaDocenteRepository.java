package com.developer.UInvFISI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.developer.UInvFISI.entity.CategoriaDocente;

@Repository("catRepository")
public interface CategoriaDocenteRepository extends JpaRepository<CategoriaDocente, Integer> {

	 @Query("select cd from CategoriaDocente cd where cd.nombreCategoria like %?1%")
	List<CategoriaDocente> findByNombreCategoria(String termino); 
}
