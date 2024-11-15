package com.sena.crudempleados.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sena.crudempleados.interfaz.ProductoInterfaz;
import com.sena.crudempleados.entidades.Producto;

@Controller
public class ProductoController {

    @Autowired
    private ProductoInterfaz interfaz;

    @GetMapping("/productos")
    public String listarProductos(Model modelo){
        modelo.addAttribute("productos", interfaz.listarTodosLosProductos());
        return "productos/index"; //retorna al archivo productos
    }

    @GetMapping("/productos/nuevo")
    public String crearProductoFormulario(Model modelo){
        Producto producto = new Producto();
        modelo.addAttribute("producto", producto);
        return "productos/crear_producto";
    }

    @PostMapping("/productos")
    public String guardarProducto(@ModelAttribute("producto") Producto producto){
        interfaz.guardarProducto(producto);
        return "redirect:/productos";
    }

    @GetMapping("/productos/editar/{id}")
    public String mostrarFormularioDeEditar(@PathVariable Long id, Model modelo) {
        Producto producto = interfaz.obtenerProductoPorId(id);
        if (producto == null) {
            // Redirige o muestra un mensaje de error si el producto no existe
            return "redirect:/productos";
        }
        modelo.addAttribute("producto", producto);
        return "productos/editar_producto";
    }

    @PostMapping("/productos/{id}")
    public String actualizarProducto(@PathVariable Long id, @ModelAttribute("producto") Producto producto, Model modelo){
        Producto productoExistente = interfaz.obtenerProductoPorId(id);
        if (productoExistente != null){
            productoExistente.setIdProducto(id);
            productoExistente.setProducNom(producto.getProducNom());
            productoExistente.setProducPrecio(producto.getProducPrecio());
            productoExistente.setCantidad(producto.getCantidad());
            productoExistente.setTipoPrenda(producto.getTipoPrenda());
            interfaz.actualizarProducto(productoExistente);
        }
        return "redirect:/productos";
    }

    @GetMapping("/productos/{id}")
    public String eliminarProducto(@PathVariable Long id){
        interfaz.eliminarProducto(id);
        return "redirect:/productos";
    }
}

