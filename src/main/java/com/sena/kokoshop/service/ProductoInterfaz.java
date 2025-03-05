package com.sena.kokoshop.service;

import com.sena.kokoshop.entidades.Producto;

public interface ProductoInterfaz {
    Producto obtenerProductoPorId(Long idProducto);
}