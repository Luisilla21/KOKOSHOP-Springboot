package com.sena.kokoshop.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmailController {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/enviar-correos")
    public String enviarCorreos() {
        String[] destinatarios = {
            "luisacamacho21100@gmail.com",
            "Killiam1119@gmail.com",
            "stivenbohorquezmolano@gmail.com",
            "lunakevin200627@gmail.com"
        };

        for (String destinatario : destinatarios) {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setFrom("kokoshop1201@gmail.com");
            mensaje.setTo(destinatario);
            mensaje.setSubject("¡Enterate de las nuevas promociones!");
            mensaje.setText("¡Queridos clientes! 🌸👗\n" + //
                                "Estamos emocionados de anunciar nuevas promociones en todas nuestras prendas. 🛍️✨ Es el momento perfecto para renovar tu guardarropa y lucir espectacular sin gastar de más.\n" + //
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

        return "redirect:/index?correoExitoso";
    }
}
