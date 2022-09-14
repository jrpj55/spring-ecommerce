package com.curso.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.repository.InterfazOrdenRepository;

@Service
public class OrdenServiceImpl implements InterfazOrdenService{
	
	@Autowired
	private InterfazOrdenRepository interfazordenRepository;
	@Override
	public Orden save(Orden orden) {
		
		return interfazordenRepository.save(orden);
	}
	@Override
	public List<Orden> findAll() {
		
		return interfazordenRepository.findAll();
	}
	
	//metodo que  genera el secuencial en string del número de la orden
	//porque llega como 000010 y lo pasamos a entero para poderlo trabajar
	public String generarNumeroOrden(){
		int numero=0;
		String numeroConcatenado=""; 
		List<Orden> ordenes=findAll();
		List<Integer> numeros = new ArrayList<Integer>();
		ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero())));
		if (ordenes.isEmpty()) {
			numero=1;
		}else {
			numero = numeros.stream().max(Integer::compare).get();
			numero++;
		}
		if (numero < 10) {
			numeroConcatenado="000000000" + String.valueOf(numero);
		}else if(numero <100) {
				numeroConcatenado="00000000" + String.valueOf(numero);
		}else if(numero <1000) {
			numeroConcatenado="0000000" + String.valueOf(numero);
		}else if(numero <10000) {
			numeroConcatenado="000000" + String.valueOf(numero);
		}
		
		
		
		return numeroConcatenado;
	}
	@Override
	public List<Orden> findByUsuarioo(Usuario usuario) {
		
		return interfazordenRepository.findByUsuarioo(usuario);
	}
}
