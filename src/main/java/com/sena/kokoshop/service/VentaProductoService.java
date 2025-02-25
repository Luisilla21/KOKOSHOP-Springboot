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

        for (ProductoVenta producto : ventaProductoDTO.getProductos()) {
            producto.setVenta(ventaGuardada);
            productoVentaRepositorio.save(producto);
        }
    }

    public List<VentaProductoDTO> listarTodasLasVentas() {
        List<VentaProductoDTO> listaDTO = new ArrayList<>();
        return listaDTO;
    }

    public VentaProductoDTO buscarVentaProductoDTO(Long id) {
        retunr new VentaProductoDTO();
    }

    public void actualizarVenta(Venta venta) {
    }

    public void eliminarVentaProductos(Long id) {
    }
}