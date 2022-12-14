package com.curso.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.InterfazOrdenService;
import com.curso.ecommerce.service.InterfazUsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	private final Logger log = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private InterfazUsuarioService usuarioService;//poder acceder a las operaciones CRUD
	@Autowired
	private InterfazOrdenService ordenService;
	
	//usuario/registro
	@GetMapping("/registro")
	public String crearUsuario() {
		
		return "usuario/registro";
	}
	@PostMapping("/saveUsuario")
	public String saveUsuario(Usuario usuario) {
		log.info("usuario registro: {}", usuario);
		usuario.setTipo("CLIENTE");
		usuarioService.save(usuario);
		return "redirect:/";
	}
	@GetMapping("/login")
	public String login() {
		return "usuario/login";
	}
	@PostMapping("/acceder")
	public String acceder(Usuario usuario, HttpSession sesion) {
		log.info("Accesos: {}", usuario);
		Optional<Usuario> user = usuarioService.findByEmail(usuario.getEmail());
		//log.info("Usuario db: {}", user.get());
		if (user.isPresent()) {
			sesion.setAttribute("idUsuario", user.get().getId());
			if (user.get().getTipo().equals("ADMIN")) {
				return "redirect:/administrador";
				
			}else {
				return "redirect:/";
			}
			
		}else {
			log.info("Usuario no existe");
		}
		return "redirect:/";
	}
	@GetMapping("/compras")
	public String obtenerCompras(Model model, HttpSession sesion) {
		model.addAttribute("var_sesion", sesion.getAttribute("idUsuario"));
		Usuario usuario = usuarioService.findById(Integer.parseInt(sesion.getAttribute("idUsuario").toString())).get();
		List<Orden> ordenes = ordenService.findByUsuarioo(usuario);
		model.addAttribute("var_ordenes", ordenes);
		return "usuario/compras";
	}
	@GetMapping("/detalleCompra/{id}")
	public String detalleCompra(@PathVariable Integer id, HttpSession sesion, Model model) {
		
		//sesion
		model.addAttribute("var_sesion", sesion.getAttribute("idUsuario"));
		log.info("id de la orden: {}", id);
		Optional<Orden> orden=ordenService.findById(id);
		model.addAttribute("var_detalle", orden.get().getDetalle());
		return "usuario/detallecompra";
	}
	@GetMapping("/cerrar")
	public String CerrarSesion(HttpSession sesion) {
		sesion.removeAttribute("idUsuario");
		return "redirect:/";
	}
}