package com.sena.kokoshop.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sena.kokoshop.entidades.Producto;
import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.interfaz.ProductoInterfaz;
import com.sena.kokoshop.interfaz.UsuarioInterfaz;

@Controller
public class CatalogoController {

    @Autowired
    private ProductoInterfaz productoInterfaz;

    @Autowired
    private UsuarioInterfaz usuarioInterfaz;

    @GetMapping("/catalogo")
    public String listarProductosCatalogo(Model modelo) {
        modelo.addAttribute("productos", productoInterfaz.listarTodosLosProductos());
        return "catalogo"; // retorna al archivo productos
    }

    @GetMapping("/catalogo/producto/{id}")
    public String mostraInterfazProducto(@PathVariable Long id, Model modelo) {
        Producto producto = productoInterfaz.obtenerProductoPorId(id);
        if (producto == null) {
            // Redirige o muestra un mensaje de error si el producto no existe
            return "redirect:/productos";
        }
        modelo.addAttribute("producto", producto);
        return "vistaProductos";
    }

    @GetMapping("/catalogo/compra/{id}")
    public String mostraInterfazCompra(@PathVariable Long id, Model modelo) {
        Producto producto = productoInterfaz.obtenerProductoPorId(id);
        if (producto == null) {
            // Redirige o muestra un mensaje de error si el producto no existe
            return "redirect:/productos";
        }
        modelo.addAttribute("producto", producto);
        return "compra";
    }

    @PostMapping("/catalogo/{usuarioID}")
    public String actualizarUsuario(@PathVariable Long usuarioID, @ModelAttribute("usuario") Usuario usuario,
            Model modelo) {
        Usuario usuarioExistente = usuarioInterfaz.obtenerUsuarioporId(usuarioID);
        if (usuarioExistente != null) {
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setApellido(usuario.getApellido());
            usuarioExistente.setTipoDocumento(usuario.getTipoDocumento());
            usuarioExistente.setNumeroDocumento(usuario.getNumeroDocumento());
            usuarioExistente.setDireccion(usuario.getDireccion());
            usuarioExistente.setCiudad(usuario.getCiudad());
            usuarioExistente.setEmail(usuario.getEmail());
            usuarioExistente.setTelefono(usuario.getTelefono());
            usuarioExistente.setCompras(usuario.getCompras());
            usuarioInterfaz.actualizarUsuario(usuarioExistente);
        }
        return "redirect:/catalogo";

    }
}
