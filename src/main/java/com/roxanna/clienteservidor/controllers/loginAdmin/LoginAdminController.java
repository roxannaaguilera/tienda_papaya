package com.roxanna.clienteservidor.controllers.loginAdmin;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.roxanna.clienteservidor.controllers.admin.AlimentosController;

import jakarta.servlet.http.HttpServletRequest;

//controlador que se encarga de identificar al administrador
@Controller
public class LoginAdminController {
	
	@Autowired
	private AlimentosController alimentosController;
	
	@RequestMapping("loginAdmin")
	public String loginAdmin() {
		return "admin/loginAdmin";
	}
	
	@RequestMapping("logoutAdmin")
	public String logoutAdmin(HttpServletRequest request) {
		request.getSession().invalidate();
		return "tienda_es";
	}
	
}
