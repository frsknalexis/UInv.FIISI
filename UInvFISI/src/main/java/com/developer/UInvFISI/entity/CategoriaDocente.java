package com.developer.UInvFISI.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "tbl_categoria_docentes", schema = "public")
public class CategoriaDocente extends BaseEntity implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "categoriaSeq", strategy = GenerationType.SEQUENCE )
	@SequenceGenerator(sequenceName="categoria_seq", name="categoriaSeq", schema="public" , allocationSize=1)
	@Column(name="categoria_docente_id", unique=true, nullable=false)
	private Integer categoriaDocenteId;
	
	@Column(name="nombre_categoria", nullable=false, length=50)
	private String nombreCategoria;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="categoriaDocente")
	@JsonIgnore
	private List<Docente> docentes;

	public CategoriaDocente() {
		docentes = new ArrayList<Docente>();
	}

	public Integer getCategoriaDocenteId() {
		return categoriaDocenteId;
	}

	public void setCategoriaDocenteId(Integer categoriaDocenteId) {
		this.categoriaDocenteId = categoriaDocenteId;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public List<Docente> getDocentes() {
		return docentes;
	}

	public void setDocentes(List<Docente> docentes) {
		this.docentes = docentes;
	}
	
	public void addDocente(Docente docente) {
		docentes.add(docente);
	}
	
}
