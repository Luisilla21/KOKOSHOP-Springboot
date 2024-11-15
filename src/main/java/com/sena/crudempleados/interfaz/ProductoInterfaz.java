package com.sena.crudempleados.interfaz;

import java.util.List;
import com.sena.crudempleados.entidades.Producto;

public interface ProductoInterfaz {
    
    public List<Producto> listarTodosLosProductos();
    public Producto guardarProducto(Producto producto);
    public Producto obtenerProductoPorId(long id);
    public Producto actualizarProducto(Producto producto);
    public void eliminarProducto(long id);

}
