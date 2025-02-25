package com.sena.kokoshop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.kokoshop.dto.VentaProductoDTO;
import com.sena.kokoshop.entidades.ProductoVenta;
import com.sena.kokoshop.entidades.Venta;
import com.sena.kokoshop.repositorio.ProductoVentaRepositorio;
import com.sena.kokoshop.repositorio.VentaRepositorio;

import jakarta.transaction.Transactional;

@Service
public class VentaProductoService {

    @Autowired
    private VentaRepositorio ventaRepositorio;

    @Autowired
    private ProductoVentaRepositorio productoVentaRepositorio;

    @Transactional
    public void guardarVentaConProductos(VentaProductoDTO ventaProductoDTO) {
        Venta venta = ventaProductoDTO.getVenta();

        Venta ventaGuardada = ventaRepositorio.save(venta);

        for (ProductoVenta producto : ventaProductoDTO.getProductosVenta()) {
            producto.setVenta(ventaGuardada);
            productoVentaRepositorio.save(producto);
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

    public VentaProductoDTO buscarVentaProductoDTO(Long id) {
        Venta venta = ventaRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        List<ProductoVenta> productos = venta.getProductos();
        return new VentaProductoDTO(venta, productos);
    }

    public void actualizarVenta(VentaProductoDTO dto) {
        Venta ventaExistente = ventaRepositorio.findById(dto.getVentaId()).orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        Venta venta = dto.getVenta();

        List<ProductoVenta> productosExistentes = productoVentaRepositorio.obtenerProductosPorIdVenta(dto.getVentaId());
        List<ProductoVenta> productos = dto.getProductosVenta();

        ventaExistente.setPrecioTotal(venta.getPrecioTotal());
        ventaExistente.setFechaVenta(venta.getFechaVenta());
        ventaExistente.setTipoVenta(venta.getTipoVenta());
        ventaExistente.setEstadoVenta(venta.getEstadoVenta());

        ventaRepositorio.save(ventaExistente);

        for(ProductoVenta productoExistente : productosExistentes){
            productoVentaRepositorio.deleteById(productoExistente.getId());
        }

        for(ProductoVenta productoNuevo : productos){
            productoNuevo.setVenta(ventaExistente);
            productoVentaRepositorio.save(productoNuevo);
        }
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