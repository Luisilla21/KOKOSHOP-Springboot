package com.sena.kokoshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private UsuarioRepositorio usuarioRepository;

    @Autowired
    private EmpleadoRepositorio empleadoRepository;

    @Transactional
    public void crearUsuarioYEmpleado(Usuario usuario, Empleado empleado) {
        // Primero se guarda el Usuario
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Luego se asigna el Usuario al Empleado y se guarda el Empleado
        empleado.setUsuario(usuarioGuardado);

        empleadoRepository.save(empleado);
    }

    public List<UsuarioEmpleadoDTO> listarTodosLosEmpleados() {
        List<UsuarioEmpleadoDTO> listaDTO = new ArrayList<>();

        List<Empleado> empleados = empleadoRepository.findAll();

        for (Empleado empleado : empleados) {
            Usuario usuario = empleado.getUsuario();

            UsuarioEmpleadoDTO dto = new UsuarioEmpleadoDTO(usuario, empleado);

            listaDTO.add(dto);
        }

        return listaDTO;
    }

    
}
