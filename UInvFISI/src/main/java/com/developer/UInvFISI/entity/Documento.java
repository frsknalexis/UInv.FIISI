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
@Table(name="tbl_documentos", schema="public")
public class Documento extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="documentoSeq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="documento_seq", name="documentoSeq", schema="public", allocationSize=1)
	@Column(name="documento_id", unique=true, nullable=false)
	private Integer documentoId;
	
	@Column(name="abreviatura", nullable=false, length=20)
	private String abreviatura;
	
	@Column(name="nombre_documento", nullable=false, length=45)
	private String nombreDocumento;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="documento")
	@JsonIgnore
	private List<Docente> docentes;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="documento1")
	@JsonIgnore
	private List<AsignaturaAlumno> asignaturaAlumnos;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="documento")
	@JsonIgnore
	private List<Autor> autor;

	public Documento() {
		docentes = new ArrayList<Docente>();
		asignaturaAlumnos = new ArrayList<AsignaturaAlumno>();
	}

	public Integer getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(Integer documentoId) {
		this.documentoId = documentoId;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
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

	public List<AsignaturaAlumno> getAsignaturaAlumnos() {
		return asignaturaAlumnos;
	}

	public void setAsignaturaAlumnos(List<AsignaturaAlumno> asignaturaAlumnos) {
		this.asignaturaAlumnos = asignaturaAlumnos;
	}
	
	public void addAsignaturaAlumno(AsignaturaAlumno asignaturaAlumno) {
		asignaturaAlumnos.add(asignaturaAlumno);
	}

	public List<Autor> getAutor() {
		return autor;
	}

	public void setAutor(List<Autor> autor) {
		this.autor = autor;
	}
	
	public void addAutor(Autor autor) {
		this.autor.add(autor);
	}
}	

