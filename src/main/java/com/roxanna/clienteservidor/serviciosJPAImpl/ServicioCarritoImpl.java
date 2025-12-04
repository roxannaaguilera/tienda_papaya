package com.roxanna.clienteservidor.serviciosJPAImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.roxanna.clienteservidor.constantesSQL.ConstantesSQL;
import com.roxanna.clienteservidor.model.Alimento;
import com.roxanna.clienteservidor.model.Carrito;
import com.roxanna.clienteservidor.model.Usuario;
import com.roxanna.clienteservidor.servicios.ServicioCarrito;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ServicioCarritoImpl implements ServicioCarrito{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void agregarProducto(int idUsuario, int idProducto, int cantidad) {
		// aunque seria más eficiente lanzar una sql
		Usuario usuario = (Usuario) entityManager.find(Usuario.class, idUsuario);
		List<Carrito>productosCarrito =  usuario.getProductosCarrito();
		boolean productoEnCarrito = false;
		// ver si el producto está en carrito y en tal caso incrementar su cantidad 
		for (Carrito pc : productosCarrito) {
			if(pc.getAlimento().getId() == idProducto) {	
				productoEnCarrito = true;
				pc.setCantidad(pc.getCantidad() + cantidad);
				entityManager.merge(pc);// actualizamos el registro en carrito
			}
			
		}
		//si el producto no está en el carrito crear un registro nuevo en el carrito 
		if(!productoEnCarrito) {
			Carrito productoCarrito = new Carrito();
			productoCarrito.setUsuario(usuario);
			productoCarrito.setAlimento(entityManager.find(Alimento.class, idProducto));
			productoCarrito.setCantidad(cantidad);
			entityManager.persist(productoCarrito);
		}
	}//end agregarProducto

	@Override
	public List<Map<String, Object>> obtenerProductosCarrito(int idUsuario ) {
		Query query =
				entityManager.createNativeQuery(
						ConstantesSQL.SQL_OBTENER_PRODUCTOS_CARRITO, Tuple.class);
		query.setParameter("usuario_id", idUsuario);
		
		List<Tuple> tuples = query.getResultList();
		
		List<Map<String , Object>> resultado = new ArrayList<>();
		for (Tuple tuple: tuples) {
			Map<String, Object> fila = new HashMap<>();
			for (TupleElement<?> element : tuple.getElements()) {
				fila.put(element.getAlias(), tuple.get(element));
			}
			resultado.add(fila);
		}
		return resultado;
	}

	@Override
	public void quitarProductoCarrito(int idUsuario, int idAlimento) {
		entityManager.createNativeQuery(ConstantesSQL.SQL_ELIMINAR_PRODUCTO_CARRITO).
		setParameter("usuario_id", idUsuario).setParameter("alimento_id", idAlimento).executeUpdate();
		
	}

}//end class
