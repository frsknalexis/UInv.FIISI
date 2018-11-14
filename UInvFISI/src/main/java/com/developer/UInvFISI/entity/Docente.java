package com.developer.UInvFISI.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tbl_docentes")
public class Docente implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="docenteseq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="docente_seq", name="docenteseq", schema="public", allocationSize=1)
	@Column(name="docente_id", unique=true, nullable=false)
	private Integer docenteId;
	
	@Column(name="apellidos_docente", nullable=false)
	private String apellidosDocente;
	
	@Column(name="nombres_docente", nullable=false)
	private String nombresDocente;
	
	@Column(name="nro_documento", nullable=false)
	private String nroDocumento;
	
	@Column(name="dina_datos_academicos", nullable=false)
	private String dinaDatosAcademicos;
	
	@Column(name="dina_proyectos_investigacion", nullable=false)
	private String dinaProyectosInvestigacion;
	
	@Column(name="codigo_orcid", nullable=false)
	private String codigoOrcid;
	
	@Column(name="publicaciones_orcid", nullable=false)
	private String publicacionesOrcid;
	
	@ManyToOne
	@JoinColumn(name="categoria_docente_id")
	private CategoriaDocente tblCategoriaDoc;
	
	@ManyToOne
	@JoinColumn(name="regimen_dedicacion_id")
	private RegimenDedicacion tblRegimenDed;
	
	@ManyToOne
	@JoinColumn(name="documento_id")
	private Documento tblDocumento;

	public Docente() {
		// TODO Auto-generated constructor stub
	}

	public Docente(Integer docenteId, String apellidosDocente, String nombresDocente, String nroDocumento,
			String dinaDatosAcademicos, String dinaProyectosInvestigacion, String codigoOrcid,
			String publicacionesOrcid, CategoriaDocente tblCategoriaDoc, RegimenDedicacion tblRegimenDed,
			Documento tblDocumento) {
		this.docenteId = docenteId;
		this.apellidosDocente = apellidosDocente;
		this.nombresDocente = nombresDocente;
		this.nroDocumento = nroDocumento;
		this.dinaDatosAcademicos = dinaDatosAcademicos;
		this.dinaProyectosInvestigacion = dinaProyectosInvestigacion;
		this.codigoOrcid = codigoOrcid;
		this.publicacionesOrcid = publicacionesOrcid;
		this.tblCategoriaDoc = tblCategoriaDoc;
		this.tblRegimenDed = tblRegimenDed;
		this.tblDocumento = tblDocumento;
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

	public String getCodigoOrcid() {
		return codigoOrcid;
	}

	public void setCodigoOrcid(String codigoOrcid) {
		this.codigoOrcid = codigoOrcid;
	}

	public String getPublicacionesOrcid() {
		return publicacionesOrcid;
	}

	public void setPublicacionesOrcid(String publicacionesOrcid) {
		this.publicacionesOrcid = publicacionesOrcid;
	}

	public CategoriaDocente getTblCategoriaDoc() {
		return tblCategoriaDoc;
	}

	public void setTblCategoriaDoc(CategoriaDocente tblCategoriaDoc) {
		this.tblCategoriaDoc = tblCategoriaDoc;
	}

	public RegimenDedicacion getTblRegimenDed() {
		return tblRegimenDed;
	}

	public void setTblRegimenDed(RegimenDedicacion tblRegimenDed) {
		this.tblRegimenDed = tblRegimenDed;
	}

	public Documento getTblDocumento() {
		return tblDocumento;
	}

	public void setTblDocumento(Documento tblDocumento) {
		this.tblDocumento = tblDocumento;
	}
}
