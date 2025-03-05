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
import com.sena.kokoshop.entidades.ProductoVenta;
import com.sena.kokoshop.service.CarritoProductoService;
import com.sena.kokoshop.service.ProductoInterfaz;
import com.sena.kokoshop.repositorio.UsuarioRepositorio;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CarritoController {

    @Autowired
    private CarritoProductoService carritoProductoService;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ProductoInterfaz productoInterfaz;

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

    @PostMapping("/carrito/agregar")
    public String agregarProductoAlCarrito(
            @RequestParam("idProducto") Long idProducto,
            @RequestParam("cantidad") Integer cantidad,
            @RequestParam("email") String email) {

        carritoProductoService.agregarProductoAlCarrito(idProducto, cantidad, email);

        return "redirect:/catalogo";
    }

    @PostMapping("/carrito/eliminar")
    public String eliminarProductoDelCarrito(@RequestParam("idProducto") Long idProducto,
                                             @RequestParam("email") String email) {
        carritoProductoService.eliminarProductoDelCarrito(idProducto, email);
        return "redirect:/carrito/ver/" + email;
    }

    @PostMapping("/carrito/interfazCompra/")
    public String mostraInterfazCompra(@RequestParam("carritoProductoDTO") CarritoProductoDTO carritoProductoDTO,
            @RequestParam("cantidad") Integer cantidad, @RequestParam("email") String email, Model modelo) {
        Producto producto = productoInterfaz.obtenerProductoPorId(carritoProductoDTO.getCarrito().getIdCarrito());
        Usuario usuario = usuarioRepositorio.findByEmail(email);
        System.out.println("---------------------------------------usuario: " + usuario.getUsuarioID());

        System.out.println("---------------------------------------cantidad: " + cantidad);

        if (producto == null) {
            return "redirect:/productos";
        }

        ProductoVenta productoVenta = new ProductoVenta();
        productoVenta.setProducto(producto);
        productoVenta.setCantidad(cantidad);

        modelo.addAttribute("producto", producto);
        modelo.addAttribute("productoVenta", productoVenta);
        modelo.addAttribute("usuario", usuario);

        return "compra";
    }

    @PostMapping("/carrito/procederCompra")
    public String procederCompra(@RequestParam("email") String email, Model model) {
        CarritoProductoDTO carritoProductoDTO = carritoProductoService.listarCarritoPorUsuario(email);

        // Si productosCarrito es null, inicializarlo como una lista vacía
        List<ProductoCarrito> productosCarrito = carritoProductoDTO.getProductosCarrito();
        if (productosCarrito == null) {
            productosCarrito = new ArrayList<>();
        }

        model.addAttribute("carritoProductoDTO", carritoProductoDTO);
        model.addAttribute("productosCarrito", productosCarrito);
        model.addAttribute("usuario", usuarioRepositorio.findByEmail(email));

        return "compra_carrito";
    }

}
