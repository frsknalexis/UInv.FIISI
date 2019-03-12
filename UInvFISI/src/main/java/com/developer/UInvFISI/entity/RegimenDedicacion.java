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
@Table(name="tbl_regimen_dedicacion", schema="public")
public class RegimenDedicacion extends BaseEntity implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6310875725916693282L;

	@Id
	@GeneratedValue(generator="regimenSeq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="regimen_seq", name="regimenSeq", schema="public", allocationSize=1)
	@Column(name="regimen_dedicacion_id", unique=true, nullable=false)
	private Integer regimenDedicacionId;
	
	@Column(name="nombre_regimen", nullable=false, length=50)
	private String nombreRegimen;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="regimenDedicacion")
	@JsonIgnore
	private List<Docente> docentes;

	public RegimenDedicacion() {
		docentes = new ArrayList<Docente>();
	}

	public Integer getRegimenDedicacionId() {
		return regimenDedicacionId;
	}

	public void setRegimenDedicacionId(Integer regimenDedicacionId) {
		this.regimenDedicacionId = regimenDedicacionId;
	}

	public String getNombreRegimen() {
		return nombreRegimen;
	}

	public void setNombreRegimen(String nombreRegimen) {
		this.nombreRegimen = nombreRegimen;
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
