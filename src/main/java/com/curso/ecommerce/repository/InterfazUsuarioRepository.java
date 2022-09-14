package com.curso.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curso.ecommerce.model.Usuario;

@Repository
public interface InterfazUsuarioRepository extends JpaRepository<Usuario, Integer>{
	
	//la funcionalidad del Optional en java 8 tipo de variable que permite almacenar 2 valores
	//primero:Objeto que necesitamos utilizar
	//segundo: valor null o empaty
	Optional<Usuario> findByEmail(String email);
	

}
