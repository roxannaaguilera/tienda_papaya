package com.roxanna.clienteservidor.controllers.admin;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.roxanna.clienteservidor.model.Alimento;
import com.roxanna.clienteservidor.servicios.ServicioAlimentos;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/")
public class AlimentosController {
	
	@Autowired
	private ServicioAlimentos servicioAlimentos;
	
	@RequestMapping("editarAlimento")
	public String editarAlimento(@RequestParam("id") int id, Model model) {
		Alimento alimento = servicioAlimentos.obtenerAlimentoPorId(id);
		model.addAttribute("alimentoEditar", alimento);
		return "admin/alimentos_editar";
	}
	
	@RequestMapping("guardarCambiosAlimento")
	public String guardarCambiosAlimento(@ModelAttribute("alimentoEditar") Alimento alimentoEditar, Model model) {
		servicioAlimentos.actualizarAlimento(alimentoEditar);
		return obtenerAlimentos(model);
		
	}
	
	@RequestMapping("registrarAlimento")
	public String registrarAlimento(Model model) {
		Alimento a = new Alimento();
		a.setPrecio(1);
		model.addAttribute("nuevoAlimento", a);
		return "admin/alimentos_registro";
	}
	
	@RequestMapping("guardarAlimento")
	public String guardarAlimento(
	        @ModelAttribute("nuevoAlimento") @Valid Alimento nuevoAlimento,
	        BindingResult resultadoValidaciones,
	        Model model) {

	    // Validaciones automáticas de la entidad
	    if (resultadoValidaciones.hasErrors()) {
	        return "admin/alimentos_registro";
	    }

	    // Validación manual de la imagen
	    if (nuevoAlimento.getImagen() == null || nuevoAlimento.getImagen().isEmpty()) {
	        resultadoValidaciones.rejectValue("imagen", "error.imagen", "Debes subir una imagen para el alimento");
	        return "admin/alimentos_registro";
	    }

	    // Validar tipo MIME
	    String contentType = nuevoAlimento.getImagen().getContentType();
	    if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
	        resultadoValidaciones.rejectValue("imagen", "error.imagen", "Solo se permiten imágenes JPG o PNG");
	        return "admin/alimentos_registro";
	    }

	    // Validar tamaño (ejemplo: máximo 2 MB)
	    if (nuevoAlimento.getImagen().getSize() > 2 * 1024 * 1024) {
	        resultadoValidaciones.rejectValue("imagen", "error.imagen", "La imagen no puede superar los 2 MB");
	        return "admin/alimentos_registro";
	    }

	    // Convertir MultipartFile a byte[] antes de guardar
	    try {
	        nuevoAlimento.setImagenPortada(nuevoAlimento.getImagen().getBytes());
	    } catch (IOException e) {
	        resultadoValidaciones.rejectValue("imagen", "error.imagen", "Error al procesar la imagen");
	        return "admin/alimentos_registro";
	    }

	    servicioAlimentos.registrarAlimento(nuevoAlimento);
	    return obtenerAlimentos(model);
	}

	
	@RequestMapping("obtenerAlimentos")
	public String obtenerAlimentos(Model model) {
		model.addAttribute("alimentos", servicioAlimentos.obtenerAlimentos());
		return "admin/alimentos";
	}
	
	@RequestMapping("borrarAlimento")
	public String borrarAlimento(@RequestParam("id")int id, Model model) {
		servicioAlimentos.borrarAlimento(id);
		return obtenerAlimentos(model);
	
	}
	
	
}
