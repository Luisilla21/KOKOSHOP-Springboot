package com.sena.kokoshop.Controllers;

import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.entidades.Producto;
import com.sena.kokoshop.entidades.Venta;
import com.sena.kokoshop.entidades.Empleado;
import com.sena.kokoshop.repositorio.UsuarioRepositorio;
import com.sena.kokoshop.repositorio.ProductoRepositorio;
import com.sena.kokoshop.repositorio.VentaRepositorio;
import com.sena.kokoshop.repositorio.EmpleadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Autowired
    private VentaRepositorio ventaRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @GetMapping("/")
public String dashboardPage(Model model) {
    // Datos para las tarjetas resumen
    long totalVentas = ventaRepositorio.count();
    model.addAttribute("totalVentas", totalVentas);

    double totalIngresos = ventaRepositorio.findAll().stream()
            .mapToDouble(Venta::getPrecioTotal)
            .sum();
    model.addAttribute("totalIngresos", Math.round(totalIngresos * 100.0) / 100.0);

    long totalProductos = productoRepositorio.count();
    model.addAttribute("totalProductos", totalProductos);

    long totalEmpleados = empleadoRepositorio.count();
    model.addAttribute("totalEmpleados", totalEmpleados);

    // Obtener ventas mensuales
    List<Double> ventasMensuales = obtenerVentasMensuales();
    model.addAttribute("ventasMensuales", ventasMensuales);

    // Obtener la distribución de productos por categoría
    List<Producto> productos = productoRepositorio.findAll();
    Map<String, Long> productosCategoria = productos.stream()
            .collect(Collectors.groupingBy(Producto::getTipoPrenda, Collectors.counting()));

    List<String> categoriasPrendas = new ArrayList<>(productosCategoria.keySet());
    List<Long> productosPorCategoria = categoriasPrendas.stream()
            .map(productosCategoria::get)
            .collect(Collectors.toList());

    model.addAttribute("categoriasPrendas", categoriasPrendas);
    model.addAttribute("productosPorCategoria", productosPorCategoria);

    // Ventas recientes
    List<Venta> ventasRecientes = ventaRepositorio.findAll().stream()
            .sorted(Comparator.comparing(Venta::getFechaVenta).reversed())
            .limit(5)
            .collect(Collectors.toList());
    model.addAttribute("ventasRecientes", ventasRecientes);

    // Productos con bajo stock
    List<Producto> productosBajoStock = productos.stream()
            .filter(p -> p.getCantidad() <= 10)
            .sorted(Comparator.comparing(Producto::getCantidad))
            .limit(5)
            .collect(Collectors.toList());
    model.addAttribute("productosBajoStock", productosBajoStock);

    // Empleados más productivos
    Map<Long, Long> ventasPorEmpleadoMap = ventaRepositorio.findAll().stream()
            .collect(Collectors.groupingBy(
                    venta -> venta.getEmpleado().getId(),
                    Collectors.counting()
            ));

    List<Map.Entry<Long, Long>> topEmpleados = ventasPorEmpleadoMap.entrySet().stream()
            .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
            .limit(5)
            .collect(Collectors.toList());

    List<String> nombresEmpleados = new ArrayList<>();
    List<Long> ventasPorEmpleado = new ArrayList<>();

    for (Map.Entry<Long, Long> entry : topEmpleados) {
        Long empleadoId = entry.getKey();
        Empleado empleado = empleadoRepositorio.findById(empleadoId).orElse(null);
        if (empleado != null && empleado.getUsuario() != null) {
            nombresEmpleados.add(empleado.getUsuario().getNombre() + " " + empleado.getUsuario().getApellido());
            ventasPorEmpleado.add(entry.getValue());
        }
    }

    model.addAttribute("nombresEmpleados", nombresEmpleados);
    model.addAttribute("ventasPorEmpleado", ventasPorEmpleado);

    return "dashboard";
}
    

private List<Double> obtenerVentasMensuales() {
    List<Double> ventasMensuales = new ArrayList<>(Collections.nCopies(12, 0.0));
    
    List<Venta> ventas = ventaRepositorio.findAll();
    int currentYear = LocalDate.now().getYear();
    
    for (Venta venta : ventas) {
        Date fechaVenta = venta.getFechaVenta();
        if (fechaVenta != null) {
            LocalDate localDate = fechaVenta.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            
            // Solo considerar ventas del año actual
            if (localDate.getYear() == currentYear) {
                int monthIndex = localDate.getMonthValue() - 1; // Ajuste porque los meses en Java van de 0-11
                ventasMensuales.set(monthIndex, ventasMensuales.get(monthIndex) + venta.getPrecioTotal());
            }
        }
    }
    
    return ventasMensuales;
}
}