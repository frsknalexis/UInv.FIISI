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
@Table(name = "tbl_asignatura_detalle", schema = "public")
public class AsignaturaAlumno extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="asignaturaDetalleSeq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="asignatura_detalle_seq", name="asignaturaDetalleSeq", schema="public", allocationSize=1)
	private Integer asignaturaDetalleId;
	
	@Column(name="alumno", nullable=false, length=200)
	private String alumno;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="asignatura_id")
	private Asignatura asignatura;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="documento_id")
	private Documento documento1;
	
	@Column(name="nro_documento", nullable=false, length=10)
	private String nroDocumento;
	
	@Column(name="asunto", length=100, nullable=false)
	private String asunto;
	
	@Column(name="nombre_fichero", length=75)
	private String nombreFichero;
	
	@Column(name="tamanio_fichero", length=10)
	private String tamanioFichero;
	
	@Column(name="formato_fichero", length=20)
	private String formatoFichero;
	
	

	public AsignaturaAlumno() {
		
	}



	public Integer getAsignaturaDetalleId() {
		return asignaturaDetalleId;
	}



	public void setAsignaturaDetalleId(Integer asignaturaDetalleId) {
		this.asignaturaDetalleId = asignaturaDetalleId;
	}



	public String getAlumno() {
		return alumno;
	}



	public void setAlumno(String alumno) {
		this.alumno = alumno;
	}



	public Asignatura getAsignatura() {
		return asignatura;
	}



	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}



	public Documento getDocumento1() {
		return documento1;
	}



	public void setDocumento1(Documento documento1) {
		this.documento1 = documento1;
	}



	public String getNroDocumento() {
		return nroDocumento;
	}



	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
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
