package com.curso.ecommerce.service;

import java.util.List;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Usuario;

public interface InterfazOrdenService {
	List<Orden> findAll();
	Orden save(Orden orden);
	String generarNumeroOrden();
	List<Orden> findByUsuarioo(Usuario usuario);

}
