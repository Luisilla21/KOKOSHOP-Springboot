package com.sena.kokoshop.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.sena.kokoshop.entidades.Producto;
import com.sena.kokoshop.interfaz.ProductoInterfaz;

@Controller
public class HomeController {

    @Autowired
    private ProductoInterfaz productoInterfaz;

    @GetMapping({"/"})
    public String home(Model modelo) {
                List<Producto> productos = productoInterfaz.listarTodosLosProductos();

        List<Producto> productosLimitados = productos.stream().limit(4).toList();

        modelo.addAttribute("productos", productosLimitados);
        return "index";
    }
}