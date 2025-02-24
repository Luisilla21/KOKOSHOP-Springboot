package com.sena.kokoshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.kokoshop.dto.VentaProductoDTO;
import com.sena.kokoshop.entidades.Venta;
import com.sena.kokoshop.interfaz.VentaInterfaz;

@Service
public class VentaProductoService {

    @Autowired
    private VentaInterfaz ventaInterfaz;

    public List<Venta> listarTodasLasVentas() {
        return ventaInterfaz.listarTodasLasVentas();
    }

    public void guardarVentaConProductos(VentaProductoDTO ventaProductoDTO) {
        Venta venta = ventaProductoDTO.getVenta();
        ventaInterfaz.guardarVenta(venta);
    }

    public VentaProductoDTO buscarVentaProductoDTO(Long id) {
        Venta venta = ventaInterfaz.obtenerVentaPorId(id);
        if (venta == null) {
            return null;
        }
        VentaProductoDTO ventaProductoDTO = new VentaProductoDTO();
        ventaProductoDTO.setVenta(venta);
        return ventaProductoDTO;
    }

    public void actualizarVenta(Venta venta) {
        ventaInterfaz.actualizarVenta(venta);
    }

    public void eliminarVentaProductos(Long id) {
        ventaInterfaz.eliminarVenta(id);
    }
}