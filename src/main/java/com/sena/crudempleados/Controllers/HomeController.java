package com.sena.crudempleados.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";  // El nombre de tu archivo HTML en `src/main/resources/templates`
    }
}
