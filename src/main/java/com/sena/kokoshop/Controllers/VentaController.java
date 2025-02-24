package com.sena.kokoshop.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sena.kokoshop.dto.VentaProductoDTO;
import com.sena.kokoshop.entidades.Venta;
import com.sena.kokoshop.service.VentaProductoService;

@Controller
public class VentaController {

    @Autowired
    private VentaProductoService ventaService;

    @GetMapping("/ventas")
    public String listarVentas(Model modelo) {
        modelo.addAttribute("ventasProductos", ventaService.listarTodasLasVentas());
        return "ventas/index";
    }

    @GetMapping("/ventas/nueva")
    public String crearVentaFormulario(Model modelo) {
        VentaProductoDTO ventaProductoDTO = new VentaProductoDTO();
        modelo.addAttribute("ventaProductoDTO", ventaProductoDTO);
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
        modelo.addAttribute("venta", vDto.getVenta()); // Asegúrate de pasar el objeto venta al modelo
        return "ventas/editar_venta";
    }

    @PostMapping("/ventas/actualizar/{idVenta}")
    public String actualizarVenta(@PathVariable Long idVenta, @ModelAttribute("venta") Venta venta) {
        ventaService.actualizarVenta(venta);
        return "redirect:/ventas";
    }

    @GetMapping("/ventas/{id}")
    public String eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVentaProductos(id);
        return "redirect:/ventas";
    }
}
