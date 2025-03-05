package com.sena.kokoshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.kokoshop.dto.CarritoProductoDTO;
import com.sena.kokoshop.entidades.Carrito;
import com.sena.kokoshop.entidades.Producto;
import com.sena.kokoshop.entidades.ProductoCarrito;
import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.repositorio.CarritoRepositorio;
import com.sena.kokoshop.repositorio.ProductoCarritoRepositorio;
import com.sena.kokoshop.repositorio.ProductoRepositorio;
import com.sena.kokoshop.repositorio.UsuarioRepositorio;

import jakarta.transaction.Transactional;

@Service
public class CarritoProductoService {

    @Autowired
    private CarritoRepositorio carritoRepositorio;

    @Autowired
    private ProductoCarritoRepositorio productoCarritoRepositorio;

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void guardarCarritoConProductos(CarritoProductoDTO carritoProductoDTO, String email) {
        Usuario usuario = usuarioRepositorio.findByEmail(email);
        Carrito carrito = carritoRepositorio.findByCliente_Id(usuario.getUsuarioID());
        if (carrito == null) {
            carrito = new Carrito();
            carrito.setCliente(usuario);
        }

        Float precioTotal = 0.0f;

        // Calcular el precio total del carrito
        if (carrito.getPrecioTotal() == null) {
            for (ProductoCarrito productoCarrito : carritoProductoDTO.getProductosCarrito()) {
                precioTotal += productoCarrito.getProducto().getProducPrecio() * productoCarrito.getCantidad();
            }
            carrito.setPrecioTotal(precioTotal);

        }

        // Guardar el carrito

        Carrito carritoGuardado = carritoRepositorio.save(carrito);

        // Guardar los productos asociados al carrito
        for (ProductoCarrito productoCarrito : carritoProductoDTO.getProductosCarrito()) {
            productoCarrito.setCarrito(carritoGuardado);
            productoCarritoRepositorio.save(productoCarrito);
        }

        for (ProductoCarrito productoCarrito : carritoProductoDTO.getProductosCarrito()) {
            Producto producto = productoCarrito.getProducto();
            Integer cantidad = productoCarrito.getCantidad();
            Integer cantidadActual = producto.getCantidad();
            producto.setCantidad(cantidadActual - cantidad);
            productoRepositorio.save(producto);
        }
    }

    @Transactional
    public void agregarProductoAlCarrito(Long idProducto, Integer cantidad, String email) {
        Usuario usuario = usuarioRepositorio.findByEmail(email);
        Carrito carrito = carritoRepositorio.findByCliente_Id(usuario.getUsuarioID());
        if (carrito == null) {
            carrito = new Carrito();
            carrito.setCliente(usuario);
            carrito.setPrecioTotal(0.0f);
            carritoRepositorio.save(carrito);
        }
        Producto producto = productoRepositorio.findById(idProducto).get();
        Integer cantidadActual = producto.getCantidad();
        if (cantidadActual >= cantidad) {

            ProductoCarrito productoCarrito = new ProductoCarrito(carrito, producto, cantidad);

            productoCarritoRepositorio.save(productoCarrito);
        }
    }

    public CarritoProductoDTO listarCarritoPorUsuario(String email) {
        Usuario usuario = usuarioRepositorio.findByEmail(email);
        Carrito carrito = carritoRepositorio.findByCliente_Id(usuario.getUsuarioID());
        if (carrito == null) {
            carrito = new Carrito();
            carrito.setCliente(usuario);
            carrito.setPrecioTotal(0.0f);
            carritoRepositorio.save(carrito);
        }
        CarritoProductoDTO carritoProductoDTO = new CarritoProductoDTO();
        carritoProductoDTO.setCarrito(carrito);
        carritoProductoDTO.setProductosCarrito(productoCarritoRepositorio.findByCarrito_Id(carrito.getIdCarrito()));
        return carritoProductoDTO;
    }

    public void eliminarProductoDelCarrito(Long idProductoCarrito, String email) {
        Usuario usuario = usuarioRepositorio.findByEmail(email);
        Carrito carrito = carritoRepositorio.findByCliente_Id(usuario.getUsuarioID());
        ProductoCarrito productoCarrito = productoCarritoRepositorio.findById(idProductoCarrito).get();
        Producto producto = productoCarrito.getProducto();
        Integer cantidad = productoCarrito.getCantidad();
        Integer cantidadActual = producto.getCantidad();
        producto.setCantidad(cantidadActual + cantidad);
        productoRepositorio.save(producto);
        productoCarritoRepositorio.deleteById(idProductoCarrito);
    }

    public void vaciarCarrito(String email) {
        Usuario usuario = usuarioRepositorio.findByEmail(email);
        Carrito carrito = carritoRepositorio.findByCliente_Id(usuario.getUsuarioID());
        for (ProductoCarrito productoCarrito : carrito.getProductos()) {
            Producto producto = productoCarrito.getProducto();
            Integer cantidad = productoCarrito.getCantidad();
            Integer cantidadActual = producto.getCantidad();
            producto.setCantidad(cantidadActual + cantidad);
            productoRepositorio.save(producto);
        }
        carrito.getProductos().clear();
        carrito.setPrecioTotal(0.0f);
        carritoRepositorio.save(carrito);
    }

    


}
