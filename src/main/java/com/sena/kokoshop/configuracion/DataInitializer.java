package com.sena.kokoshop.configuracion;

import com.sena.kokoshop.entidades.Rol;
import com.sena.kokoshop.interfaz.RolInterfaz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RolInterfaz rolInterfaz;

    @Override
    public void run(String... args) throws Exception {
        // Crear roles si no existen
        crearRolSiNoExiste("ADMIN");
        crearRolSiNoExiste("EMPLEADO");
        crearRolSiNoExiste("CLIENTE");
    }

    private void crearRolSiNoExiste(String nombreRol) {
        if (rolInterfaz.findByNombre(nombreRol) == null) {
            Rol rol = new Rol();
            rol.setNombre(nombreRol);
            rolInterfaz.save(rol);
            System.out.println("Rol creado: " + nombreRol); // Log para verificar
        }
    }
}
