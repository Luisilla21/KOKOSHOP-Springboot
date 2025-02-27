package com.sena.kokoshop.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.kokoshop.dto.UsuarioEmpleadoDTO;
import com.sena.kokoshop.entidades.Empleado;
import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.repositorio.EmpleadoRepositorio;
import com.sena.kokoshop.repositorio.UsuarioRepositorio;

import jakarta.transaction.Transactional;

@Service
public class UsuarioEmpleadoService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @Transactional
    public void crearUsuarioYEmpleado(Usuario usuario, Empleado empleado) {
        // Primero se guarda el Usuario
        Usuario usuarioGuardado = usuarioRepositorio.save(usuario);

        // Luego se asigna el Usuario al Empleado y se guarda el Empleado
        empleado.setUsuario(usuarioGuardado);

        empleadoRepositorio.save(empleado);
    }

    public List<UsuarioEmpleadoDTO> listarTodosLosEmpleados() {
        List<UsuarioEmpleadoDTO> listaDTO = new ArrayList<>();
        List<Empleado> empleados = empleadoRepositorio.findAll();

        for (Empleado empleado : empleados) {
            Usuario usuario = empleado.getUsuario();
            UsuarioEmpleadoDTO dto = new UsuarioEmpleadoDTO(usuario, empleado);
            listaDTO.add(dto);
        }

        return listaDTO;
    }

    public UsuarioEmpleadoDTO buscUsuarioEmpleadoDTO(long id) {

        Empleado empleado = empleadoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Usuario usuario = empleado.getUsuario();
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado para el empleado");
        }

        return new UsuarioEmpleadoDTO(usuario, empleado);
    }

    public void actualizarUsuarioEmpleado(UsuarioEmpleadoDTO dto) {

        Empleado empleadoExistente = empleadoRepositorio.findById(dto.getIdEmpleado())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Empleado empleado = dto.getEmpleado();

        Usuario usuarioExistente = empleadoExistente.getUsuario();
        Usuario usuario = dto.getUsuario();
        if (usuarioExistente == null) {
            throw new RuntimeException("Usuario no encontrado para el empleado");
        }

        // Actualizar Usuario
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setApellido(usuario.getApellido());
        usuarioExistente.setTipoDocumento(usuario.getTipoDocumento());
        usuarioExistente.setNumeroDocumento(usuario.getNumeroDocumento());
        usuarioExistente.setDireccion(usuario.getDireccion());
        usuarioExistente.setCiudad(usuario.getCiudad());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setPassword(usuario.getPassword());
        usuarioExistente.setTelefono(usuario.getTelefono());
        usuarioExistente.setCompras(usuario.getCompras());
        usuarioRepositorio.save(usuarioExistente);

        // Actualizar Empleado

        empleadoExistente.setSalario(empleado.getSalario());
        empleadoExistente.setHoraEntrada(empleado.getHoraEntrada());
        empleadoExistente.setHoraSalida(empleado.getHoraSalida());
        empleadoExistente.setHorasTrabajadas(empleado.getHorasTrabajadas());
        empleadoExistente.setVentas(empleado.getVentas());

        empleadoRepositorio.save(empleadoExistente);
    }

    public void eliminarUsuarioEmpleado(long idEmp) {
        Empleado empleado = empleadoRepositorio.findById(idEmp)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Usuario usuario = empleado.getUsuario();
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado para el empleado");
        }

        empleadoRepositorio.deleteById(empleado.getId());
        usuarioRepositorio.deleteById(usuario.getUsuarioID());
    }
}
