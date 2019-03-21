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

@Entity
@Table(name = "tbl_informes_trimestrales", schema = "public")
public class InformeTrimestral extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2203150453836381116L;
	
	@Id
	@GeneratedValue(generator="informeTrimestralSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="informe_trimestral_seq", name="informeTrimestralSeq", allocationSize=1, schema="public")
	@Column(name="informe_trimestral_id", nullable=false, unique=true)
	private Integer informeTrimestralId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="asignacion_detalle_id")
	private AsignacionDocente asignacionDetalle;
	
	@Column(name="descripcion", nullable=false, length=100)
	private String descripcion;
	
	@Column(name="trimestre", nullable=false, length=50)
	private String trimestre;
	
	@Column(name="nombre_fichero", nullable=false, length=150)
	private String nombreFichero;
	
	@Column(name="tamanio_fichero", nullable=false)
	private Long tamanioFichero;
	
	@Column(name="formato_fichero", nullable=false, length=150)
	private String formatoFichero;

	public InformeTrimestral() {
		
	}

	public Integer getInformeTrimestralId() {
		return informeTrimestralId;
	}

	public void setInformeTrimestralId(Integer informeTrimestralId) {
		this.informeTrimestralId = informeTrimestralId;
	}

	public AsignacionDocente getAsignacionDetalle() {
		return asignacionDetalle;
	}

	public void setAsignacionDetalle(AsignacionDocente asignacionDetalle) {
		this.asignacionDetalle = asignacionDetalle;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTrimestre() {
		return trimestre;
	}

	public void setTrimestre(String trimestre) {
		this.trimestre = trimestre;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public Long getTamanioFichero() {
		return tamanioFichero;
	}

	public void setTamanioFichero(Long tamanioFichero) {
		this.tamanioFichero = tamanioFichero;
	}

	public String getFormatoFichero() {
		return formatoFichero;
	}

	public void setFormatoFichero(String formatoFichero) {
		this.formatoFichero = formatoFichero;
	}
	
}
