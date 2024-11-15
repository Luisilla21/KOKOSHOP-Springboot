package com.sena.crudempleados.interfaz;

import java.util.List;
import com.sena.crudempleados.entidades.Cliente;
public interface ClienteInterfaz {

    public List<Cliente>listarTodosLosClientes();
    public Cliente guardarCliente(Cliente cliente);
    public Cliente obtenerClienteporId(long id);
    public Cliente actualizarCliente(Cliente cliente);
    public void eliminarCliente(long id);
}
