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

    @Autowired
    private EmpleadoInterfaz interfaz;

    @GetMapping("/empleados")
    public String listarEmpleados(Model modelo) {
        modelo.addAttribute("usuariosEmpleados", usuarioEmpleadoService.listarTodosLosEmpleados());
        return "empleados/index"; //retorna al archivo empleados
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
        Empleado empleado = interfaz.obtenerEmpleadoPorId(id);
        if (empleado == null) {
            // Redirige o muestra un mensaje de error si el empleado no existe
            return "redirect:/empleados";
        }
        modelo.addAttribute("empleado", empleado);
        return "empleados/editar_empleado";
    }

    @PostMapping("/empleados/{id}")
    public String actualizarEmpleado(@PathVariable Long id, @ModelAttribute("empleado") Empleado empleado, Model modelo) {
        Empleado empleadoExistente = interfaz.obtenerEmpleadoPorId(id);
        if (empleadoExistente != null) {
            empleadoExistente.setId(id);
            empleadoExistente.setSalario(empleado.getSalario());
            empleadoExistente.setHoraEntrada(empleado.getHoraEntrada());
            empleadoExistente.setHoraSalida(empleado.getHoraSalida());
            empleadoExistente.setHorasTrabajadas(empleado.getHorasTrabajadas());
            interfaz.actualizarEmpleado(empleadoExistente);
        }
        return "redirect:/empleados";

    }

    @GetMapping("/empleados/{id}")
    public String eliminarEmpleado(@PathVariable Long id) {
        interfaz.eliminarEmpleado(id);
        return "redirect:/empleados";
    }

}
