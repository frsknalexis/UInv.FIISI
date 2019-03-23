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
@Table(name = "tbl_informes_trabajo", schema = "public")
public class InformeTrabajo extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "informeTrabajoSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "informe_trabajo_seq", name = "informeTrabajoSeq", allocationSize = 1, schema = "public")
	@Column(name="informe_trabajo_id", unique = true, nullable = false)
	private Integer informeTrabajoId;
	
	@Column(name="asunto", length=100, nullable=false)
	private String asunto;
	
	@Column(name="nombre_fichero", length=75)
	private String nombreFichero;
	
	@Column(name="tamanio_fichero", length=10)
	private String tamanioFichero;
	
	@Column(name="formato_fichero", length=75)
	private String formatoFichero;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="trabajo_id")
	private Trabajo trabajo;
	
	public InformeTrabajo() {
		
	}

	public Integer getInformeTrabajoId() {
		return informeTrabajoId;
	}

	public void setInformeTrabajoId(Integer informeTrabajoId) {
		this.informeTrabajoId = informeTrabajoId;
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

	public Trabajo getTrabajo() {
		return trabajo;
	}

	public void setTrabajo(Trabajo trabajo) {
		this.trabajo = trabajo;
	}

}
