package com.sena.kokoshop.Controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sena.kokoshop.dto.CarritoProductoDTO;
import com.sena.kokoshop.dto.VentaProductoDTO;
import com.sena.kokoshop.entidades.Empleado;
import com.sena.kokoshop.entidades.Producto;
import com.sena.kokoshop.entidades.ProductoCarrito;
import com.sena.kokoshop.entidades.ProductoVenta;
import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.entidades.Venta;
import com.sena.kokoshop.interfaz.UsuarioInterfaz;
import com.sena.kokoshop.repositorio.EmpleadoRepositorio;
import com.sena.kokoshop.repositorio.UsuarioRepositorio;
import com.sena.kokoshop.service.CarritoProductoService;
import com.sena.kokoshop.service.VentaProductoService;

@Controller
public class CarritoController {

    @Autowired
    private CarritoProductoService carritoProductoService;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @Autowired
    private UsuarioInterfaz usuarioInterfaz;

    @Autowired
    private VentaProductoService ventaService;

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

    @PostMapping("/carrito/eliminar/")
    public void eliminarProductoDelCarrito(@RequestParam("idProducto") Long idProducto,
            @RequestParam("email") String email, Model modelo) {
        carritoProductoService.eliminarProductoDelCarrito(idProducto, email);
    }

    @PostMapping("/carrito/interfazCompra/")
    public String mostraInterfazCompra(
            @ModelAttribute("carritoProductoDTO") CarritoProductoDTO carritoProductoDTO, // Recibe el objeto desde el
                                                                                         // formulario
            @RequestParam("email") String email, // Recibe el email como parámetro
            Model modelo) {

        Usuario usuario = usuarioRepositorio.findByEmail(email);

        List<Producto> productos = new ArrayList<>();

        List<ProductoCarrito> productosCarrito = carritoProductoDTO.getProductosCarrito();
        for (ProductoCarrito productoCarrito : productosCarrito) {
            productos.add(productoCarrito.getProducto());
        }

        modelo.addAttribute("usuario", usuario);
        modelo.addAttribute("productos", productos);
        modelo.addAttribute("productosCarrito", productosCarrito);
        modelo.addAttribute("carritoProductoDTO", carritoProductoDTO);

        return "cliente/compra-carrito";
    }

    @PostMapping("/carrito/compra/")
    public String realizarPedido(@ModelAttribute("usuario") Usuario usuario,
            @ModelAttribute CarritoProductoDTO carritoProductoDTO, Model modelo) {

        // First, ensure the user is saved or retrieved from the database
        Usuario usuarioExistente = usuarioInterfaz.obtenerUsuarioporId(usuario.getUsuarioID());

        System.out.println("--------------Usuario: " + usuario.getUsuarioID());
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

        Empleado admin = empleadoRepositorio.findById(1L)
                .orElseThrow(() -> new RuntimeException("Empleado Admin no encontrado"));

        VentaProductoDTO ventaProductoDTO = new VentaProductoDTO();
        Venta venta = new Venta();
        venta.setCliente(usuarioExistente); // Use the persisted user
        venta.setEmpleado(admin);
        venta.setFechaVenta(new Date(System.currentTimeMillis()));

        List<ProductoVenta> productosVenta = new ArrayList<>();
        Float precioTotal = 10000.0f; // Shipping cost

        // First pass: create ProductoVenta and calculate total price
        for (ProductoCarrito productoCarrito : carritoProductoDTO.getProductosCarrito()) {
            ProductoVenta productoVenta = new ProductoVenta();
            productoVenta.setProducto(productoCarrito.getProducto());
            productoVenta.setCantidad(productoCarrito.getCantidad());
            productoVenta.setVenta(venta);

            productosVenta.add(productoVenta);

            precioTotal += productoCarrito.getProducto().getProducPrecio() * productoCarrito.getCantidad();

            System.out.println("--------------Producto: " + productoVenta.getProducto().getProducNom());
            System.out.println("--------------Cantidad: " + productoCarrito.getCantidad());
        }

        venta.setPrecioTotal(precioTotal);
        venta.setTipoVenta("Virtual");
        venta.setEstadoVenta("Pendiente");

        ventaProductoDTO.setVenta(venta);
        ventaProductoDTO.setProductos(productosVenta);

        ventaService.guardarVentaConProductos(ventaProductoDTO);

        carritoProductoService.vaciarCarrito(usuarioExistente.getEmail());

        return "index";
    }
}
