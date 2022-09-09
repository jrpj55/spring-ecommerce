package com.curso.ecommerce.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.ProductoService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	@Autowired
	private ProductoService productoService;
	
	
	@GetMapping("")
	public String show(Model model) {
		model.addAttribute("produ", productoService.findAll());
		return "productos/show";
		
	}
	@GetMapping("/create")
	public String create() {
		return "productos/create";
	}
	
	@PostMapping("/save")
	private String save(Producto pro) {
		LOGGER.info("Este es el objeto del producto {}", pro);
		Usuario u = new Usuario(1,"","","","","","","");
		pro.setUsuariop(u);
		productoService.save(pro);
		return "redirect:/productos";
	}
	
	
	
}
