package com.sena.kokoshop.interfaz;

import java.util.List;

import com.sena.kokoshop.entidades.Venta;

public interface VentaInterfaz {
    public List<Venta> listarTodasLasVentas();
    public Venta guardarVenta(Venta venta);
    public Venta obtenerVentaPorId(long id);
    public Venta actualizarVenta(Venta venta);
    public void eliminarVenta(long id);
}
