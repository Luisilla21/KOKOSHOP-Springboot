package com.sena.kokoshop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.kokoshop.dto.VentaProductoDTO;
import com.sena.kokoshop.entidades.Producto;
import com.sena.kokoshop.entidades.ProductoVenta;
import com.sena.kokoshop.entidades.Venta;
import com.sena.kokoshop.repositorio.ProductoRepositorio;
import com.sena.kokoshop.repositorio.ProductoVentaRepositorio;
import com.sena.kokoshop.repositorio.VentaRepositorio;

import jakarta.transaction.Transactional;

@Service
public class VentaProductoService {

    @Autowired
    private VentaRepositorio ventaRepositorio;

    @Autowired
    private ProductoVentaRepositorio productoVentaRepositorio;

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Transactional
    public void guardarVentaConProductos(VentaProductoDTO ventaProductoDTO) {
        Venta venta = ventaProductoDTO.getVenta();
        Float precioTotal = 0.0f;

        // Calcular el precio total de la venta
        if (venta.getPrecioTotal() == null) {
            for (ProductoVenta productoVenta : ventaProductoDTO.getProductosVenta()) {
                precioTotal += productoVenta.getProducto().getProducPrecio() * productoVenta.getCantidad();
            }
            venta.setPrecioTotal(precioTotal);

        }

        // Guardar la venta
        Venta ventaGuardada = ventaRepositorio.save(venta);

        // Guardar los productos asociados a la venta
        for (ProductoVenta productoVenta : ventaProductoDTO.getProductosVenta()) {
            productoVenta.setVenta(ventaGuardada);
            productoVentaRepositorio.save(productoVenta);
        }

        for (ProductoVenta productoVenta : ventaProductoDTO.getProductosVenta()) {
            Producto producto = productoVenta.getProducto();
            Integer cantidad = productoVenta.getCantidad();
            Integer cantidadActual = producto.getCantidad();
            producto.setCantidad(cantidadActual - cantidad);
            productoRepositorio.save(producto);
        }
    }

    public List<VentaProductoDTO> listarTodasLasVentas() {
        List<VentaProductoDTO> listaDTO = new ArrayList<>();
        List<Venta> ventas = ventaRepositorio.findAll();
        for (Venta venta : ventas) {
            List<ProductoVenta> productos = venta.getProductos();
            VentaProductoDTO dto = new VentaProductoDTO(venta, productos);
            listaDTO.add(dto);
        }
        return listaDTO;
    }

    public List<VentaProductoDTO> listarVentasCliente(Long idCliente) {
        List<VentaProductoDTO> listaDTO = new ArrayList<>();
        List<Venta> ventas = ventaRepositorio.findByCliente_Id(idCliente);
        for (Venta venta : ventas) {
            List<ProductoVenta> productos = venta.getProductos();
            VentaProductoDTO dto = new VentaProductoDTO(venta, productos);
            listaDTO.add(dto);
        }
        return listaDTO;
    }

    public VentaProductoDTO buscarVentaProductoDTO(Long id) {
        Venta venta = ventaRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        List<ProductoVenta> productos = venta.getProductos();
        return new VentaProductoDTO(venta, productos);
    }

    public void actualizarVenta(VentaProductoDTO dto) {
        Venta ventaExistente = ventaRepositorio.findById(dto.getVenta().getIdVenta())
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        
        // Actualizar los campos de la venta
        ventaExistente.setFechaVenta(dto.getVenta().getFechaVenta());
        ventaExistente.setTipoVenta(dto.getVenta().getTipoVenta());
        ventaExistente.setEstadoVenta(dto.getVenta().getEstadoVenta());
        ventaExistente.setCliente(dto.getVenta().getCliente());
        ventaExistente.setEmpleado(dto.getVenta().getEmpleado());
    
        // Eliminar relaciones de productos existentes
        List<ProductoVenta> productosExistentes = productoVentaRepositorio.obtenerProductosPorIdVenta(ventaExistente.getIdVenta());
        for (ProductoVenta productoExistente : productosExistentes) {
            productoVentaRepositorio.deleteById(productoExistente.getId());
        }
    
        // Calcular nuevo precio total antes de guardar la venta
        Float precioTotal = 0.0f;
    
        // Guardar nuevas relaciones producto-venta
        for (ProductoVenta productoNuevo : dto.getProductosVenta()) {
            if (productoNuevo.getProducto() == null || productoNuevo.getCantidad() == null) {
                continue; // Evita productos inválidos
            }
    
            // Obtener producto desde la BD para calcular precio
            Producto producto = productoRepositorio.findById(productoNuevo.getProducto().getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    
            precioTotal += producto.getProducPrecio() * productoNuevo.getCantidad();
            
            // Asociar la venta al producto y guardar
            productoNuevo.setVenta(ventaExistente);
            productoVentaRepositorio.save(productoNuevo);
        }
    
        // Ahora sí, actualizar y guardar la venta con el nuevo precio total
        ventaExistente.setPrecioTotal(precioTotal);
        ventaRepositorio.save(ventaExistente);
    }
    

    public void eliminarVentaProductos(Long id) {
        Venta venta = ventaRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        List<ProductoVenta> productos = venta.getProductos();

        for (ProductoVenta producto : productos) {
            productoVentaRepositorio.deleteById(producto.getId());
        }

        ventaRepositorio.deleteById(venta.getIdVenta());
    }
}