package com.sena.kokoshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.kokoshop.entidades.Producto;
import com.sena.kokoshop.repositorio.ProductoRepositorio;

@Service
public class ProductoServiceImpl implements ProductoInterfaz {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Override
    public Producto obtenerProductoPorId(Long idProducto) {
        return productoRepositorio.findById(idProducto).orElse(null);
    }
}