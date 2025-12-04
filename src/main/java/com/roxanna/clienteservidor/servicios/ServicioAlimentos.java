package com.roxanna.clienteservidor.servicios;

import java.util.List;
import java.util.Map;

import com.roxanna.clienteservidor.model.Alimento;



public interface ServicioAlimentos {
	
	void registrarAlimento(Alimento alimento);
	
	List<Alimento> obtenerAlimentos();
	
	void borrarAlimento(int id);
	
	void actualizarAlimento(Alimento alimento);

	Alimento obtenerAlimentoPorId(int int1);
	
	//para la parte publica . servicios REST
	
	List< Map<String ,Object> > obtenerAlimentosParaFormarJSON ();

}
