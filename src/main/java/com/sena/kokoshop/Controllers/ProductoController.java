package com.sena.kokoshop.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.sena.kokoshop.dto.ProductoTallaDTO;
import com.sena.kokoshop.entidades.CantidadTalla;
import com.sena.kokoshop.service.ProductoTallaService;

@Controller
public class ProductoController {

    @Autowired
    private ProductoTallaService productoService;

    @GetMapping("/productos")
    public String listarProductos(Model modelo) {
        modelo.addAttribute("productosTallas", productoService.listarTodosLosProductos());
        return "productos/index"; // retorna al archivo productos
    }

    @GetMapping("/productos/nuevo")
    public String crearProductoFormulario(Model modelo) {
        ProductoTallaDTO productoTallaDTO = new ProductoTallaDTO();
        productoTallaDTO.getTallas().add(new CantidadTalla()); // Inicializamos la lista de tallas
        modelo.addAttribute("productoTallaDTO", productoTallaDTO);
        return "productos/crear_producto";
    }

    @PostMapping("/productos")
    public String guardarProducto(@ModelAttribute ProductoTallaDTO productoTallaDTO) {
        System.out.println("Controlador");
        System.out.println(productoTallaDTO.getProducto().toString());
        productoService.guardarProductoConTallas(productoTallaDTO);
        return "redirect:/productos";
    }

    @GetMapping("/productos/editar/{id}")
    public String mostrarFormularioDeEditar(@PathVariable Long id, Model modelo) {
        ProductoTallaDTO pDto = productoService.buscarProductoTallaDTO(id);
        if (pDto == null) {
            // Redirige o muestra un mensaje de error si el producto no existe
            return "redirect:/productos";
        }
        System.out.println("Controlador mostrar form editar");
        System.out.println(pDto.getProducto().toString());
        modelo.addAttribute("productoTalla", pDto);
        return "productos/editar_producto";
    }

    @PostMapping("/productos/actualizar/{idProducto}")
    public String actualizarProducto(@PathVariable Long idProducto,
            @ModelAttribute("productoTalla") ProductoTallaDTO pDto) {
        System.out.println("Controlador actualizar");
        System.out.println(pDto.getProducto().toString());
        productoService.actualizarProductoTallas(pDto);
        return "redirect:/productos";
    }

    @GetMapping("/productos/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProductoTallas(id);
        return "redirect:/productos";
    }

    @GetMapping("productos/nueva-talla")
    public String nuevaTallaFragment(Model model) {
        model.addAttribute("talla", new CantidadTalla());
        return "fragments :: nueva-talla";
    }

}
