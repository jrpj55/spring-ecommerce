package com.curso.ecommerce.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.InterfazUsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	private final Logger log = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private InterfazUsuarioService usuarioService;//poder acceder a las operaciones CRUD
	
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
			sesion.setAttribute("idUusario", user.get().getId());
			if (user.get().getTipo().equals("ADMIN")) {
				return "redirect:/administrador";
				
			}else {
				return "redirect:/";
			}
			
		}else {
			log.info("Uusario no existe");
		}
		return "redirect:/";
}
}