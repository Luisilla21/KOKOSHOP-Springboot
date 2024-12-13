package com.sena.kokoshop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.kokoshop.dto.ProductoTallaDTO;
import com.sena.kokoshop.entidades.CantidadTalla;
import com.sena.kokoshop.entidades.Producto;
import com.sena.kokoshop.repositorio.CantidadTallaRepositorio;
import com.sena.kokoshop.repositorio.ProductoRepositorio;

import jakarta.transaction.Transactional;

@Service
public class ProductoTallaService {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Autowired
    private CantidadTallaRepositorio tallaRepositorio;

    @Transactional
    public Producto guardarProductoConTallas(ProductoTallaDTO productoTallaDTO) {
        Producto producto = productoTallaDTO.getProducto();
        // Guardar el producto primero
        Producto productoGuardado = productoRepositorio.save(producto);

        // Asignar el producto guardado a cada talla y guardar
        for (CantidadTalla talla : productoTallaDTO.getTallas()) {
            talla.setProducto(productoGuardado);
            tallaRepositorio.save(talla);
        }

        return productoGuardado;
    }

    public List<ProductoTallaDTO> listarTodosLosProductos() {
        List<ProductoTallaDTO> listaDTO = new ArrayList<>();
        List<Producto> productos = productoRepositorio.findAll();
        for (Producto producto : productos) {
            List<CantidadTalla> tallas = producto.getTallas();
            ProductoTallaDTO dto = new ProductoTallaDTO(producto, tallas);
            listaDTO.add(dto);
        }
        return listaDTO;
    }

    public ProductoTallaDTO buscarProductoTallaDTO(long id) {
        Producto producto = productoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<CantidadTalla> tallas = producto.getTallas();

        return new ProductoTallaDTO(producto, tallas);
    }

    public void actualizarProductoTallas(ProductoTallaDTO dto) {
        Producto productoExistente = productoRepositorio.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Producto producto = dto.getProducto();

        List<CantidadTalla> tallasExistentes = tallaRepositorio.obtenerTallasPorIdProducto(dto.getProductoId());
        List<CantidadTalla> tallasNuevas = dto.getTallas();

        productoExistente.setProducNom(producto.getProducNom());
        productoExistente.setProducPrecio(producto.getProducPrecio());
        productoExistente.setTipoPrenda(producto.getTipoPrenda());
        productoExistente.setProductosVenta(producto.getProductosVenta());

        productoRepositorio.save(productoExistente);

        for (CantidadTalla tallaExistente : tallasExistentes) {
            tallaRepositorio.deleteById(tallaExistente.getId());
        }

        for (CantidadTalla tallaNueva : tallasNuevas) {
            tallaNueva.setProducto(productoExistente);
            tallaRepositorio.save(tallaNueva);
        }

    }

    public void eliminarProductoTallas(long idPro) {
        Producto producto = productoRepositorio.findById(idPro)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        List<CantidadTalla> tallas = tallaRepositorio.obtenerTallasPorIdProducto(idPro);

        for (CantidadTalla talla : tallas) {
            tallaRepositorio.deleteById(talla.getId());
        }

        productoRepositorio.deleteById(producto.getIdProducto());
    }
}
