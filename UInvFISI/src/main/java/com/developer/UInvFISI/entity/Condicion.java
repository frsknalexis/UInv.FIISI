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
@Table(name = "tbl_condicion", schema = "public")
public class Condicion extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "condicionSeq" , strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="condicion_seq", name="condicionSeq", schema="public", allocationSize=1)
	@Column(name="condicion_id", unique=true, nullable=false)
	private Integer condicionId;
	
	@Column(name="nombre_condicion", nullable=false, length=45)
	private String nombreCondicion;

	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="condicion")
	private List<Docente> docentes;
	
	public Condicion() {
		docentes = new ArrayList<Docente>();
	}

	public Integer getCondicionId() {
		return condicionId;
	}

	public void setCondicionId(Integer condicionId) {
		this.condicionId = condicionId;
	}

	public String getNombreCondicion() {
		return nombreCondicion;
	}

	public void setNombreCondicion(String nombreCondicion) {
		this.nombreCondicion = nombreCondicion;
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
