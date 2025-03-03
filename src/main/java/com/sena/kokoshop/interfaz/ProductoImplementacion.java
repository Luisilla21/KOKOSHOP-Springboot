package com.sena.kokoshop.interfaz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.kokoshop.entidades.Producto;
import com.sena.kokoshop.repositorio.ProductoRepositorio;

@Service
public class ProductoImplementacion implements ProductoInterfaz{
    @Autowired
    private ProductoRepositorio repositorio;
 
    
    @Override
    public List<Producto> listarTodosLosProductos(){
        return repositorio.findAll();
    }
    @Override
    public Producto guardarProducto(Producto producto){
        return repositorio.save(producto);
    }
    @Override
    public Producto obtenerProductoPorId(long id) {
        return repositorio.findById(id).get();
    }
    @Override
    public Producto actualizarProducto(Producto producto) {
        
        return repositorio.save(producto);
    }
    @Override
    public void eliminarProducto(long id) {
       repositorio.deleteById(id);
        
    }
    
}
