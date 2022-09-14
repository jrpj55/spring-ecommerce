package com.curso.ecommerce.service;

import java.util.Optional;

import com.curso.ecommerce.model.Usuario;


public interface InterfazUsuarioService {
	//Metodo que permite obtener un usuario de la base de datos
	Optional<Usuario> findById(Integer id);
	Usuario save(Usuario usuario);

	

}
