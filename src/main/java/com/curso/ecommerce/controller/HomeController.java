package com.curso.ecommerce.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.InterfazDetalleOrdenService;
import com.curso.ecommerce.service.InterfazOrdenService;
import com.curso.ecommerce.service.InterfazUsuarioService;
import com.curso.ecommerce.service.ProductoService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	private final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private InterfazUsuarioService usuarioService;
	
	@Autowired
	private InterfazOrdenService ordenService;
	
	@Autowired
	private InterfazDetalleOrdenService detalleordenService;
	
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
	public String addCarrito(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
		DetalleOrden detalleOrden = new DetalleOrden();
		Producto producto = new Producto();
		double sumaTotal = 0;
		
		Optional<Producto> optionProducto = productoService.get(id);
		log.info("Producto añadido: {}", optionProducto.get());
		log.info("Cantidad: {}", cantidad);
		producto = optionProducto.get();
		
		detalleOrden.setCantidad(cantidad);
		detalleOrden.setPrecio(producto.getPrecio());
		detalleOrden.setNombre(producto.getNombre());
		detalleOrden.setTotal(producto.getPrecio()*cantidad);
		detalleOrden.setObjProducto(producto);//de aca depende la clave foranea
		
		//Validación para que el producto no se agregue mas de una vez
		Integer idProducto=producto.getId();
		boolean ingresado = detalles.stream().anyMatch(p -> p.getObjProducto().getId()==idProducto);
		if (!ingresado) {
			detalles.add(detalleOrden);
		}
			
		
		sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
		orden.setTotal(sumaTotal);
		model.addAttribute("carrito", detalles);
		model.addAttribute("fact", orden);
		
		
		return "usuario/carrito";
	}
	//Quitar producto del carrito de compras
	@GetMapping("delete/cart/{id}")
	public String deleteProductoCart(@PathVariable Integer id, Model model) {
		//Lista nuevos productos
		List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();
		for (DetalleOrden detalleOrden: detalles) {
			if (detalleOrden.getObjProducto().getId()!=id) {
				ordenesNueva.add(detalleOrden);
						
			}
		}
		//Poner la nueva lista con los productos restantes
        detalles=ordenesNueva;
		
        double sumaTotal=0;
		sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
		orden.setTotal(sumaTotal);
		model.addAttribute("carrito", detalles);
		model.addAttribute("fact", orden);
		return "usuario/carrito";
	}
	
	@GetMapping("/getCart")
		public String getCart(Model model){
		model.addAttribute("carrito", detalles);
		model.addAttribute("fact", orden);
		return "/usuario/carrito";
	}
	@GetMapping("/order")
	public String order(Model model) {
		Usuario usuario = usuarioService.findById(1).get();
		
		model.addAttribute("carrito", detalles);
		model.addAttribute("fact", orden);
		model.addAttribute("vistaUsuario", usuario);
		
		return "/usuario/resumenorden";
	}
	//guardar la orden
	@GetMapping("/saveOrder")
	public String saveOrder() {
		Date fechaCreacion = new Date();
		orden.setFechaCreacion(fechaCreacion);
		orden.setNumero(ordenService.generarNumeroOrden());
		
		//usuario
		Usuario usuario = usuarioService.findById(1).get();
		orden.setUsuarioo(usuario);
		ordenService.save(orden);
		//guardar detalles
		for (DetalleOrden dt: detalles) {
			dt.setObjOrden(orden);
			detalleordenService.save(dt);
		}
		//Limpiar lista y orden
		orden = new Orden();
		detalles.clear();
		return "redirect:/";
	}
	@PostMapping("/buscar")
	public String buscarProducto(@RequestParam String var_nombre, Model model){
		log.info("Nombre del producto: {}",var_nombre );
		//Obtiene todos los productos productoService.findAll
		//Pasamos por un stream() es una API en java 8 permite realizar operaciones sobre la colección
		//como por ejemplo buscar, filtar, reordenar, reducir, etc
		//filtramos con filter que es una funcion lambda
		//le pasamos el predicado que es lo queremos hacer 
		//a traves de una ficcion fecha anonima trae el nombre del prodcuto
		//y el metodo contains le pasamos la cadena de caracteres que contengan ese nombre me lo devuelva en una lista
		List<Producto> productos = productoService.findAll().stream().filter(p ->p.getNombre().contains(var_nombre)).toList();
		model.addAttribute("proto", productos); //la lista que recibimos en el home y este de nombre proto
		
		return "usuario/home";
	}
}
