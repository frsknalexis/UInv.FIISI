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

@Entity
@Table(name="tbl_documentos", schema="public")
public class Documento extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="documentoSeq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="documento_seq", name="documentoSeq", schema="public", allocationSize=1)
	@Column(name="documento_id", unique=true, nullable=false)
	private Integer documentoId;
	
	@Column(name="abreviatura", nullable=false, length=20)
	private String abreviatura;
	
	@Column(name="nombre_documento", nullable=false, length=30)
	private String nombreDocumento;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="documento")
	private List<Docente> docentes;

	public Documento() {
		docentes = new ArrayList<Docente>();
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

	public List<Docente> getDocentes() {
		return docentes;
	}

	public void setDocentes(List<Docente> docentes) {
		this.docentes = docentes;
	}
	
	public void addDocente(Docente docente) {
		docentes.add(docente);
	}
	
}	
