package com.curso.ecommerce.service;

import java.util.List;

import com.curso.ecommerce.model.Orden;

public interface InterfazOrdenService {
	List<Orden> findAll();
	Orden save(Orden orden);

}
