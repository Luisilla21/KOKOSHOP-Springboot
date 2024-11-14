package com.sena.crudempleados.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sena.crudempleados.interfaz.EmpleadoInterfaz;
import com.sena.crudempleados.entidades.Empleado;

@Controller
public class EmpleadoController {

    @Autowired
    private EmpleadoInterfaz interfaz;
    @GetMapping("/empleados")
    public String listarEmpleados(Model modelo){
        modelo.addAttribute("empleados", interfaz.listarTodosLosEmpleados());
        return "empleados"; //retorna al archivo empleados
    }

    @GetMapping("/empleados/nuevo")
    public String crearEmpleadoFormulario(Model modelo){
        Empleado empleado = new Empleado();
        modelo.addAttribute("empleado", empleado);
        return "crear_empleado";
    }

    @PostMapping("/empleados")
    public String guardarEmpleado(@ModelAttribute("empleado")Empleado empleado){
        interfaz.guardarEmpleado(empleado);
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
        return "editar_empleado";
    }

    @PostMapping("/empleados/{id}")
    public String actualizarEmpleado(@PathVariable Long id, @ModelAttribute("empleado") Empleado empleado, Model modelo){
        Empleado empleadoExistente = interfaz.obtenerEmpleadoPorId(id);
        if (empleadoExistente != null){
        empleadoExistente.setId(id);
        empleadoExistente.setNombre(empleado.getNombre());
        empleadoExistente.setSalario(empleado.getSalario());
        empleadoExistente.setHoraEntrada(empleado.getHoraEntrada());
        empleadoExistente.setHoraSalida(empleado.getHoraSalida());
        empleadoExistente.setHorasTrabajadas(empleado.getHorasTrabajadas());
        interfaz.actualizarEmpleado(empleadoExistente);
        }
        return "redirect:/empleados";

    }

    @GetMapping("/empleados/{id}")
    public String eliminarEmpleado(@PathVariable Long id){
        interfaz.eliminarEmpleado(id);
        return "redirect:/empleados";
    }

}
