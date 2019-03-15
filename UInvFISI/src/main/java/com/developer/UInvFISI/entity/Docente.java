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
@Table(name="tbl_docentes", schema="public")
public class Docente extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6261053429578225112L;

	@Id
	@GeneratedValue(generator="docenteSeq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="docente_seq", name="docenteSeq", schema="public", allocationSize=1)
	@Column(name="docente_id", nullable=false, unique=true)
	private Integer docenteId;
	
	@Column(name="apellidos_docente", nullable=false, length=100)
	private String apellidosDocente;
	
	@Column(name="nombres_docente", nullable=false, length=50)
	private String nombresDocente;
	
	@Column(name="nro_documento", nullable=false, length=8)
	private String nroDocumento;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="categoria_docente_id")
	private CategoriaDocente categoriaDocente;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="regimen_dedicacion_id")
	private RegimenDedicacion regimenDedicacion;
	
	@Column(name="dina_datos_academicos", length=5, nullable=false)
	private String dinaDatosAcademicos;
	
	@Column(name="dina_proyectos_investigacion", length=5, nullable=false)
	private String dinaProyectosInvestigacion;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="documento_id")
	private Documento documento;
	
	@Column(name="codigo_orcid", length=100, nullable=false)
	private String codigoOrcid;
	
	@Column(name="publicaciones_orcid", nullable=false)
	private Integer publicacionesOrcid;
	
	
	
	public Docente() {
		
	}
	
	
	public Integer getDocenteId() {
		return docenteId;
	}

	public void setDocenteId(Integer docenteId) {
		this.docenteId = docenteId;
	}

	public String getApellidosDocente() {
		return apellidosDocente;
	}

	public void setApellidosDocente(String apellidosDocente) {
		this.apellidosDocente = apellidosDocente;
	}

	public String getNombresDocente() {
		return nombresDocente;
	}

	public void setNombresDocente(String nombresDocente) {
		this.nombresDocente = nombresDocente;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public CategoriaDocente getCategoriaDocente() {
		return categoriaDocente;
	}

	public void setCategoriaDocente(CategoriaDocente categoriaDocente) {
		this.categoriaDocente = categoriaDocente;
	}

	public RegimenDedicacion getRegimenDedicacion() {
		return regimenDedicacion;
	}

	public void setRegimenDedicacion(RegimenDedicacion regimenDedicacion) {
		this.regimenDedicacion = regimenDedicacion;
	}

	public String getDinaDatosAcademicos() {
		return dinaDatosAcademicos;
	}

	public void setDinaDatosAcademicos(String dinaDatosAcademicos) {
		this.dinaDatosAcademicos = dinaDatosAcademicos;
	}

	public String getDinaProyectosInvestigacion() {
		return dinaProyectosInvestigacion;
	}

	public void setDinaProyectosInvestigacion(String dinaProyectosInvestigacion) {
		this.dinaProyectosInvestigacion = dinaProyectosInvestigacion;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public String getCodigoOrcid() {
		return codigoOrcid;
	}

	public void setCodigoOrcid(String codigoOrcid) {
		this.codigoOrcid = codigoOrcid;
	}

	public Integer getPublicacionesOrcid() {
		return publicacionesOrcid;
	}

	public void setPublicacionesOrcid(Integer publicacionesOrcid) {
		this.publicacionesOrcid = publicacionesOrcid;
	}


	
}
