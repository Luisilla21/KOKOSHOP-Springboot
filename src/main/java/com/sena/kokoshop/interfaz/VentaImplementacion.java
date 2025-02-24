package com.sena.kokoshop.interfaz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.kokoshop.entidades.Venta;
import com.sena.kokoshop.repositorio.VentaRepositorio;

@Service
public class VentaImplementacion implements VentaInterfaz {
    @Autowired
    private VentaRepositorio repositorioVenta;

    @Override
    public List<Venta> listarTodasLasVentas() {
        return repositorioVenta.findAll();
    }

    @Override
    public Venta guardarVenta(Venta venta) {
        return repositorioVenta.save(venta);
    }

    @Override
    public Venta obtenerVentaPorId(long id) {
        return repositorioVenta.findById(id).orElse(null);
    }

    @Override
    public Venta actualizarVenta(Venta venta) {
        return repositorioVenta.save(venta);
    }

    @Override
    public void eliminarVenta(long id) {
        repositorioVenta.deleteById(id);
    }
}
