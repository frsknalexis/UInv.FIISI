package com.developer.UInvFISI.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tbl_documentos")
public class Documento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator="documentoseq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="documento_seq", name="documentoseq", schema="public", allocationSize=1)
	@Column(name="documento_id", unique=true, nullable=false)
	private Integer documentoId;
	
	@Column(name="abreviatura", nullable=false)
	private String abreviatura;
	
	@Column(name="nombre_documento", nullable=false)
	private String nombreDocumento;
	
	@OneToMany(mappedBy="tblDocumento")
	private List<Docente> tblDocente;

	public Documento() {
		// TODO Auto-generated constructor stub
	}

	public Documento(Integer documentoId, String abreviatura, String nombreDocumento) {
		this.documentoId = documentoId;
		this.abreviatura = abreviatura;
		this.nombreDocumento = nombreDocumento;
	}

	public Integer getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(Integer documentoId) {
		this.documentoId = documentoId;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
