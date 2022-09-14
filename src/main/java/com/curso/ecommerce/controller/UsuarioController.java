package com.curso.ecommerce.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		log.info("usuario registro: ", usuario);
		usuario.setTipo("CLIENTE");
		usuarioService.save(usuario);
		
		
		return "redirect:/";
	}

}
