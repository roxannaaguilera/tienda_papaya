package com.roxanna.clienteservidor.serviciosJPAImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.query.sql.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;

import com.roxanna.clienteservidor.constantesSQL.ConstantesSQL;
import com.roxanna.clienteservidor.model.Alimento;
import com.roxanna.clienteservidor.servicios.ServicioAlimentos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ServicioAlimentosImpl implements ServicioAlimentos {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void registrarAlimento(Alimento alimento) {
		
		try {
			alimento.setImagenPortada(alimento.getImagen().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		entityManager.persist(alimento);
	}

	@Override
	public List<Alimento> obtenerAlimentos() {
		return entityManager.createQuery("select a from Alimento a order by a.id desc").getResultList();
	}

	@Override
	public void borrarAlimento(int id) {
		entityManager.createNativeQuery(
				"delete from CARRITO where ALIMENTO_ID = :id").
				setParameter("id", id).executeUpdate();
		
		entityManager.createNativeQuery(
				"delete from PRODUCTO_PEDIDO where ALIMENTO_ID = :id ").
				setParameter("id", id).executeUpdate();		
		//createNativeQuery lanza sql, no confundir con createQuery
		entityManager.createNativeQuery(
				"delete from tabla_alimentos where id = :id ").
				setParameter("id", id).executeUpdate();
			
	}

	@Override
	public void actualizarAlimento(Alimento alimentoFormEditar) {
		//entityManager.merge(alimento);
		Alimento alimentoBaseDeDatos = entityManager.find(Alimento.class, alimentoFormEditar.getId());
		alimentoBaseDeDatos.setNombre(alimentoFormEditar.getNombre());
		alimentoBaseDeDatos.setDescripcion(alimentoFormEditar.getDescripcion());
		alimentoBaseDeDatos.setPrecio(alimentoFormEditar.getPrecio());
		alimentoBaseDeDatos.setCantidad(alimentoFormEditar.getCantidad());
		alimentoBaseDeDatos.setPeso(alimentoFormEditar.getPeso());
		alimentoBaseDeDatos.setCategoria(alimentoFormEditar.getCategoria());
		if(alimentoFormEditar.getImagen().getSize()>0 ) {
			try {
				alimentoBaseDeDatos.setImagenPortada(alimentoFormEditar.getImagen().getBytes());
			} catch (IOException e) {
				System.out.println("no se pudo procesar el archivo subido");
				e.printStackTrace();
			}
		}
		entityManager.merge(alimentoBaseDeDatos);
	}
	
	
	@Override
	public Alimento obtenerAlimentoPorId(int id) {
		return entityManager.find(Alimento.class, id);
	}

	@Override
	public List<Map<String, Object>> obtenerAlimentosParaFormarJSON() {
		// vamos a lanzar una SQL para pedir exactamente lo que necesitamos 
		Query query = entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_ALIMENTOS_PARA_JSON, Tuple.class);
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

}
