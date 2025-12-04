 package com.roxanna.clienteservidor.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

//si no ponemos el @Table(name = "nombre_tabla") el nombre de la tabla
//será el mismo nombre de la clase , empezando por minúscula
@Entity
public class Usuario {
	
	private String nombre;
	private String apellidos;
	private String pass;
	
	@Column(unique = true)
	private String email;
	
	private String telefono;
	private String direccion;
	
	@OneToMany(mappedBy = "usuario")
	private List<Carrito> productosCarrito =
		new ArrayList<Carrito>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	
	public Usuario(String nombre, String apellidos, String pass, String email, String telefono, String direccion) {
		super();
		this.nombre = nombre;
		this.apellidos =apellidos;
		this.pass = pass;
		this.email = email;
		this.telefono = telefono;
		this.direccion = direccion;
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

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public List<Carrito> getProductosCarrito() {
		return productosCarrito;
	}

	public void setProductosCarrito(List<Carrito> productosCarrito) {
		this.productosCarrito = productosCarrito;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}