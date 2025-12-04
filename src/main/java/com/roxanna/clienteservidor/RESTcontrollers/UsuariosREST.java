package com.roxanna.clienteservidor.RESTcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roxanna.clienteservidor.model.Usuario;
import com.roxanna.clienteservidor.servicios.ServicioUsuarios;

import jakarta.servlet.http.HttpServletRequest;



@RestController
@RequestMapping("usuariosREST/")
public class UsuariosREST {

	@Autowired
	private ServicioUsuarios servicioUsuarios;
	
	
	@RequestMapping("login")
	public String login(String email, String pass, HttpServletRequest request){
		Usuario usuario = servicioUsuarios.obtenerUsuarioPorEmailYpass(email, pass);
		String respuesta = "";
		if( usuario != null ) {
			request.getSession().setAttribute("usuario_id", usuario.getId());
			respuesta = "ok," + usuario.getNombre();
		}else {
			respuesta = "error,email o pass incorrectos";
		}
		return respuesta;
	}
	
	@RequestMapping("registrar")
	public String registrar(String nombre, String apellidos, String pass, String email, String telefono, String direccion){
		System.out.println("voy a registrar: " + nombre + " " + apellidos + " " + pass + " " + email + " " + telefono + " " + direccion);
		if(servicioUsuarios.obtenerUsuarioPorEmail(email) != null) {
			return "email ya registrado";
		}		
		servicioUsuarios.registrarUsuario(new Usuario(nombre, apellidos, pass, email, telefono, direccion));
		return "registro ok, ya puedes identificarte";
	}
	
	
}
