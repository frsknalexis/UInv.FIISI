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
@Table(name = "tbl_informes_asignacion", schema = "public")
public class InformeInvestigacion extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1030110710750653163L;

	@Id
	@GeneratedValue(generator = "informeAsignacionSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "informe_asignacion_seq", name = "informeAsignacionSeq", allocationSize = 1, schema = "public")
	@Column(name="informe_asignacion_id", unique = true, nullable = false)
	private Integer informeAsignacionId;
	
	@Column(name="asunto", length=100, nullable=false)
	private String asunto;
	
	@Column(name="nombre_fichero", length=75)
	private String nombreFichero;
	
	@Column(name="tamanio_fichero", length=10)
	private String tamanioFichero;
	
	@Column(name="formato_fichero", length=20)
	private String formatoFichero;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="asignacion_id")
	private Asignacion asignacion;
	
	public InformeInvestigacion() {
		
	}

	public Integer getInformeAsignacionId() {
		return informeAsignacionId;
	}

	public void setInformeAsignacionId(Integer informeAsignacionId) {
		this.informeAsignacionId = informeAsignacionId;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public String getTamanioFichero() {
		return tamanioFichero;
	}

	public void setTamanioFichero(String tamanioFichero) {
		this.tamanioFichero = tamanioFichero;
	}

	public String getFormatoFichero() {
		return formatoFichero;
	}

	public void setFormatoFichero(String formatoFichero) {
		this.formatoFichero = formatoFichero;
	}

	public Asignacion getAsignacion() {
		return asignacion;
	}

	public void setAsignacion(Asignacion asignacion) {
		this.asignacion = asignacion;
	}
	
}
