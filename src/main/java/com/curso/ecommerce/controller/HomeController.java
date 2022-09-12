package com.curso.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.curso.ecommerce.model.DetalleOrden;
import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.service.ProductoService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	private final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ProductoService productoService;
	
	//para almacenas los detalles de la orden
	List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();
	//para almacenar datos de la Orden
	Orden orden = new Orden();
	
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
	@PostMapping("/cart") 
	public String addCarrito(@RequestParam Integer id, @RequestParam Integer cantidad) {
		DetalleOrden detalleOrden = new DetalleOrden();
		Producto producto = new Producto();
		double sumaTotal = 0;
		Optional<Producto> optionProducto = productoService.get(id);
		
		log.info("Producto añadido: {}", optionProducto.get());
		log.info("Cantidad: {}", cantidad);
		return "usuario/carrito";
	}
}
