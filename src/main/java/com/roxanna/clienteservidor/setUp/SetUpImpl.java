package com.roxanna.clienteservidor.setUp;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.roxanna.clienteservidor.model.Alimento;
import com.roxanna.clienteservidor.model.Carrito;
import com.roxanna.clienteservidor.model.Rubro;
import com.roxanna.clienteservidor.model.Usuario;
import com.roxanna.clienteservidor.servicios.ServicioPedidos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;



@Service
@Transactional
public class SetUpImpl implements SetUp{
	
	@Autowired
	private ServicioPedidos servicioPedidos;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void prepararRegistros() {
		TablaSetUp registroSetUp = null;
		// si no hay un registro en la tabla setUp preparamos los registros iniciales
		
		try {
			registroSetUp = (TablaSetUp)entityManager.createQuery("select r from TablaSetUp r").getSingleResult();
		} catch (Exception e) {
			System.out.println("No se encontró ningún registro en la tabla setUp, "
			+ "comenzamos  a registrar los registros iniciales...");
		}
		if(registroSetUp != null && registroSetUp.isCompletado() ) {
			return;
		}
		
		//preparar rubros iniciales:
		Rubro lacteos= new Rubro("lácteos", "Productos de origen animal");
		entityManager.persist(lacteos);
		
		Rubro frutasVerduras = new Rubro("frutas y verduras", "Productos frescos de origen vegetal");
		entityManager.persist(frutasVerduras);

		Rubro carnes = new Rubro("carnes", "Productos de origen animal, carne fresca y embutidos");
		entityManager.persist(carnes);

		Rubro pescadosMariscos = new Rubro("pescados y mariscos", "Productos del mar, frescos o en conserva");
		entityManager.persist(pescadosMariscos);

		Rubro cereales = new Rubro("cereales y derivados", "Pan, arroz, pasta, harinas y cereales");
		entityManager.persist(cereales);

		Rubro dulces = new Rubro("dulces y repostería", "Chocolates, galletas, bollería y caramelos");
		entityManager.persist(dulces);

		
		Alimento alimento1 = new Alimento(
			    "Zanahoria",
			    "verdura crujiente rica en vitamina A, perfecta para ensaladas y jugos naturales",
			    1.0,   // precio válido
			    80,    // peso válido
			    1.0,   // campo validado >= 1.0
			    "verduras"
			);
			alimento1.setRubro(frutasVerduras);

			entityManager.persist(alimento1);
			alimento1.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/alimentos/zanahoria.jpeg"));

			Alimento alimento2 = new Alimento(
			    "Tomate",
			    "fruto rojo jugoso rico en antioxidantes, ideal para salsas y ensaladas frescas",
			    1.0,   // precio válido
			    100,   // peso válido
			    1.0,   // corregido (antes 0.18)
			    "verduras"
			);
			alimento2.setRubro(frutasVerduras);
			entityManager.persist(alimento2);
			alimento2.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/alimentos/tomate.jpeg"));

			Alimento alimento3 = new Alimento(
			    "Leche Entera",
			    "bebida láctea rica en calcio y proteínas, esencial para huesos fuertes y sanos",
			    1.20,  // precio válido
			    60,    // peso válido
			    1.0,   // campo validado >= 1.0
			    "lácteos"
			);
			alimento3.setRubro(lacteos);
			entityManager.persist(alimento3);
			alimento3.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/alimentos/leche_entera.jpeg"));

			Alimento alimento4 = new Alimento(
			    "Pan de Centeno",
			    "pan oscuro con alto contenido de fibra para una alimentación balanceada",
			    1.50,  // precio válido
			    40,    // peso válido
			    1.0,   // corregido (antes 0.45)
			    "panadería"
			);
			alimento4.setRubro(cereales);
			entityManager.persist(alimento4);
			alimento4.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/alimentos/pan_integral.jpeg"));

			Alimento alimento5 = new Alimento(
			    "Manzana Verde",
			    "fruta ácida y refrescante ideal para postres y snacks saludables",
			    1.20,  // precio válido
			    90,    // peso válido
			    1.0,   // corregido (antes 0.22)
			    "frutas"
			);
			alimento5.setRubro(frutasVerduras);
			entityManager.persist(alimento5);
			alimento5.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/alimentos/manzana_verde.jpeg"));

			Alimento alimento6 = new Alimento(
			    "Banana",
			    "fruta dulce rica en potasio, perfecta para deportistas y desayunos nutritivos",
			    1.10,   // precio válido
			    120,    // peso válido
			    1.0,    // campo validado >= 1.0
			    "frutas"
			);
			alimento6.setRubro(frutasVerduras);
			entityManager.persist(alimento6);
			alimento6.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/alimentos/banana.jpeg"));

			Alimento alimento7 = new Alimento(
			    "Yogur Natural",
			    "producto lácteo fermentado,fuente de probióticos para la salud digestiva",
			    1.30,   // precio válido
			    50,     // peso válido
			    1.0,    // campo validado >= 1.0
			    "lácteos"
			);
			alimento7.setRubro(lacteos);
			entityManager.persist(alimento7);
			alimento7.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/alimentos/yogur_natural.jpeg"));

			Alimento alimento8 = new Alimento(
			    "Arroz Integral",
			    "cereal de grano entero con alto contenido de fibra, base perfecta para comidas",
			    2.00,   // precio válido
			    100,    // peso válido
			    1.0,    // campo validado >= 1.0
			    "cereales"
			);
			alimento8.setRubro(cereales);
			entityManager.persist(alimento8);
			alimento8.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/alimentos/arroz_integral.jpeg"));

			Alimento alimento9 = new Alimento(
			    "Queso Cheddar",
			    "queso amarillo de sabor intenso y alto contenido proteico, ideal para gratinar",
			    2.50,   // precio válido
			    80,     // peso válido
			    1.0,    // campo validado >= 1.0
			    "lácteos"
			);
			alimento9.setRubro(lacteos);
			entityManager.persist(alimento9);
			alimento9.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/alimentos/queso_cheddar.jpeg"));

			Alimento alimento10 = new Alimento(
			    "Pepino",
			    "verdura fresca con alto contenido de agua y pocas calorías, perfecta para hidratación",
			    1.90,   // precio válido
			    150,    // peso válido
			    1.0,    // campo validado >= 1.0
			    "verduras"
			);
			alimento10.setRubro(frutasVerduras);
			entityManager.persist(alimento10);
			alimento10.setImagenPortada(obtenerInfoImagen("http://localhost:8080/imagenes_base/alimentos/pepino.jpeg"));

		Usuario usuario1= new Usuario("Roxanna", "Aguilera Ortiz", "123", "raguilera@centronelson.org", "643554252", "C. Ramón Gómez de la serna 125");
		entityManager.persist(usuario1);
		
		Usuario usuario2 = new Usuario("Carlos", "Martinez","abc456", "cfernandez@centronelson.org", "612345678", "Av. de América 45");
		entityManager.persist(usuario2);

		Usuario usuario3 = new Usuario("Lucía","Perez", "pass789", "lmartinez@centronelson.org", "634567890", "Calle Alcalá 210");
		entityManager.persist(usuario3);

		Usuario usuario4 = new Usuario("Diego", "Carrizal","secure321", "dlopez@centronelson.org", "622334455", "Paseo de la Castellana 89");
		entityManager.persist(usuario4);

		Usuario usuario5 = new Usuario("Valeria", "Laferte" ,"clave987", "vgarcia@centronelson.org", "655443322", "Calle Serrano 12");
		entityManager.persist(usuario5);
		
		//vamos a meter unos productos en el carrito de varios usuarios
		
		Carrito registroCarrito = new Carrito();
		registroCarrito.setUsuario(usuario1);
		registroCarrito.setAlimento(alimento1);
		registroCarrito.setCantidad(3);
		entityManager.persist(registroCarrito);
		
		//registrar un pedido para usuario1
		servicioPedidos.procesarPaso1("Roxanna", "Aguilera","123", "raguilera@centronelson.org", "643554252", "C. Ramón Gómez de la serna 125", usuario1.getId());
		servicioPedidos.procesarPaso2("a domicilio", 4.50, "4 dias Hábiles", usuario1.getId());
		servicioPedidos.procesarPaso3("1", "0123 4567 8901 0123", "Ares Sancho", usuario1.getId());
		servicioPedidos.confirmarPedido(usuario1.getId());
		
		Carrito registroCarrito2 = new Carrito();
		registroCarrito2.setUsuario(usuario3);
		registroCarrito2.setAlimento(alimento5);
		registroCarrito2.setCantidad(2);
		entityManager.persist(registroCarrito2);
		
		//Una vez preparados los registros iniciales marcamos el setup completado de la siguiente forma:

		TablaSetUp registro = new TablaSetUp();
		registro.setCompletado(true);
		entityManager.persist(registro);
	}// end prepararRegistros
	
	//método que nos va a permitir obtener un byte[] de cada archivo
	//de imagenes_base
	private byte[] obtenerInfoImagen(String ruta_origen) {
	    try {
	        HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                                         .uri(URI.create(ruta_origen))
	                                         .build();
	        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
	        if (response.statusCode() == 200) {
	            return response.body();
	        } else {
	            System.err.println("Error al descargar la imagen: HTTP " + response.statusCode());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}