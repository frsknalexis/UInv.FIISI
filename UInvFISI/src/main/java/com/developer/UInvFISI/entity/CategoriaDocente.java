package com.developer.UInvFISI.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_categoria_docentes")
public class CategoriaDocente implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="categoriaseq",strategy=GenerationType.SEQUENCE )
	@SequenceGenerator(sequenceName="categoria_seq", name ="categoriaseq", schema="public" ,allocationSize = 1)
	@Column(name="categoria_docente_id", unique=true, nullable=false)
	private Integer categoriaDocenteId;
	
	@Column(name="nombre_categoria", nullable=false)
	private String nombreCategoria;
	
	@Column(name="fecha_registro", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRegistro;
	
	@Column(name="fecha_modificacion", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificacion;

	public CategoriaDocente() {
		// TODO Auto-generated constructor stub
	}

	public CategoriaDocente(Integer categoriaDocenteId, String nombreCategoria, Date fechaRegistro,
			Date fechaModificacion) {
		this.categoriaDocenteId = categoriaDocenteId;
		this.nombreCategoria = nombreCategoria;
		this.fechaRegistro = fechaRegistro;
		this.fechaModificacion = fechaModificacion;
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

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
}
