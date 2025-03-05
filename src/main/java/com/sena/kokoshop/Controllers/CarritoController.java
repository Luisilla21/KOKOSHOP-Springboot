package com.sena.kokoshop.Controllers;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.sena.kokoshop.dto.CarritoProductoDTO;
import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.entidades.Producto;
import com.sena.kokoshop.entidades.ProductoCarrito;
import com.sena.kokoshop.service.CarritoProductoService;
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
        CarritoProductoDTO carritoProductoDTO = carritoProductoService.listarCarritoPorUsuario(email);
        List<Producto> productos = new ArrayList<>();
        List<ProductoCarrito> productosCarrito = carritoProductoDTO.getProductosCarrito();

        for (ProductoCarrito productoCarrito : productosCarrito) {
            productos.add(productoCarrito.getProducto());
        }

        model.addAttribute("productos", productos);
        model.addAttribute("productosCarrito", productosCarrito);
        model.addAttribute("carritoProductoDTO", carritoProductoDTO);

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
