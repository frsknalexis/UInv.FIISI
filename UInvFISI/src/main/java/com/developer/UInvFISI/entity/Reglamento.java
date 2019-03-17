package com.developer.UInvFISI.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_reglamento", schema = "public")
public class Reglamento extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7898314725559003048L;

	@Id
	@GeneratedValue(generator = "reglamentoSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName = "reglamento_seq", name = "reglamentoSeq", allocationSize = 1, schema = "public")
	@Column(name="reglamento_id", unique = true, nullable = false)
	private Integer reglamentoId;
	
	@Column(name="asunto", length=100, nullable=false)
	private String asunto;
	
	@Column(name="nombre_fichero", length=150)
	private String nombreFichero;
	
	@Column(name="tamanio_fichero", length=10)
	private String tamanioFichero;
	
	@Column(name="formato_fichero", length=20)
	private String formatoFichero;
	
	
	public Reglamento() {
		
	}


	public Integer getReglamentoId() {
		return reglamentoId;
	}


	public void setReglamentoId(Integer reglamentoId) {
		this.reglamentoId = reglamentoId;
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
	
	
	
}
