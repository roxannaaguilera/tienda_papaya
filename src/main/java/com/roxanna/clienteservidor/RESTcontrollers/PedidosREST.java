package com.roxanna.clienteservidor.RESTcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roxanna.clienteservidor.RESTcontrollers.datos.ResumenPedido;
import com.roxanna.clienteservidor.servicios.ServicioPedidos;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("pedidosREST/")
public class PedidosREST {
	
	@Autowired
	private ServicioPedidos servicioPedidos;
	
	@RequestMapping("paso4")
	public String paso4(HttpServletRequest request) {
		int idUsuario = 
				(int)request.getSession().getAttribute("usuario_id");
		servicioPedidos.confirmarPedido(idUsuario);
		return "ok";
	}
	
	@RequestMapping("paso3")
	public ResumenPedido paso3(String tarjeta, String numero, String titular, 
			HttpServletRequest request) {
		int idUsuario = 
				(int)request.getSession().getAttribute("usuario_id");
		ResumenPedido resumen = 
				servicioPedidos.procesarPaso3(tarjeta,numero,titular, idUsuario);
		return resumen;		
	}
	
	@RequestMapping("paso2")
	public ResumenPedido paso2(String metodo_envio, HttpServletRequest request) {
	    int idUsuario = (int) request.getSession().getAttribute("usuario_id");
	    
	    // El backend calcula el costo y tiempo según el método
	    double costoEnvio = 0;
	    String tiempoEstimadoEntrega = "";
	    
	    switch(metodo_envio) {
	        case "express":
	            costoEnvio = 5.99;
	            tiempoEstimadoEntrega = "24-48 horas";
	            break;
	        case "estandar":
	            costoEnvio = 2.99;
	            tiempoEstimadoEntrega = "3-5 días";
	            break;
	        case "tienda":
	            costoEnvio = 0;
	            tiempoEstimadoEntrega = "Disponible hoy";
	            break;
	    }
	    
	    ResumenPedido resumen = servicioPedidos.procesarPaso2(
	        metodo_envio, costoEnvio, tiempoEstimadoEntrega, idUsuario
	    );
	    
	    return resumen;
	}
	
	@RequestMapping("paso1")
	public String paso1(String nombre, String apellidos, String direccion, String provincia, String email, String telefono, HttpServletRequest request) {
		String respuesta = "";
		int idUsuario = 
				(int)request.getSession().getAttribute("usuario_id");
		servicioPedidos.procesarPaso1(nombre, apellidos, direccion, provincia, email, telefono, idUsuario);
		respuesta = "ok";
		return respuesta;
	}

}
