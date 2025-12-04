package com.roxanna.clienteservidor.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Rubro {
	private String nombre;
	private String descripcion;
	
	@OneToMany(mappedBy = "rubro")
	private List<Alimento> alimentos = 
			new ArrayList<Alimento>();
	
	public Rubro() {
		
	}
	
	
	 public Rubro(String nombre, String descripcion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
	}


	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

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

	 public List<Alimento> getAlimentos() {
		 return alimentos;
	 }

	 public void setAlimentos(List<Alimento> alimentos) {
		 this.alimentos = alimentos;
	 }

	 public int getId() {
		 return id;
	 }

	 public void setId(int id) {
		 this.id = id;
	 }
	
}
