package com.sena.kokoshop.configuracion;

import java.util.Optional;
import com.sena.kokoshop.entidades.Rol;
import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.interfaz.RolInterfaz;
import com.sena.kokoshop.entidades.Empleado;
import com.sena.kokoshop.interfaz.EmpleadoInterfaz;
import com.sena.kokoshop.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.util.Optionals;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RolInterfaz rolInterfaz;

    @Autowired
    private UsuarioRepositorio usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmpleadoInterfaz empleadoInterfaz;

    @Override
    public void run(String... args) throws Exception {
        // Crear roles si no existen
        crearRolSiNoExiste("ADMIN");
        crearRolSiNoExiste("EMPLEADO");
        crearRolSiNoExiste("CLIENTE");
        crearAdmin();
    }

    private void crearRolSiNoExiste(String nombreRol) {
        if (rolInterfaz.findByNombre(nombreRol) == null) {
            Rol rol = new Rol();
            rol.setNombre(nombreRol);
            rolInterfaz.save(rol);
            System.out.println("Rol creado: " + nombreRol); // Log para verificar
        }
    }

    public void crearAdmin() {
        // Verificar si el rol ADMIN existe, si no, crearlo
        Rol adminRole = rolInterfaz.findByNombre("ADMIN");
        if (adminRole == null) {
            Rol rol = new Rol();
            rol.setNombre("ADMIN");
            rolInterfaz.save(rol);
        }

        // Verificar si hay un usuario administrador
        Usuario adminUser = usuarioRepository.findByEmail("admin@gmail.com");
        if (adminUser == null) {
            Usuario admin = new Usuario();
            admin.setNombre("Admin");
            admin.setApellido("Kokoshop");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("1234567890")); // Contraseña encriptada
            admin.setRol(adminRole);
            usuarioRepository.save(admin);

            Empleado empleado = new Empleado();
            empleado.setUsuario(admin);
            empleadoInterfaz.guardarEmpleado(empleado);

            System.out.println("🛠️ Usuario administrador creado con éxito.");
        } else {
            System.out.println("✅ Ya existe un usuario administrador.");
        }
    }

}
