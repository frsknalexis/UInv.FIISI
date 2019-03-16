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

@Entity
@Table(name="tbl_programa", schema="public")
public class Programa extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4628618002644137270L;

	@Id
	@GeneratedValue(generator="programaSeq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="programa_seq", name="programaSeq", allocationSize=1, schema="public")
	@Column(name="programa_id", nullable=false, unique=true)
	private Integer programaId;
	
	@Column(name="nombre_programa", nullable=false, length=200)
	private String nombrePrograma;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="programa")
	@JsonIgnore
	private List<LineaInvestigacion> lineasInvestigacion;

	public Programa() {
		lineasInvestigacion = new ArrayList<LineaInvestigacion>();
	}

	public Integer getProgramaId() {
		return programaId;
	}

	public void setProgramaId(Integer programaId) {
		this.programaId = programaId;
	}

	public String getNombrePrograma() {
		return nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}

	public List<LineaInvestigacion> getLineasInvestigacion() {
		return lineasInvestigacion;
	}

	public void setLineasInvestigacion(List<LineaInvestigacion> lineasInvestigacion) {
		this.lineasInvestigacion = lineasInvestigacion;
	}
	
	public void addLinea(LineaInvestigacion lineaInvestigacion) {
		lineasInvestigacion.add(lineaInvestigacion);
	}
}
