package com.sena.kokoshop.Controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sena.kokoshop.dto.VentaProductoDTO;
import com.sena.kokoshop.entidades.Empleado;
import com.sena.kokoshop.entidades.Producto;
import com.sena.kokoshop.entidades.ProductoVenta;
import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.entidades.Venta;
import com.sena.kokoshop.interfaz.ProductoInterfaz;
import com.sena.kokoshop.interfaz.UsuarioInterfaz;
import com.sena.kokoshop.repositorio.EmpleadoRepositorio;
import com.sena.kokoshop.repositorio.UsuarioRepositorio;
import com.sena.kokoshop.service.VentaProductoService;

@Controller
public class CatalogoController {

    @Autowired
    private ProductoInterfaz productoInterfaz;

    @Autowired
    private UsuarioInterfaz usuarioInterfaz;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @Autowired
    private VentaProductoService ventaService;

    @GetMapping("/catalogo")
    public String listarProductosCatalogo(Model modelo) {
        modelo.addAttribute("productos", productoInterfaz.listarTodosLosProductos());
        return "catalogo"; // retorna al archivo productos
    }

    @GetMapping("/catalogo/producto/{id}")
    public String mostraInterfazProducto(@PathVariable Long id, Model modelo) {

        Producto producto = productoInterfaz.obtenerProductoPorId(id);
        Integer cantidad = 1;
        Usuario usuario = new Usuario();
        modelo.addAttribute("usuario", usuario);
        if (producto == null) {
            // Redirige o muestra un mensaje de error si el producto no existe
            return "redirect:/productos";
        }

        VentaProductoDTO ventaProductoDTO = new VentaProductoDTO();
        ventaProductoDTO.getProductosVenta().add(new ProductoVenta());
        ProductoVenta productoVenta = ventaProductoDTO.getProductosVenta().get(0);

        productoVenta.setProducto(producto);
        productoVenta.setCantidad(cantidad);

        modelo.addAttribute("productoVenta", productoVenta);
        modelo.addAttribute("producto", producto);
        modelo.addAttribute("cantidad", cantidad);
        return "vistaProductos";
    }

    @PostMapping("/catalogo/interfazCompra/")
    public String mostraInterfazCompra(@RequestParam("idProducto") Long idProducto,
            @RequestParam("cantidad") Integer cantidad, @RequestParam("email") String email, Model modelo) {
        Producto producto = productoInterfaz.obtenerProductoPorId(idProducto);
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

    @PostMapping("/catalogo/compra/")
    public String realizarPedido(@ModelAttribute("usuario") Usuario usuario,
            @ModelAttribute("productoVenta") ProductoVenta productoVenta, @ModelAttribute("idProducto") Long idProducto,
            Model modelo) {
        Empleado admin = empleadoRepositorio.findById(1L)
                .orElseThrow(() -> new RuntimeException("Empleado Admin no encontrado"));
        Usuario usuarioExistente = usuarioRepositorio.findById(usuario.getUsuarioID())
                .orElseThrow(() -> new RuntimeException("Usuario Admin no encontrado"));

        if (usuarioExistente != null) {
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setApellido(usuario.getApellido());
            usuarioExistente.setTipoDocumento(usuario.getTipoDocumento());
            usuarioExistente.setNumeroDocumento(usuario.getNumeroDocumento());
            usuarioExistente.setDireccion(usuario.getDireccion());
            usuarioExistente.setCiudad(usuario.getCiudad());
            usuarioExistente.setTelefono(usuario.getTelefono());
            usuarioExistente.setCompras(usuario.getCompras());
            usuarioInterfaz.actualizarUsuario(usuarioExistente);
        }

        VentaProductoDTO ventaProductoDTO = new VentaProductoDTO();
        Venta venta = new Venta();
        ventaProductoDTO.setVenta(venta);

        productoVenta.setProducto(productoInterfaz.obtenerProductoPorId(idProducto));
        productoVenta.setCantidad(productoVenta.getCantidad());

        ventaProductoDTO.getVenta().setCliente(usuarioExistente);
        ventaProductoDTO.getVenta().setEmpleado(admin);
        ventaProductoDTO.getVenta().setFechaVenta(new Date(System.currentTimeMillis()));
        ventaProductoDTO.getVenta()
                .setPrecioTotal(productoVenta.getProducto().getProducPrecio() * productoVenta.getCantidad() + 10000);
        ventaProductoDTO.getVenta().setTipoVenta("Virtual");
        ventaProductoDTO.getVenta().setEstadoVenta("Pendiente");
        ventaProductoDTO.getProductosVenta().add(productoVenta);
        ventaService.guardarVentaConProductos(ventaProductoDTO);

        return "redirect:/catalogo";

    }

}
