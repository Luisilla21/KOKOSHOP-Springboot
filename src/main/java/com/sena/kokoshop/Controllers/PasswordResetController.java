package com.sena.kokoshop.Controllers;

import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.interfaz.UsuarioInterfaz;
import com.sena.kokoshop.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class PasswordResetController {

    @Autowired
    private UsuarioInterfaz usuarioInterfaz;

    @Autowired
    private EmailService emailService;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        Usuario usuario = usuarioInterfaz.findByEmail(email);

        if (usuario == null) {
            model.addAttribute("error", "No se encontró una cuenta con ese correo.");
            return "forgot-password";
        }

        String token = UUID.randomUUID().toString();
        usuario.setResetToken(token);
        usuarioInterfaz.guardarUsuario(usuario);

        String resetLink = "http://localhost:8080/reset-password?token=" + token;
        emailService.sendEmail(email, "Restablecer tu contraseña", 
                "Haz clic en el siguiente enlace para restablecer tu contraseña: " + resetLink);

        model.addAttribute("message", "Se ha enviado un enlace de recuperación a tu correo.");
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        Usuario usuario = usuarioInterfaz.findByResetToken(token).orElse(null);

        if (usuario == null) {
            model.addAttribute("error", "Token inválido o expirado.");
            return "reset-password";
        }

        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
public String processResetPassword(@RequestParam("token") String token, 
                                   @RequestParam("password") String password, 
                                   Model model) {
    Usuario usuario = usuarioInterfaz.findByResetToken(token).orElse(null);

    if (usuario == null) {
        model.addAttribute("error", "Token inválido o expirado.");
        return "reset-password";
    }

    // Cifrar la nueva contraseña antes de guardarla
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    usuario.setPassword(passwordEncoder.encode(password));
    usuario.setResetToken(null);
    usuarioInterfaz.guardarUsuario(usuario);

    model.addAttribute("message", "Contraseña restablecida con éxito. Ahora puedes iniciar sesión.");
    return "login";
}
}
