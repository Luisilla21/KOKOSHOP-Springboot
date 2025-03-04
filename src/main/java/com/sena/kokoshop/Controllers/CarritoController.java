package com.sena.kokoshop.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.repositorio.CarritoRepositorio;
import com.sena.kokoshop.repositorio.UsuarioRepositorio;
import com.sena.kokoshop.service.CarritoProductoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CarritoController {

    @Autowired
    private CarritoProductoService carritoProductoService;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @GetMapping("/carrito/ver/{email}")
    public String verCarrito(@PathVariable String email, Model model) {
        model.addAttribute("carrito", carritoProductoService.listarCarritoPorUsuario(email));
        return "cliente/carrito";
    }

    @PostMapping("/carrito/agregar/")
    public String agregarProductoAlCarrito(@RequestParam("idProducto") Long idProducto,
            @RequestParam("cantidad") Integer cantidad, @RequestParam("email") String email, Model modelo) {
        carritoProductoService.agregarProductoAlCarrito(idProducto, cantidad, email);
        return "redirect:/catalogo";
    }

    @PostMapping("/carrito/eliminar/")
    public void eliminarProductoDelCarrito(@RequestParam("idProducto") Long idProducto,
            @RequestParam("email") String email, Model modelo) {
        carritoProductoService.eliminarProductoDelCarrito(idProducto, email);
    }

}
