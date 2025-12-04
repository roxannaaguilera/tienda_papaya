package com.roxanna.clienteservidor.servicios;

import java.util.List;

import com.roxanna.clienteservidor.RESTcontrollers.datos.ResumenPedido;
import com.roxanna.clienteservidor.model.Pedido;


public interface ServicioPedidos {

	
	//gestion en administracion
	List<Pedido> obtenerPedidos();
	Pedido obtenerPedidoPorId(int idPedido);
	void actualizarPedido(int idPedido, String estado);
	
	//operaciones ajax
	void procesarPaso1(String nombre, String apellidos, String direccion, String provincia, String email, String telefono,  int idUsuario);
	ResumenPedido procesarPaso2(String metodoDeEnvio, double costoEnvio, String tiempoEstimadoEntrega, int idUsuario);
	ResumenPedido procesarPaso3(String tarjeta, String numero, String titular, int idUsuario);
	void confirmarPedido(int idUsuario);

}
