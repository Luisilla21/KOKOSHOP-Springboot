package com.sena.kokoshop.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sena.kokoshop.entidades.Producto;
import com.sena.kokoshop.entidades.Rol;
import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.interfaz.ProductoInterfaz;
import com.sena.kokoshop.interfaz.RolInterfaz;
import com.sena.kokoshop.interfaz.UsuarioInterfaz;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioInterfaz interfaz;

    @Autowired
    private RolInterfaz rolInterfaz;

    @Autowired
    private ProductoInterfaz productoInterfaz;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/usuarios/")
    public String listarUsuarios(Model modelo) {
        modelo.addAttribute("usuarios", interfaz.listarTodosLosUsuarios());
        return "/usuarios/index"; // retorna al archivo usuarios
    }

    @GetMapping("/usuarios/nuevo")
    public String crearUsuarioFormulario(Model modelo) {
        Usuario usuario = new Usuario();
        modelo.addAttribute("usuario", usuario);
        return "usuarios/crear_usuario";
    }

    @PostMapping("/usuarios/")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        interfaz.guardarUsuario(usuario);
        return "redirect:/usuarios/";

    }

    @GetMapping("/usuarios/editar/{usuarioID}")
    public String mostrarFormularioDeEditar(@PathVariable Long usuarioID, Model modelo) {
        Usuario usuario = interfaz.obtenerUsuarioporId(usuarioID);
        if (usuario == null) {
            // Redirige o muestra un mensaje de error si el cliente no existe
            return "redirect:/usuarios";
        }
        modelo.addAttribute("usuario", usuario);
        return "usuarios/editar_usuario";
    }

    @PostMapping("/usuarios/{usuarioID}")
    public String actualizarUsuario(@PathVariable Long usuarioID, @ModelAttribute("usuario") Usuario usuario,
            Model modelo) {
        Usuario usuarioExistente = interfaz.obtenerUsuarioporId(usuarioID);
        if (usuarioExistente != null) {
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setApellido(usuario.getApellido());
            usuarioExistente.setTipoDocumento(usuario.getTipoDocumento());
            usuarioExistente.setNumeroDocumento(usuario.getNumeroDocumento());
            usuarioExistente.setDireccion(usuario.getDireccion());
            usuarioExistente.setCiudad(usuario.getCiudad());
            usuarioExistente.setEmail(usuario.getEmail());
            usuarioExistente.setPassword(usuario.getPassword());
            usuarioExistente.setTelefono(usuario.getTelefono());
            usuarioExistente.setCompras(usuario.getCompras());
            interfaz.actualizarUsuario(usuarioExistente);
        }
        return "redirect:/usuarios/";

    }

    @GetMapping("/usuarios/{usuarioID}")
    public String eliminarUsuario(@PathVariable Long usuarioID) {
        interfaz.eliminarUsuario(usuarioID);
        return "redirect:/usuarios/";
    }

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
        model.addAttribute("usuarios", new Usuario());
        return "registro";
    }

    // Procesar registro
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Asignar un rol por defecto (por ejemplo, CLIENTE)
        Rol clienteRol = rolInterfaz.findByNombre("CLIENTE");
        System.out.println("--------------------Rol: " + clienteRol.getNombre());
        usuario.setRol(clienteRol);

        System.out.println("--------------------Usuario: " + usuario.getRol().getNombre());

        interfaz.guardarUsuario(usuario);
        return "redirect:/login?registroExitoso";// Redirige al login con un mensaje
    }

    @GetMapping({ "/", "/index" })
    public String mostrarPaginaDeInicio(@AuthenticationPrincipal User user, Model model) {
        List<Producto> productos = productoInterfaz.listarTodosLosProductos();

        List<Producto> productosLimitados = productos.stream().limit(4).toList();

        model.addAttribute("productos", productosLimitados);
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
