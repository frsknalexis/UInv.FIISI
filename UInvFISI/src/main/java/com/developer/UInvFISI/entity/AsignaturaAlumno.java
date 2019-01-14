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
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="asignatura_id")
	@JsonBackReference
	private Asignatura asignatura;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="documento_id")
	@JsonBackReference
	private Documento documento1;
	
	@Column(name="nro_documento", nullable=false, length=10)
	private String nroDocumento;
	
	

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
	
	
}