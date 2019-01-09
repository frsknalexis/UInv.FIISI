package com.developer.UInvFISI.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tbl_asignacion_detalle", schema = "public")
public class AsignacionDocente extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7748259298386590780L;

	@Id
	@GeneratedValue(generator="asignacionDetalleSeq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="asignacion_detalle_seq", name="asignacionDetalleSeq", schema="public", allocationSize=1)
	private Integer asignacionDetalleId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="asignacion_id")
	@JsonBackReference
	private Asignacion asignacion;
	
	@Column(name="investigador", nullable=false, length=150)
	private String investigador;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="condicion_id")
	@JsonBackReference
	private Condicion condicion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="facultad_id")
	@JsonBackReference
	private Facultad facultad;

	public AsignacionDocente() {
		
	}

	public Integer getAsignacionDetalleId() {
		return asignacionDetalleId;
	}

	public void setAsignacionDetalleId(Integer asignacionDetalleId) {
		this.asignacionDetalleId = asignacionDetalleId;
	}

	public Asignacion getAsignacion() {
		return asignacion;
	}

	public void setAsignacion(Asignacion asignacion) {
		this.asignacion = asignacion;
	}

	public Condicion getCondicion() {
		return condicion;
	}

	public void setCondicion(Condicion condicion) {
		this.condicion = condicion;
	}

	public String getInvestigador() {
		return investigador;
	}

	public void setInvestigador(String investigador) {
		this.investigador = investigador;
	}

	public Facultad getFacultad() {
		return facultad;
	}

	public void setFacultad(Facultad facultad) {
		this.facultad = facultad;
	}

}