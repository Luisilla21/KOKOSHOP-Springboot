package com.sena.kokoshop.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sena.kokoshop.entidades.Producto;
import com.sena.kokoshop.interfaz.ProductoInterfaz;

@Controller
public class CatalogoController {

    @Autowired
    private ProductoInterfaz interfaz;

    @GetMapping("/catalogo")
    public String listarProductosCatalogo(Model modelo) {
        modelo.addAttribute("productos", interfaz.listarTodosLosProductos());
        return "catalogo"; // retorna al archivo productos
    }

    @GetMapping("/catalogo/producto/{id}")
    public String mostraInterfazProducto(@PathVariable Long id, Model modelo ){
        Producto producto = interfaz.obtenerProductoPorId(id);
        if (producto == null) {
            // Redirige o muestra un mensaje de error si el producto no existe
            return "redirect:/productos";
        }
        modelo.addAttribute("producto", producto);
        return "vistaProductos";
    }

}
