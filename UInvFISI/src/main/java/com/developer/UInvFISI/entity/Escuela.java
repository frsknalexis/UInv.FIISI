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
@Table(name="tbl_escuela", schema="public")
public class Escuela extends BaseEntity implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4514440967792279072L;
	
	@Id
	@GeneratedValue(generator="escuelaSeq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="escuela_seq", name="escuelaSeq", schema="public", allocationSize=1)
	@Column(name="escuela_id", nullable=false, unique=true)
	private Integer escuelaId;
	
	@Column(name="nombre_escuela", nullable=false, length=150)
	private String nombreEscuela;
	
	@Column(name="director_escuela", nullable=false, length=150)
	private String directorEscuela;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="escuela")
	@JsonIgnore
	private List<Asignatura> asignaturas;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="escuela")
	@JsonIgnore
	private List<Trabajo> trabajo;
	
	public Escuela() {
		asignaturas = new ArrayList<Asignatura>();
	}

	public Integer getEscuelaId() {
		return escuelaId;
	}

	public void setEscuelaId(Integer escuelaId) {
		this.escuelaId = escuelaId;
	}

	public String getNombreEscuela() {
		return nombreEscuela;
	}

	public void setNombreEscuela(String nombreEscuela) {
		this.nombreEscuela = nombreEscuela;
	}

	public String getDirectorEscuela() {
		return directorEscuela;
	}

	public void setDirectorEscuela(String directorEscuela) {
		this.directorEscuela = directorEscuela;
	}

	public List<Asignatura> getAsignaturas() {
		return asignaturas;
	}

	public void setAsignaturas(List<Asignatura> asignaturas) {
		this.asignaturas = asignaturas;
	}

	public void addAsignatura(Asignatura asignatura) {
		asignaturas.add(asignatura);
	}
	
	public List<Trabajo> getTrabajo() {
		return trabajo;
	}

	public void setTrabajo(List<Trabajo> trabajo) {
		this.trabajo = trabajo;
	}
}
