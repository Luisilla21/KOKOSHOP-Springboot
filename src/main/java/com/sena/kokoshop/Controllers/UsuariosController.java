package com.sena.kokoshop.Controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sena.kokoshop.entidades.Usuarios;
import com.sena.kokoshop.service.UsuariosService;

@Controller
public class UsuariosController {

    private final UsuariosService usuariosService;

    public UsuariosController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    // Página de inicio de sesión
    @GetMapping("/login")
    public String mostrarFormularioDeLogin(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "registroExitoso", required = false) String registroExitoso,
            Model model) {
        if (error != null) {
            model.addAttribute("error", "Credenciales inválidas. Intenta de nuevo.");
        }
        if (logout != null) {
            model.addAttribute("logout", "Has cerrado sesión exitosamente.");
            return "index";
        }
        if (registroExitoso != null) {
            model.addAttribute("registroExitoso", "¡Registro exitoso! Ahora puedes iniciar sesión.");
        }
        return "login";
    }

    // Página de registro
    @GetMapping("/registro")
    public String mostrarFormularioDeRegistro(Model model) {
        model.addAttribute("usuarios", new Usuarios());
        return "registro";
    }

    // Procesar registro
    @PostMapping("/registro")
    public String registrarUsuario(Usuarios usuarios) {
        usuariosService.guardar(usuarios);
        return "redirect:/login?registroExitoso"; // Redirige al login con un mensaje
    }

    @GetMapping("/index")
    public String mostrarPaginaDeInicio(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("username", user.getUsername());
            model.addAttribute("isAuthenticated", true);
        } else {
            model.addAttribute("isAuthenticated", false);
        }
        return "index"; // Asegúrate de que la vista "index.html" exista
    }

     @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros";
    }

    @GetMapping("/asesoria")
    public String asesoria() {
        return "asesoria";
    }
}
