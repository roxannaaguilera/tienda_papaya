package com.roxanna.clienteservidor.serviciosJPAImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roxanna.clienteservidor.RESTcontrollers.datos.ResumenPedido;
import com.roxanna.clienteservidor.constantes.Estados;
import com.roxanna.clienteservidor.constantesSQL.ConstantesSQL;
import com.roxanna.clienteservidor.model.Carrito;
import com.roxanna.clienteservidor.model.Pedido;
import com.roxanna.clienteservidor.model.ProductoPedido;
import com.roxanna.clienteservidor.model.Usuario;
import com.roxanna.clienteservidor.servicios.ServicioCarrito;
import com.roxanna.clienteservidor.servicios.ServicioPedidos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Transactional
public class ServicioPedidosImpl implements ServicioPedidos{

	@Autowired
	private ServicioCarrito servicioCarrito;
	
	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public List<Pedido> obtenerPedidos() {
		return entityManager.createQuery(
				"select p from Pedido p order by p.id desc", Pedido.class).getResultList();
	}

	@Override
	public Pedido obtenerPedidoPorId(int idPedido) {
		return (Pedido)entityManager.find(Pedido.class, idPedido);
	}

	@Override
	public void actualizarPedido(int idPedido, String estado) {
		Pedido p = entityManager.find(Pedido.class, idPedido);
		p.setEstado(estado);	
		entityManager.merge(p);
	}
	
	@Override
	public void confirmarPedido(int idUsuario) {
		Pedido p = obtenerPedidoIncompletoActual(idUsuario);
		//obtener los productos en el carrito para meterlos en productoPedido
		Usuario u = entityManager.find(Usuario.class, idUsuario);
		List<Carrito> c = entityManager.createQuery(
				"select c from Carrito c where c.usuario.id = :usuario_id")
				.setParameter("usuario_id", idUsuario)
				.getResultList();
		System.out.println("productos en el carrito a procesar: " + c.size());
		for( Carrito productoCarrito : c ) {
			ProductoPedido pp = new ProductoPedido();
			pp.setCantidad(productoCarrito.getCantidad());
			pp.setAlimento(productoCarrito.getAlimento());
			pp.setPedido(p);
			entityManager.persist(pp);
		}
		p.setEstado(Estados.TERMINADO.name());
		entityManager.merge(p);
		//eliminar los productos del carrito
		entityManager.createNativeQuery(ConstantesSQL.SQL_VACIAR_CARRITO)
		.setParameter("usuario_id", idUsuario).executeUpdate();
		
	}	
	
	@Override
	public void  procesarPaso1(String nombre, String apellidos, String direccion, String provincia, String email, String telefono,  int idUsuario) {
		Pedido p = obtenerPedidoIncompletoActual(idUsuario);
		p.setNombreCompleto(nombre);
		p.setApellidos(apellidos);
		p.setDireccion(direccion);
		p.setProvincia(provincia);
		p.setEmail(email);
		p.setTelefono(telefono);
		entityManager.merge(p);
	}
	
	@Override
	public ResumenPedido procesarPaso2(String metodoDeEnvio, double costoEnvio, String tiempoEstimadoEntrega,
			int idUsuario) {
		Pedido p = obtenerPedidoIncompletoActual(idUsuario);
		p.setMetodoDeEnvio(metodoDeEnvio);
		p.setCostoEnvio(costoEnvio);
		p.setTiempoEstimadoEntrega(tiempoEstimadoEntrega);
		entityManager.merge(p);
		return obtenerResumenDelPedido(idUsuario);
	}	

	
	@Override
	public ResumenPedido procesarPaso3(String tarjeta, String numero, String titular, int idUsuario) {
		Pedido p = obtenerPedidoIncompletoActual(idUsuario);
		p.setTipoTarjeta(tarjeta);
		p.setNumeroTarjeta(numero);
		p.setTitularTarjeta(titular);
		entityManager.merge(p);		
		//preparamos el ResumenPedido a devolver
		return obtenerResumenDelPedido(idUsuario);		
	}
	
	
	
	private ResumenPedido obtenerResumenDelPedido(int idUsuario) {
		Pedido p = obtenerPedidoIncompletoActual(idUsuario);
		ResumenPedido resumen = new ResumenPedido();
		resumen.setNombreCompleto(p.getNombreCompleto());
		resumen.setApellidos(p.getApellidos());	
		resumen.setDireccion(p.getDireccion());
		resumen.setProvincia(p.getProvincia());
		resumen.setMetodoDeEnvio(p.getMetodoDeEnvio());
		resumen.setCostoEnvio(p.getCostoEnvio());
		resumen.setTiempoEstimadoEntrega(p.getTiempoEstimadoEntrega());
		resumen.setTipoTarjeta(p.getTipoTarjeta());
		resumen.setTitularTarjeta(p.getTitularTarjeta());
		//lo siguiente ira ofuscado menos los 4 ultimos numeros
		resumen.setNumeroTarjeta(p.getNumeroTarjeta());
		resumen.setAlimentos(servicioCarrito.obtenerProductosCarrito(idUsuario));
		return resumen;
	}
	
	//este metodo devuelve el pedido incompleto actual del usuario, 
	//si no existe, lo creamos
	private Pedido obtenerPedidoIncompletoActual(int idUsuario) {
		Usuario usuario = entityManager.find(Usuario.class, idUsuario);
		Object pedidoIncompleto = null;
		List<Pedido> pedidos = 
				entityManager.createQuery(
					"select p from Pedido p where p.estado = :estado and p.usuario.id = :usuario_id")
				.setParameter("estado", Estados.INCOMPLETO.name())
				.setParameter("usuario_id", idUsuario).getResultList();
		if(pedidos.size() == 1) {
			pedidoIncompleto = pedidos.get(0);
		}
		Pedido pedido = null;
		if(pedidoIncompleto != null) {
			pedido = (Pedido) pedidoIncompleto;
		}else {
			pedido = new Pedido();
			pedido.setEstado(Estados.INCOMPLETO.name());
			pedido.setUsuario(usuario);
		}
		return pedido;
	}

}











