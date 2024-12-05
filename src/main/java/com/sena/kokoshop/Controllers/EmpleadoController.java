package com.sena.kokoshop.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sena.kokoshop.dto.UsuarioEmpleadoDTO;
import com.sena.kokoshop.entidades.Empleado;
import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.interfaz.EmpleadoInterfaz;
import com.sena.kokoshop.service.UsuarioEmpleadoService;

@Controller
public class EmpleadoController {

    @Autowired
    private UsuarioEmpleadoService usuarioEmpleadoService;

    @GetMapping("/empleados")
    public String listarEmpleados(Model modelo) {
        modelo.addAttribute("usuariosEmpleados", usuarioEmpleadoService.listarTodosLosEmpleados());
        return "empleados/index"; // retorna al archivo empleados
    }

    @GetMapping("/empleados/nuevo")
    public String crearEmpleadoFormulario(Model modelo) {

        modelo.addAttribute("usuarioEmpleado", new UsuarioEmpleadoDTO());

        return "empleados/crear_empleado";
    }

    @PostMapping("/crearEmpleado")
    public String guardarEmpleado(@ModelAttribute("usuarioEmpleado") UsuarioEmpleadoDTO dto) {
        Usuario usuario = dto.getUsuario();
        Empleado empleado = dto.getEmpleado();

        usuarioEmpleadoService.crearUsuarioYEmpleado(usuario, empleado); // Lógica de servicio
        return "redirect:/empleados";
    }

    @GetMapping("/empleados/editar/{id}")
    public String mostrarFormularioDeEditar(@PathVariable Long id, Model modelo) {
        UsuarioEmpleadoDTO uDto = usuarioEmpleadoService.buscUsuarioEmpleadoDTO(id);
        if (uDto == null) {
            return "redirect:/empleados";
        }
        modelo.addAttribute("usuarioEmpleado", uDto);
        return "empleados/editar_empleado";
    }

    @PostMapping("/empleados/actualizar")
    public String actualizarEmpleado(@PathVariable Long id,
            @ModelAttribute("usuarioEmpleado") UsuarioEmpleadoDTO uDto) {
            
        usuarioEmpleadoService.actualizarUsuarioEmpleado(uDto);
        return "redirect:/empleados";

    }

    @GetMapping("/empleados/{id}")
    public String eliminarEmpleado(@PathVariable Long id) {
        usuarioEmpleadoService.eliminarUsuarioEmpleado(id);
        return "redirect:/empleados";
    }

}
