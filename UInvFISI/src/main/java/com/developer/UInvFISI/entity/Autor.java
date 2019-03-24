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
@Table(name = "tbl_autor", schema = "public")
public class Autor extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="autorSeq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="autor_seq", name="autorSeq", schema="public", allocationSize=1)
	@Column(name="autor_id", unique=true, nullable=false)
	private Integer autorId;
	
	@Column(name="nombre", nullable=false)
	private String nombre;
	
	@Column(name="nro_documento", nullable=true, length=8)
	private String nroDocumento;
	
	@Column(name="celular", nullable=true, length=12)
	private String celular;
	
	@Column(name="email", nullable=true)
	private String email;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="trabajo_id")
	private Trabajo trabajo;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="documento_id")
	private Documento documento;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="condicion_id")
	private Condicion condicion;
	
	public Autor() {
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Trabajo getTrabajo() {
		return trabajo;
	}

	public void setTrabajo(Trabajo trabajo) {
		this.trabajo = trabajo;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Condicion getCondicion() {
		return condicion;
	}

	public void setCondicion(Condicion condicion) {
		this.condicion = condicion;
	}
	
}
