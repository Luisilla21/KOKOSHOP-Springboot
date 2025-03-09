package com.sena.kokoshop.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sena.kokoshop.dto.VentaProductoDTO;
import com.sena.kokoshop.entidades.Carrito;
import com.sena.kokoshop.entidades.EstadoCuenta;
import com.sena.kokoshop.entidades.Producto;
import com.sena.kokoshop.entidades.ProductoVenta;
import com.sena.kokoshop.entidades.Rol;
import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.interfaz.ProductoInterfaz;
import com.sena.kokoshop.interfaz.RolInterfaz;
import com.sena.kokoshop.interfaz.UsuarioInterfaz;
import com.sena.kokoshop.repositorio.CarritoRepositorio;
import com.sena.kokoshop.repositorio.EstadoCuentaRepositorio;
import com.sena.kokoshop.repositorio.UsuarioRepositorio;
import com.sena.kokoshop.service.VentaProductoService;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioInterfaz interfaz;

    @Autowired
    private RolInterfaz rolInterfaz;

    @Autowired
    private ProductoInterfaz productoInterfaz;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CarritoRepositorio carritoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private VentaProductoService ventaService;

    @Autowired
    private EstadoCuentaRepositorio estadoCuentaRepositorio;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/usuarios/")
    public String listarUsuarios(Model modelo) {
        List<Usuario> usuarios = interfaz.listarTodosLosUsuarios();
        List<Usuario> usuariosHabilitados = new ArrayList<>();
        List<Usuario> usuariosDeshabilitados = new ArrayList<>();

        usuarios.forEach(usuario -> {
            if ("HABILITADA".equals(usuario.getEstadoCuenta().getNombre())) {
                usuariosHabilitados.add(usuario);
            } else {
                usuariosDeshabilitados.add(usuario);

            }
        });
        modelo.addAttribute("usuariosHabilitados", usuariosHabilitados);
        modelo.addAttribute("usuariosDeshabilitados", usuariosDeshabilitados);

        return "usuarios/index"; // retorna al archivo usuarios
    }

    @GetMapping("/usuarios/nuevo")
    public String crearUsuarioFormulario(Model modelo) {
        Usuario usuario = new Usuario();
        modelo.addAttribute("usuario", usuario);
        return "usuarios/crear_usuario";
    }

    @PostMapping("/usuarios/")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        EstadoCuenta estadoCuenta = estadoCuentaRepositorio.findByNombre("HABILITADA");

        usuario.setEstadoCuenta(estadoCuenta);
        interfaz.guardarUsuario(usuario);
        return "redirect:/usuarios/";

    }

    @GetMapping("/usuarios/editar/{usuarioID}")
    public String mostrarFormularioDeEditar(@PathVariable Long usuarioID, Model modelo) {
        Usuario usuario = interfaz.obtenerUsuarioporId(usuarioID);

        if (usuario == null) {
            // Redirige o muestra un mensaje de error si el cliente no existe
            return "redirect:/usuarios";
        }
        System.out.println("--------------Usuario: " + usuario.getRol().getNombre());

        modelo.addAttribute("usuario", usuario);
        return "usuarios/editar_usuario";
    }

    @PostMapping("/usuarios/{usuarioID}")
    public String actualizarUsuario(@PathVariable Long usuarioID, @ModelAttribute("usuario") Usuario usuario,
            Model modelo) {
        Usuario usuarioExistente = interfaz.obtenerUsuarioporId(usuarioID);
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
            usuarioExistente.setRol(usuario.getRol());
            usuarioExistente.setEstadoCuenta(usuario.getEstadoCuenta());
            interfaz.actualizarUsuario(usuarioExistente);
        }
        return "redirect:/usuarios/";

    }

    @GetMapping("/usuarios/eliminar/{usuarioID}")
    public String eliminarUsuario(@PathVariable Long usuarioID) {
        Usuario usuario = interfaz.obtenerUsuarioporId(usuarioID);
        EstadoCuenta estadoCuenta = estadoCuentaRepositorio.findByNombre("DESHABILITADA");

        usuario.setEstadoCuenta(estadoCuenta);
        interfaz.actualizarUsuario(usuario);
        return "redirect:/usuarios/";
    }

    @GetMapping("/usuarios/habilitar/{usuarioID}")
    public String habilitarUsuario(@PathVariable Long usuarioID) {
        Usuario usuario = interfaz.obtenerUsuarioporId(usuarioID);
        EstadoCuenta estadoCuenta = estadoCuentaRepositorio.findByNombre("HABILITADA");

        usuario.setEstadoCuenta(estadoCuenta);
        interfaz.actualizarUsuario(usuario);
        return "redirect:/usuarios/";
    }

    @GetMapping("/login")
    public String mostrarFormularioDeLogin(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "registroExitoso", required = false) String registroExitoso,
            Model model) {
        if (error != null) {
            model.addAttribute("error", "Credenciales inválidas o cuenta deshabilitada.");
        }
        if (logout != null) {
            model.addAttribute("logout", "Has cerrado sesión exitosamente.");
            return "redirect:/index";
        }
        if (registroExitoso != null) {
            model.addAttribute("registroExitoso", "¡Registro exitoso! Ahora puedes iniciar sesión.");
        }
        return "login";
    }

    // Página de registro
    @GetMapping("/registro")
    public String mostrarFormularioDeRegistro(Model model) {
        model.addAttribute("usuarios", new Usuario());
        return "registro";
    }

    // Procesar registro
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Asignar un rol por defecto (por ejemplo, CLIENTE)
        Rol clienteRol = rolInterfaz.findByNombre("CLIENTE");

        usuario.setRol(clienteRol);

        EstadoCuenta estadoCuenta = estadoCuentaRepositorio.findByNombre("HABILITADA");

        usuario.setEstadoCuenta(estadoCuenta);

        Usuario usuarioNuevo = interfaz.guardarUsuario(usuario);

        // Crear un carrito vacío para el nuevo usuario
        Carrito carrito = new Carrito();
        carrito.setCliente(usuarioNuevo);
        carrito.setPrecioTotal(0.0f);
        carritoRepositorio.save(carrito);
        return "redirect:/login?registroExitoso";// Redirige al login con un mensaje
    }

    @GetMapping({ "/", "/index" })
    public String mostrarPaginaDeInicio(Model model) {
        List<Producto> productos = productoInterfaz.listarTodosLosProductos();

        List<Producto> productosLimitados = productos.stream().limit(4).toList();

        model.addAttribute("productos", productosLimitados);

        return "index"; // Asegúrate de que la vista "index.html" exista
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros";
    }

    @GetMapping("/asesoria")
    public String asesoria() {
        return "asesoria";
    }

    @GetMapping("/cliente/mis-compras/{email}")
    public String misCompras(@PathVariable String email, Model model) {
        Usuario usuario = usuarioRepositorio.findByEmail(email);

        if (usuario == null) {
            return "redirect:/index";
        }

        model.addAttribute("ventasProductos", ventaService.listarVentasCliente(usuario.getUsuarioID()));
        return "mis-compras";
    }

    @GetMapping("/cliente/mis-compras/detalles/{id}")
    public String detallesCompra(@PathVariable Long id, Model model) {
        VentaProductoDTO ventaProductoDTO = ventaService.buscarVentaProductoDTO(id);
        List<Producto> productos = new ArrayList<>();
        List<ProductoVenta> productosVenta = ventaProductoDTO.getProductosVenta();

        for (ProductoVenta productoVenta : ventaProductoDTO.getProductosVenta()) {
            productos.add(productoVenta.getProducto());
        }
        model.addAttribute("productos", productos);
        model.addAttribute("productosVenta", productosVenta);
        model.addAttribute("ventaProductoDTO", ventaProductoDTO);
        return "detalles-compra";
    }

}
