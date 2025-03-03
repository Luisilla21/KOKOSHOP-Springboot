package com.sena.kokoshop.interfaz;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.entidades.Empleado;
import com.sena.kokoshop.repositorio.EmpleadoRepositorio;

@Service
public class EmpleadoImplementacion implements EmpleadoInterfaz {
    @Autowired
    private EmpleadoRepositorio repositorio;

    @Override
    public List<Empleado> listarTodosLosEmpleados() {
        return repositorio.findAll();
    }

    @Override
    public Empleado guardarEmpleado(Empleado empleado) {
        return repositorio.save(empleado);
    }

    @Override
    public Empleado obtenerEmpleadoPorId(long id) {
        return repositorio.findById(id).get();
    }

    @Override
    public Empleado actualizarEmpleado(Empleado empleado) {

        return repositorio.save(empleado);
    }

    @Override
    public void eliminarEmpleado(long id) {
        repositorio.deleteById(id);
    }


}
