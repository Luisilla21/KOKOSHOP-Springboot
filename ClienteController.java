package com.sena.crudempleados.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sena.crudempleados.interfaz.ClienteInterfaz;
import com.sena.crudempleados.entidades.Cliente;

@Controller
public class ClienteController {
 @Autowired
    private ClienteInterfaz interfaz;
    @GetMapping("/clientes")
    public String listarClientes(Model modelo){
        modelo.addAttribute("clientes", interfaz.listarTodosLosClientes());
        return "clientes/index"; //retorna al archivo Clientes
    }

    @GetMapping("/clientes/nuevo")
    public String crearClienteFormulario(Model modelo){
        Cliente cliente = new Cliente();
        modelo.addAttribute("cliente", cliente);
        return "clientes/crear_cliente";
    }

    @PostMapping("/clientes")
    public String guardarCliente(@ModelAttribute("cliente")Cliente cliente){
        interfaz.guardarCliente(cliente);
        return "redirect:/clientes";


    }

    @GetMapping("/clientes/editar/{clienteID}")
    public String mostrarFormularioDeEditar(@PathVariable Long clienteID, Model modelo) {
        Cliente cliente = interfaz.obtenerClienteporId(clienteID);
        if (cliente == null) {
            // Redirige o muestra un mensaje de error si el cliente no existe
            return "redirect:/clientes";
        }
        modelo.addAttribute("cliente", cliente);
        return "clientes/editar_cliente";
    }

    @PostMapping("/clientes/{clienteID}")
    public String actualizarCliente(@PathVariable Long clienteID, @ModelAttribute("cliente") Cliente cliente, Model modelo){
        Cliente clienteExistente = interfaz.obtenerClienteporId(clienteID);
        if (clienteExistente != null){
            clienteExistente.setClienteID(clienteID); // Aquí puedes ignorarlo si es autogenerado
        clienteExistente.setNombre(cliente.getNombre());
        clienteExistente.setApellido(cliente.getApellido());
        clienteExistente.setDireccion(cliente.getDireccion());
        clienteExistente.setCiudad(cliente.getCiudad());
        clienteExistente.setEstado(cliente.getEstado());
        clienteExistente.setCorreoElectronico(cliente.getCorreoElectronico());
        clienteExistente.setTelefono(cliente.getTelefono());
        clienteExistente.setFechaRegistro(cliente.getFechaRegistro());
        clienteExistente.setHistorialCompras(cliente.getHistorialCompras());
        interfaz.actualizarCliente(clienteExistente);
        }
        return "redirect:/clientes";

    }

    @GetMapping("/clientes/{clienteID}")
    public String eliminarCliente(@PathVariable Long clienteID){
        interfaz.eliminarCliente(clienteID);
        return "redirect:/clientes";
    }

}
