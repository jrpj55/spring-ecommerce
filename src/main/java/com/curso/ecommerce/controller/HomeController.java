package com.curso.ecommerce.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.service.ProductoService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	private final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ProductoService productoService;
	
	@GetMapping("")
	public String home(Model model) {
		model.addAttribute("proto", productoService.findAll());
		return "usuario/home";
		
	}
	
	@GetMapping("productohome/{id}")
	public String productoHome(@PathVariable Integer id, Model model) {
		log.info("id producto enviado con parámetro {}",id );
		Producto pro= new Producto();
		Optional<Producto> optionalProducto = productoService.get(id);
		pro=optionalProducto.get();
		model.addAttribute("productos_home", pro);
		
		
		return "usuario/productohome";
		
	}
}
