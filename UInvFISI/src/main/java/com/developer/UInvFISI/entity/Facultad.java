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
@Table(name="tbl_facultad", schema="public")
public class Facultad extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2917463655523305123L;
	
	@Id
	@GeneratedValue(generator="facultadSeq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="facultad_seq", name="facultadSeq", schema="public", allocationSize=1)
	@Column(name="facultad_id", nullable=false, unique=true)
	private Integer facultadId;
	
	@Column(name="nombre_facultad", nullable=false, length=150)
	private String nombreFacultad;
	
	@Column(name="abreviatura_facultad", nullable=false, length=10)
	private String abreviaturaFacultad;

	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="facultad")
	@JsonIgnore
	private List<AsignacionDocente> asignacionDocentes;
	
	public Facultad() {
		asignacionDocentes = new ArrayList<AsignacionDocente>();
	}

	public Integer getFacultadId() {
		return facultadId;
	}

	public void setFacultadId(Integer facultadId) {
		this.facultadId = facultadId;
	}

	public String getNombreFacultad() {
		return nombreFacultad;
	}

	public void setNombreFacultad(String nombreFacultad) {
		this.nombreFacultad = nombreFacultad;
	}

	public String getAbreviaturaFacultad() {
		return abreviaturaFacultad;
	}

	public void setAbreviaturaFacultad(String abreviaturaFacultad) {
		this.abreviaturaFacultad = abreviaturaFacultad;
	}

	public List<AsignacionDocente> getAsignacionDocentes() {
		return asignacionDocentes;
	}

	public void setAsignacionDocentes(List<AsignacionDocente> asignacionDocentes) {
		this.asignacionDocentes = asignacionDocentes;
	}
	
	public void addAsignacionDocente(AsignacionDocente asignacionDocente) {
		asignacionDocentes.add(asignacionDocente);
	}
}
