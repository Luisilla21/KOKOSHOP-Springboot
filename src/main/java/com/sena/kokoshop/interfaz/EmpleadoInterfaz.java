package com.sena.kokoshop.interfaz;

import java.util.List;

import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.entidades.Empleado;

public interface EmpleadoInterfaz {

    public List<Empleado> listarTodosLosEmpleados();
    public Empleado guardarEmpleado(Empleado empleado);
    public Empleado obtenerEmpleadoPorId(long id);
    public Empleado actualizarEmpleado(Empleado empleado);
    public void eliminarEmpleado(long id);

}
 