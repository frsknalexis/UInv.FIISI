package com.developer.UInvFISI.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tbl_roles", schema="public")
public class Rol extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7431739129581200482L;
	
	@Id
	@GeneratedValue(generator="rolSeq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="rol_seq", name="rolSeq", allocationSize=1, schema="public")
	@Column(name="rol_id", unique=true, nullable=false)
	private Integer rolId;
	
	@Column(name="nombre", nullable=false, unique=true, length=45)
	private String nombre;
	
	@Column(name="descripcion", length=100)
	private String descripcion;
	
	@ManyToMany(cascade=CascadeType.ALL, mappedBy="roles")
	private List<Usuario> usuarios;
	
	public Rol() {
		
	}
	
	public Rol(String nombre, List<Usuario> usuarios) {
		super();
		this.nombre = nombre;
		this.usuarios = usuarios;
	}

	public Rol(String nombre, String descripcion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public Integer getRolId() {
		return rolId;
	}

	public void setRolId(Integer rolId) {
		this.rolId = rolId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}
