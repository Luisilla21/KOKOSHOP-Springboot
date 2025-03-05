package com.sena.kokoshop.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sena.kokoshop.dto.VentaProductoDTO;
import com.sena.kokoshop.entidades.ProductoVenta;
import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.entidades.Venta;
import com.sena.kokoshop.entidades.Producto;
import com.sena.kokoshop.interfaz.EmpleadoInterfaz;
import com.sena.kokoshop.interfaz.ProductoInterfaz;
import com.sena.kokoshop.interfaz.UsuarioInterfaz;
import com.sena.kokoshop.repositorio.UsuarioRepositorio;
import com.sena.kokoshop.service.UsuarioEmpleadoService;
import com.sena.kokoshop.service.VentaProductoService;

@Controller
public class VentaController {

    @Autowired
    private VentaProductoService ventaService;

    @Autowired
    private UsuarioInterfaz usuarioInterfaz;

    @Autowired
    private EmpleadoInterfaz empleadoInterfaz;

    @Autowired
    private ProductoInterfaz productoInterfaz;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @GetMapping("/ventas")
    public String listarVentas(Model modelo) {
        modelo.addAttribute("ventasProductos", ventaService.listarTodasLasVentas());
        return "ventas/index";
    }

    @GetMapping("/ventas/nueva")
    public String crearVentaFormulario(Model modelo) {
        VentaProductoDTO ventaProductoDTO = new VentaProductoDTO();
        ventaProductoDTO.getProductosVenta().add(new ProductoVenta());

        modelo.addAttribute("ventaProductoDTO", ventaProductoDTO);
        modelo.addAttribute("clientes", usuarioInterfaz.listarTodosLosUsuarios());
        modelo.addAttribute("empleados", empleadoInterfaz.listarTodosLosEmpleados());
        modelo.addAttribute("productos", productoInterfaz.listarTodosLosProductos());
        return "ventas/crear_venta";
    }

    @PostMapping("/ventas")
    public String guardarVenta(@ModelAttribute("ventaProductoDTO") VentaProductoDTO ventaProductoDTO) {
        ventaService.guardarVentaConProductos(ventaProductoDTO);
        return "redirect:/ventas";
    }

    @GetMapping("/ventas/editar/{id}")
public String mostrarFormularioDeEditar(@PathVariable Long id, Model modelo) {
    VentaProductoDTO vDto = ventaService.buscarVentaProductoDTO(id);
    if (vDto == null) {
        // Redirige o muestra un mensaje de error si la venta no existe
        return "redirect:/ventas";
    }
    modelo.addAttribute("ventaProductoDTO", vDto);
    modelo.addAttribute("clientes", usuarioInterfaz.listarTodosLosUsuarios());
    modelo.addAttribute("empleados", empleadoInterfaz.listarTodosLosEmpleados());
    modelo.addAttribute("productos", productoInterfaz.listarTodosLosProductos());
    return "ventas/editar_venta";
}

@PostMapping("/ventas/actualizar/{idVenta}")
public String actualizarVenta(@PathVariable Long idVenta,
        @ModelAttribute("ventaProductoDTO") VentaProductoDTO vDTO) {
    // Ensure the venta ID is set correctly
    vDTO.getVenta().setIdVenta(idVenta);
    ventaService.actualizarVenta(vDTO);
    return "redirect:/ventas";
}

    @GetMapping("/ventas/{id}")
    public String eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVentaProductos(id);
        return "redirect:/ventas";
    }

    @GetMapping("ventas/nuevo-producto")
    public String nuevoProductoFragment(Model model) {
        model.addAttribute("productoVenta", new ProductoVenta());
        return "fragments :: nuevo-producto";
    }

    @GetMapping("/cliente/mis-compras/{email}")
    public String misCompras(@PathVariable String email, Model model) {
        Usuario usuario = usuarioRepositorio.findByEmail(email);

        if (usuario == null) {
            return "redirect:/index";
        }

        model.addAttribute("ventasProductos", ventaService.listarVentasCliente(usuario.getUsuarioID()));
        return "mis-compras";
    }

    @GetMapping("/cliente/mis-compras/detalles/{id}")
    public String detallesCompra(@PathVariable Long id, Model model) {
        VentaProductoDTO ventaProductoDTO = ventaService.buscarVentaProductoDTO(id);
        List<Producto> productos = new ArrayList<>();
        List<ProductoVenta> productosVenta = ventaProductoDTO.getProductosVenta();

        for (ProductoVenta productoVenta : ventaProductoDTO.getProductosVenta()) {
            productos.add(productoVenta.getProducto());
        }
        model.addAttribute("productos", productos);
        model.addAttribute("productosVenta", productosVenta);
        model.addAttribute("ventaProductoDTO", ventaProductoDTO);
        return "detalles-compra";
    }

    @PostMapping("/catalogo/compra/venta")
    public String guardarVentaDesdeCarrito(@ModelAttribute("ventaProductoDTO") VentaProductoDTO ventaProductoDTO, @RequestParam("email") String email) {
        ventaService.guardarVentaDesdeCarrito(ventaProductoDTO, email);
        return "redirect:/ventas";
    }

}

