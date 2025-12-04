package com.roxanna.clienteservidor.serviciosJPAImpl;

import java.util.List;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roxanna.clienteservidor.model.Usuario;
import com.roxanna.clienteservidor.servicios.ServicioUsuarios;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;



@Service

public class ServicioUsuariosImpl implements ServicioUsuarios{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	@Override
	public void registrarUsuario(Usuario usuario) {
		entityManager.persist(usuario);
		
	}

	@Override
	public List<Usuario> obtenerUsuarios() {
		//ahora en JPA no hay criteria
		//para obtener datos y hacer consultas tenemos estas opciones
		//SQL nativo
		//JPQL - pseudo SQl que devuelve objetos
		List<Usuario> usuarios =
				entityManager.createQuery(
						"select u from Usuario u").getResultList();
		return usuarios;
	}

	@Override
	public void borrarUsuario(int id) {
		Usuario usuarioAborrar = obtenerUsuarioPorId(id);
		if(usuarioAborrar != null) {
			entityManager.remove(usuarioAborrar);
		}
		
	}

	@Override
	public Usuario obtenerUsuarioPorId(int id) {
		return entityManager.find(Usuario.class, id);

	}

	@Override
	public void actualizarUsuario(Usuario usuarioEditar) {
		entityManager.merge(usuarioEditar);
		
	}

	@Override
	public Usuario obtenerUsuarioPorEmailYpass(String email, String pass) {
		try {
			Usuario usuario =
					(Usuario)entityManager.createQuery("select u from Usuario u where u.email = :email and u.pass = :pass ").setParameter("email", email).setParameter("pass", pass).getSingleResult();
			return usuario;
			
		} catch (Exception e) {
			System.out.println("no se encontró un usuario con el email y pass indicado ");
		}
		return null;
		
	}

	@Override
	public Usuario obtenerUsuarioPorEmail(String email) {
	    try {
	        return entityManager.createQuery(
	            "SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
	            .setParameter("email", email)
	            .getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	    }
	}

}
