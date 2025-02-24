package com.sena.kokoshop.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sena.kokoshop.interfaz.ProductoInterfaz;


@Controller
public class CatalogoController {

    @Autowired
    private ProductoInterfaz interfaz;

    @GetMapping("/catalogo")
    public String listarProductosCatalogo(Model modelo){
        modelo.addAttribute("productos", interfaz.listarTodosLosProductos());
        return "catalogo"; // retorna al archivo productos
        
    }
           
    
}

