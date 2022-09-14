package com.curso.ecommerce.service;



import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

import com.curso.ecommerce.model.DetalleOrden;
import com.curso.ecommerce.repository.InterfazDetalleOrdenRepository;

@Service
public class DetalleOrdenServiceImpl implements InterfazDetalleOrdenService{
	
	@Autowired
	private InterfazDetalleOrdenRepository interfazdetalleordenRepository;

	@Override
	public DetalleOrden save(DetalleOrden detalleOrden) {
		
		return interfazdetalleordenRepository.save(detalleOrden);
	}


}
