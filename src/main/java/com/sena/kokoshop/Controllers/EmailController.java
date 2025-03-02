package com.sena.kokoshop.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.sena.kokoshop.entidades.Rol;
import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.interfaz.RolInterfaz;
import com.sena.kokoshop.interfaz.UsuarioInterfaz;

@Controller
public class EmailController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UsuarioInterfaz interfaz;

    @Autowired
    private RolInterfaz rolInterfaz;

    @PostMapping("/enviar-correos")
    public String enviarCorreos() {
        List<Usuario> clientes = interfaz.obtenerUsuariosPorRolCliente();
        System.out.println("------------------Clientes: " + clientes.size());

        for (Usuario cliente : clientes) {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setFrom("kokoshop1201@gmail.com");
            mensaje.setTo(cliente.getEmail());
            mensaje.setSubject("¡Enterate de las nuevas promociones!");
            mensaje.setText("¡Queridos clientes! 🌸👗\n" + //
                    "Estamos emocionados de anunciar nuevas promociones en todas nuestras prendas. 🛍️✨ Es el momento perfecto para renovar tu guardarropa y lucir espectacular sin gastar de más.\n"
                    + //
                    "\n" + //
                    "📌 Aprovecha nuestros descuentos únicos y no te quedes sin tu estilo favorito.\n" + //
                    "🏃‍♀️ Promoción válida por tiempo limitado.\n" + //
                    "\n" + //
                    "Te esperamos para que disfrutes de esta increíble oportunidad. ¡No te lo pierdas!\n" + //
                    "\n" + //
                    "KOKOSHOP: Tu estilo, tu tienda.\n" + //
                    "📲 Contáctanos para más información. ❤️");
            mailSender.send(mensaje);
        }

        return "redirect:/usuarios/";
    }
}
