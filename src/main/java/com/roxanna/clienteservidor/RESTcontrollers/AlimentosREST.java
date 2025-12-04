package com.roxanna.clienteservidor.RESTcontrollers;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roxanna.clienteservidor.model.Alimento;
import com.roxanna.clienteservidor.servicios.ServicioAlimentos;

@RestController
@RequestMapping("alimentosREST/")
public class AlimentosREST {
	
	@Autowired
	private ServicioAlimentos servicioAlimentos;
	
	@RequestMapping("obtener")
	public List<Map<String, Object>> obtenerAlimentos() {
		return servicioAlimentos.obtenerAlimentosParaFormarJSON();
//		return servicioAlimentos.obtenerAlimentos();
	}

}
