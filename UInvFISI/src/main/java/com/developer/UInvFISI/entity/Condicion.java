package com.developer.UInvFISI.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

	
	public Condicion() {
		
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
}
