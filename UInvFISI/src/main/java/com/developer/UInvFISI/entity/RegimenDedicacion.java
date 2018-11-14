package com.developer.UInvFISI.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tbl_regimen_dedicacion")
public class RegimenDedicacion implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator="regimendseq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="regimend_seq", name="regimendseq", schema="publuc", allocationSize=1)
	@Column(name="regimen_dedicacion_id", unique=true, nullable=false)
	private Integer regimenDedicacionId;
	
	@Column(name="nombre_regimen", nullable=false)
	private String nombreRegimen;
	
	@Column(name="fecha_registro", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha_registro;
	
	@Column(name="fecha_modificacion", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha_modificacion;
	
	@OneToMany(mappedBy="tblRegimenDed")
	private List<Docente> tblDocente;

	public RegimenDedicacion() {
		// TODO Auto-generated constructor stub
	}

	public RegimenDedicacion(Integer regimenDedicacionId, String nombreRegimen, Date fecha_registro,
			Date fecha_modificacion, List<Docente> tblDocente) {
		this.regimenDedicacionId = regimenDedicacionId;
		this.nombreRegimen = nombreRegimen;
		this.fecha_registro = fecha_registro;
		this.fecha_modificacion = fecha_modificacion;
		this.tblDocente = tblDocente;
	}

	public Integer getRegimenDedicacionId() {
		return regimenDedicacionId;
	}

	public void setRegimenDedicacionId(Integer regimenDedicacionId) {
		this.regimenDedicacionId = regimenDedicacionId;
	}

	public String getNombreRegimen() {
		return nombreRegimen;
	}

	public void setNombreRegimen(String nombreRegimen) {
		this.nombreRegimen = nombreRegimen;
	}

	public Date getFecha_registro() {
		return fecha_registro;
	}

	public void setFecha_registro(Date fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	public Date getFecha_modificacion() {
		return fecha_modificacion;
	}

	public void setFecha_modificacion(Date fecha_modificacion) {
		this.fecha_modificacion = fecha_modificacion;
	}

	public List<Docente> getTblDocente() {
		return tblDocente;
	}

	public void setTblDocente(List<Docente> tblDocente) {
		this.tblDocente = tblDocente;
	}
}
