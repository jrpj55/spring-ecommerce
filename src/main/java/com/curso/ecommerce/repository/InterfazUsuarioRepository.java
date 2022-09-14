package com.curso.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curso.ecommerce.model.Usuario;

@Repository
public interface InterfazUsuarioRepository extends JpaRepository<Usuario, Integer>{
	

}
