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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tbl_asignatura", schema="public")
public class Asignatura extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -527204773850608062L;

		
	@Id
	@GeneratedValue(generator="asignaturaSeq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="asignatura_seq", name="asignaturaSeq", schema="public", allocationSize=1)
	@Column(name="asignatura_id", nullable=false, unique=true)
	private Integer asignaturaId;
	
	@Column(name="nombre_asignatura", nullable=false, length=200)
	private String nombreAsignatura;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="escuela_id")
	private Escuela escuela;
	
	@Column(name="nombre_docente", nullable=false, length=100)
	private String nombreDocente;
	
	
	@Column(name="periodo", nullable=false, length=25)
	private String periodo;
	
	@Column(name="ciclo", nullable=false, length=25)
	private String ciclo;
	
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="asignatura")
	@JsonIgnore
	private List<AsignaturaAlumno> asignaturaAlumnos;
	
	public Asignatura() {
		asignaturaAlumnos = new ArrayList<AsignaturaAlumno>();
	}

	
	
	public Integer getAsignaturaId() {
		return asignaturaId;
	}



	public void setAsignaturaId(Integer asignaturaId) {
		this.asignaturaId = asignaturaId;
	}



	public String getNombreAsignatura() {
		return nombreAsignatura;
	}



	public void setNombreAsignatura(String nombreAsignatura) {
		this.nombreAsignatura = nombreAsignatura;
	}



	public Escuela getEscuela() {
		return escuela;
	}



	public void setEscuela(Escuela escuela) {
		this.escuela = escuela;
	}



	public String getNombreDocente() {
		return nombreDocente;
	}



	public void setNombreDocente(String nombreDocente) {
		this.nombreDocente = nombreDocente;
	}



	public String getPeriodo() {
		return periodo;
	}



	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}



	public String getCiclo() {
		return ciclo;
	}



	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
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
	
}
