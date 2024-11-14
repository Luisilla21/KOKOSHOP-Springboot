package com.sena.crudempleados.interfaz;

import java.util.List;
import com.sena.crudempleados.entidades.Empleado;

public interface EmpleadoInterfaz {

    public List<Empleado> listarTodosLosEmpleados();
    public Empleado guardarEmpleado(Empleado empleado);
    public Empleado obtenerEmpleadoPorId(long id);
    public Empleado actualizarEmpleado(Empleado empleado);
    public void eliminarEmpleado(long id);

}
