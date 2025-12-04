package com.roxanna.clienteservidor.RESTcontrollers.datos;

import java.util.List;
import java.util.Map;

public class ResumenPedido {
	
	//productos que hay en el carrito
	private List<Map<String, Object>> alimentos;
	
	//datos del paso1
	private String nombreCompleto;
	private String apellidos;
	private String direccion;
	private String provincia;
	private String email;
	private String telefono;
	
	//datos paso intermedio
	private String metodoDeEnvio;
	private double costoEnvio;
	private String tiempoEstimadoEntrega;
	
	
	//datos del paso2
	private String titularTarjeta;
	private String numeroTarjeta;
	private String tipoTarjeta;
	
	public List<Map<String, Object>> getAlimentos() {
		return alimentos;
	}
	public void setAlimentos(List<Map<String, Object>> alimentos) {
		this.alimentos = alimentos;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
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
	public String getMetodoDeEnvio() {
		return metodoDeEnvio;
	}
	public void setMetodoDeEnvio(String metodoDeEnvio) {
		this.metodoDeEnvio = metodoDeEnvio;
	}
	public double getCostoEnvio() {
		return costoEnvio;
	}
	public void setCostoEnvio(double costoEnvio) {
		this.costoEnvio = costoEnvio;
	}
	public String getTiempoEstimadoEntrega() {
		return tiempoEstimadoEntrega;
	}
	public void setTiempoEstimadoEntrega(String tiempoEstimadoEntrega) {
		this.tiempoEstimadoEntrega = tiempoEstimadoEntrega;
	}
	public String getTitularTarjeta() {
		return titularTarjeta;
	}
	public void setTitularTarjeta(String titularTarjeta) {
		this.titularTarjeta = titularTarjeta;
	}
	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}
	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}
	public String getTipoTarjeta() {
		return tipoTarjeta;
	}
	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

}