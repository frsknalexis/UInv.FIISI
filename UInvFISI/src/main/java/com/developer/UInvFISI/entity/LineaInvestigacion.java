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
@Table(name="tbl_linea_investigacion", schema="public")
public class LineaInvestigacion extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5749771075073078887L;

	@Id
	@GeneratedValue(generator="lineaInvestigacionSeq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="linea_investigacion_seq", name="lineaInvestigacionSeq", allocationSize=1, schema="public")
	@Column(name="linea_investigacion_id", nullable=false, unique=true)
	private Integer lineaInvestigacionId;
	
	@Column(name="nombre_linea_investigacion", nullable=false, length=200)
	private String nombreLineaInvestigacion;
	
	@ManyToOne
	@JoinColumn(name="programa_id")
	private Programa programa;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="lineaInvestigacion")
	@JsonIgnore
	private List<Asignacion> asignaciones;
	
	public LineaInvestigacion() {
		asignaciones = new ArrayList<Asignacion>();
	}

	public Integer getLineaInvestigacionId() {
		return lineaInvestigacionId;
	}

	public void setLineaInvestigacionId(Integer lineaInvestigacionId) {
		this.lineaInvestigacionId = lineaInvestigacionId;
	}

	public String getNombreLineaInvestigacion() {
		return nombreLineaInvestigacion;
	}

	public void setNombreLineaInvestigacion(String nombreLineaInvestigacion) {
		this.nombreLineaInvestigacion = nombreLineaInvestigacion;
	}

	public Programa getPrograma() {
		return programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

	public List<Asignacion> getAsignaciones() {
		return asignaciones;
	}

	public void setAsignaciones(List<Asignacion> asignaciones) {
		this.asignaciones = asignaciones;
	}
	
	public void addAsignacion(Asignacion asignacion) {
		asignaciones.add(asignacion);
	}
		
}
