package com.roxanna.clienteservidor.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tabla_alimentos")
public class Alimento {

    @Size(min = 3, max = 40, message = "El nombre debe tener entre 3 y 40 caracteres")
    @NotEmpty(message = "Debes insertar un nombre")
    @Pattern(regexp = "[A-Za-z0-9 áéíóúÁÉÍÓÚñÑ]+", message = "El nombre solo puede contener letras, números y espacios")
    private String nombre;

    @Size(max = 200, message = "La descripción no puede superar los 200 caracteres")
    private String descripcion;

    @NotNull(message = "Debes indicar un precio")
    @Min(value = 1, message = "El precio mínimo es de 1 euro")
    @Max(value = 999, message = "El precio máximo es de 999 euros")
    private double precio;

    @NotNull(message = "Debes indicar la cantidad")
    @Min(value = 1, message = "La cantidad mínima es 1 unidad")
    @Max(value = 1000, message = "La cantidad máxima es 1000 unidades")
    private int cantidad;

    @NotNull(message = "Debes indicar el peso")
    @Min(value = 1, message = "El peso mínimo es 1 gramo")
    @Max(value = 10000, message = "El peso máximo es 10 kg")
    private double peso;

    @NotEmpty(message = "Debes indicar una categoría")
    @Size(min = 3, max = 30, message = "La categoría debe tener entre 3 y 30 caracteres")
    private String categoria;

    @Lob
    @Column(name = "imagen_portada", columnDefinition = "LONGBLOB")
    private byte[] imagenPortada;

    @Transient
    private MultipartFile imagen;

    @OneToMany(mappedBy = "alimento")
    private List<Carrito> carritos = new ArrayList<>();

    
    @ManyToOne
    @JoinColumn(name = "rubro_id")
    private Rubro rubro;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


	public Alimento() {
	
	}
	
	public Alimento(String nombre, String descripcion, double precio, int cantidad, double peso, String categoria) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.cantidad = cantidad;
		this.peso = peso;
		this.categoria = categoria;
	}



	public Alimento(String nombre, String descripcion, double precio, int cantidad, double peso, String categoria,
			int id) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.cantidad = cantidad;
		this.peso = peso;
		this.categoria = categoria;
		this.id = id;
		
		
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

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public byte[] getImagenPortada() {
		return imagenPortada;
	}

	public void setImagenPortada(byte[] imagenPortada) {
		this.imagenPortada = imagenPortada;
	}

	public MultipartFile getImagen() {
		return imagen;
	}

	public void setImagen(MultipartFile imagen) {
		this.imagen = imagen;
	}

	public List<Carrito> getCarritos() {
		return carritos;
	}

	public void setCarritos(List<Carrito> carritos) {
		this.carritos = carritos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Rubro getRubro() {
		return rubro;
	}

	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}
	
	
}
