package com.sena.kokoshop.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.interfaz.UsuarioInterfaz;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioInterfaz interfaz;

    @GetMapping("/usuarios")
    public String listarUsuarios(Model modelo) {
        modelo.addAttribute("usuarios", interfaz.listarTodosLosUsuarios());
        return "usuarios/index"; // retorna al archivo usuarios
    }

    @GetMapping("/usuarios/nuevo")
    public String crearUsuarioFormulario(Model modelo) {
        Usuario usuario = new Usuario();
        modelo.addAttribute("usuario", usuario);
        return "usuarios/crear_usuario";
    }

    @PostMapping("/usuarios")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        interfaz.guardarUsuario(usuario);
        return "redirect:/usuarios";

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
            usuarioExistente.setDireccion(usuario.getDireccion());
            usuarioExistente.setCiudad(usuario.getCiudad());
            usuarioExistente.setEstado(usuario.getEstado());
            usuarioExistente.setCorreoElectronico(usuario.getCorreoElectronico());
            usuarioExistente.setTelefono(usuario.getTelefono());
            usuarioExistente.setFechaRegistro(usuario.getFechaRegistro());
            usuarioExistente.setHistorialCompras(usuario.getHistorialCompras());
            interfaz.actualizarUsuario(usuarioExistente);
        }
        return "redirect:/usuarios";

    }

    @GetMapping("/usuarios/{usuarioID}")
    public String eliminarUsuario(@PathVariable Long usuarioID) {
        interfaz.eliminarUsuario(usuarioID);
        return "redirect:/usuarios";
    }

}
