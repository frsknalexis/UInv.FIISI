package com.developer.UInvFISI.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_usuarios", schema = "public")
public class Usuario extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8884008995716701193L;

	@Id
	@GeneratedValue(generator="usuarioSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="usuario_seq", name="usuarioSeq", allocationSize=1, schema="public")
	@Column(name="usuario_id", unique=true, nullable=false)
	private Integer usuarioId;
	
	@Column(name="nombre", nullable=false, length=50)
	@NotEmpty(message="no puede estar vacio")
	private String nombre;
	
	@Column(name="apellidos", nullable=false, length=75)
	@NotEmpty(message="no puede estar vacio")
	private String apellidos;
	
	@Column(name="email", nullable=false, unique=true, length=45)
	@NotEmpty(message="no puede estar vacio")
	@Email(message="no es una direccion de correo valido")
	private String email;
	
	@Column(name="password", nullable=false, length=100)
	@NotEmpty(message="no puede estar vacio")
	@Size(min=4, message="deberia tener al menos 4 caracteres")
	private String password;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="tbl_usuario_roles", joinColumns =  @JoinColumn(name="usuario_id")
	, inverseJoinColumns = @JoinColumn(name="rol_id"))
	private List<Rol> roles;

	public Usuario() {
		
		
	}
	
	public Usuario(String nombre, String apellidos, String email, String password) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.password = password;
	}



	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}
	
}
