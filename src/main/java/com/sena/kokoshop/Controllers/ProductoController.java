package com.sena.kokoshop.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sena.kokoshop.entidades.Producto;
import com.sena.kokoshop.interfaz.ProductoInterfaz;

@Controller
public class ProductoController {

    @Autowired
    private ProductoInterfaz interfaz;

    @GetMapping("/productos/")
    public String listarProductos(Model modelo) {
        List<Producto> productos = interfaz.listarTodosLosProductos();

        for (Producto producto : productos) {
            byte[] imagenBytes = producto.getImagen();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

        }

        modelo.addAttribute("productos", productos);
        return "productos/index"; // retorna al archivo productos
    }

    @GetMapping("/productos/nuevo")
    public String crearProductoFormulario(Model modelo, MultipartFile imagenForm) {
        Producto producto = new Producto();
        modelo.addAttribute("imagenForm", imagenForm);
        modelo.addAttribute("producto", producto);
        return "productos/crear_producto";
    }

    @PostMapping("/productos/")
    public String guardarProducto(@ModelAttribute("producto") Producto producto,
            @RequestParam("imagenForm") MultipartFile imagenForm) {

        try {
            producto.setImagen(imagenForm.getBytes());
            interfaz.guardarProducto(producto);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        return "redirect:/productos/";
    }

    @GetMapping("/productos/editar/{id}")
    public String mostrarFormularioDeEditar(@PathVariable Long id, Model modelo) {
        Producto producto = interfaz.obtenerProductoPorId(id);
        if (producto == null) {
            // Redirige o muestra un mensaje de error si el producto no existe
            return "redirect:/productos";
        }
        modelo.addAttribute("producto", producto);
        return "productos/editar_producto";
    }

    @PostMapping("/productos/actualizar/{idProducto}")
    public String actualizarProducto(@PathVariable Long idProducto,
            @ModelAttribute("producto") Producto producto,
            @RequestParam("imagenForm") MultipartFile imagenForm) {
        Producto productoExistente = interfaz.obtenerProductoPorId(idProducto);
        if (productoExistente != null) {
            productoExistente.setProducNom(producto.getProducNom());
            productoExistente.setProducDescripcion(producto.getProducDescripcion());
            productoExistente.setProducPrecio(producto.getProducPrecio());
            productoExistente.setTipoPrenda(producto.getTipoPrenda());
            productoExistente.setCantidad(producto.getCantidad());
            if (!imagenForm.isEmpty()) {
                try {
                    productoExistente.setImagen(imagenForm.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                    return "error";
                }
            }
            interfaz.actualizarProducto(productoExistente);
        }
        return "redirect:/productos/";
    }

    @GetMapping("/productos/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        interfaz.eliminarProducto(id);
        return "redirect:/productos/";
    }

    @GetMapping("/productos/imagen/{id}")
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable Long id) {
        Optional<Producto> producto = Optional.ofNullable(interfaz.obtenerProductoPorId(id));

        if (producto.isPresent() && producto.get().getImagen() != null) {
            byte[] imagenBytes = producto.get().getImagen();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Ajusta el tipo MIME según la imagen
            return new ResponseEntity<>(imagenBytes, headers, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }

}
