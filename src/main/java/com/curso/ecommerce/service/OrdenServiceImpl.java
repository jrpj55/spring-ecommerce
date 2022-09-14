package com.curso.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.repository.InterfazOrdenRepository;

@Service
public class OrdenServiceImpl implements InterfazOrdenService{
	
	@Autowired
	private InterfazOrdenRepository interfazordenRepository;
	@Override
	public Orden save(Orden orden) {
		
		return interfazordenRepository.save(orden);
	}

}
