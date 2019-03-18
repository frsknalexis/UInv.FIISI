package com.developer.UInvFISI.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tbl_asignacion", schema = "public")
public class Asignacion extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4751826034393696099L;
	
	@Id
	@GeneratedValue(generator="asignacionSeq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="asignacion_seq", name="asignacionSeq", schema="public", allocationSize=1)
	@Column(name="asignacion_id", unique=true, nullable=false)
	private Integer asignacionId;
	
	@Column(name="nombre_investigacion", nullable=false, length=500)
	private String nombreInvestigacion;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="linea_investigacion_id")
	private LineaInvestigacion lineaInvestigacion;
	
	@Column(name="fecha_inicio")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fechaInicio;
	
	@Column(name="fecha_fin")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fechaFin;
	
	@Column(name="anio", nullable=false, precision=17)
	private Integer anio;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="asignacion")
	@JsonIgnore
	private List<AsignacionDocente> asignacionDocentes;

	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="asignacion")
	@JsonIgnore
	private List<InformeInvestigacion> informesInvestigacion;
	
	public Asignacion() {
		asignacionDocentes = new ArrayList<AsignacionDocente>();
		informesInvestigacion = new ArrayList<InformeInvestigacion>();
	}

	public Integer getAsignacionId() {
		return asignacionId;
	}

	public void setAsignacionId(Integer asignacionId) {
		this.asignacionId = asignacionId;
	}

	public String getNombreInvestigacion() {
		return nombreInvestigacion;
	}

	public void setNombreInvestigacion(String nombreInvestigacion) {
		this.nombreInvestigacion = nombreInvestigacion;
	}

	public List<AsignacionDocente> getAsignacionDocentes() {
		return asignacionDocentes;
	}

	public void setAsignacionDocentes(List<AsignacionDocente> asignacionDocentes) {
		this.asignacionDocentes = asignacionDocentes;
	}
	
	public void addAsignacionDocente(AsignacionDocente asignacionDocente) {
		asignacionDocentes.add(asignacionDocente);
	}

	public LineaInvestigacion getLineaInvestigacion() {
		return lineaInvestigacion;
	}

	public void setLineaInvestigacion(LineaInvestigacion lineaInvestigacion) {
		this.lineaInvestigacion = lineaInvestigacion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public List<InformeInvestigacion> getInformesInvestigacion() {
		return informesInvestigacion;
	}

	public void setInformesInvestigacion(List<InformeInvestigacion> informesInvestigacion) {
		this.informesInvestigacion = informesInvestigacion;
	}
	
	public void addInformeInvestigacion(InformeInvestigacion informeInvestigacion) {
		informesInvestigacion.add(informeInvestigacion);
	}
}
