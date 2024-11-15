package com.sena.crudempleados.interfaz;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sena.crudempleados.entidades.Cliente;
import com.sena.crudempleados.repositorio.ClienteRepositorio;

@Service
public class ClienteImplementacion implements ClienteInterfaz{
    @Autowired
    private ClienteRepositorio repositorio;

    @Override
    public List<Cliente> listarTodosLosClientes(){
        return repositorio.findAll();
    }
    @Override
    public Cliente guardarCliente(Cliente cliente){
        return repositorio.save(cliente);
    }
    @Override
    public Cliente obtenerClienteporId(long id){
        return repositorio.findById(id).get();
    }
    @Override
    public Cliente actualizarCliente(Cliente cliente){
        return repositorio.save(cliente);
    }
    @Override
    public void eliminarCliente(long id){
        repositorio.deleteById(id);
    }
}