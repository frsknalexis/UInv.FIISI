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
@Table(name = "tbl_trabajo", schema = "public")
public class Trabajo extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator="trabajoSeq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="trabajo_seq", name="trabajoSeq", schema="public", allocationSize=1)
	@Column(name="trabajo_id", unique=true, nullable=false)
	private Integer trabajoId;
	
	@Column(name="nombre", nullable=false, length=150)
	private String nombre;
	
	@Column(name="tipo_trabajo", nullable=false)
	private String tipoTrabajo;
	
	@Column(name="cita_trabajo", nullable=false)
	private String citaTrabajo;
	
	@Column(name="grado_academico", nullable=false)
	private String gradoAcademico;
	
	@Column(name="denominacion", nullable=false)
	private String denominacion;
	
	@Column(name="fecha_sustentacion")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fechaSustentacion;

	@Column(name="anio", nullable=false, precision=17)
	private Integer anio;
	
	@Column(name="area_conocimiento", nullable=false)
	private String areaConocimiento;
	
	@Column(name="cantidad_hojas", nullable=false)
	private Integer cantidadHojas;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="trabajo")
	@JsonIgnore
	private List<Autor> autor;

	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="trabajo")
	@JsonIgnore
	private List<InformeTrabajo> informesTrabajo;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="escuela_id")
	private Escuela escuela;

	public Trabajo() {
		autor = new ArrayList<Autor>();
		informesTrabajo = new ArrayList<InformeTrabajo>();
	}
	
	public Integer getTrabajoId() {
		return trabajoId;
	}

	public void setTrabajoId(Integer trabajoId) {
		this.trabajoId = trabajoId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipoTrabajo() {
		return tipoTrabajo;
	}

	public void setTipoTrabajo(String tipoTrabajo) {
		this.tipoTrabajo = tipoTrabajo;
	}

	public String getCitaTrabajo() {
		return citaTrabajo;
	}

	public void setCitaTrabajo(String citaTrabajo) {
		this.citaTrabajo = citaTrabajo;
	}

	public String getGradoAcademico() {
		return gradoAcademico;
	}

	public void setGradoAcademico(String gradoAcademico) {
		this.gradoAcademico = gradoAcademico;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public Date getFechaSustentacion() {
		return fechaSustentacion;
	}

	public void setFechaSustentacion(Date fechaSustentacion) {
		this.fechaSustentacion = fechaSustentacion;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public String getAreaConocimiento() {
		return areaConocimiento;
	}

	public void setAreaConocimiento(String areaConocimiento) {
		this.areaConocimiento = areaConocimiento;
	}

	public Integer getCantidadHojas() {
		return cantidadHojas;
	}

	public void setCantidadHojas(Integer cantidadHojas) {
		this.cantidadHojas = cantidadHojas;
	}

	public Escuela getEscuela() {
		return escuela;
	}

	public void setEscuela(Escuela escuela) {
		this.escuela = escuela;
	}

	public List<Autor> getAutor() {
		return autor;
	}

	public void setAutor(List<Autor> autor) {
		this.autor = autor;
	}

	public void addAutor(Autor autor) {
		this.autor.add(autor);
	}
	
	public List<InformeTrabajo> getInformesTrabajo() {
		return informesTrabajo;
	}

	public void setInformesTrabajo(List<InformeTrabajo> informesTrabajo) {
		this.informesTrabajo = informesTrabajo;
	}
	
	public void addInformeTrabajo(InformeTrabajo informeTrabajo) {
		this.informesTrabajo.add(informeTrabajo);
	}

}
