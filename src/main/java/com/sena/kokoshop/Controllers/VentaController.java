package com.sena.kokoshop.Controllers;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sena.kokoshop.dto.VentaProductoDTO;
import com.sena.kokoshop.entidades.ProductoVenta;
import com.sena.kokoshop.entidades.Usuario;
import com.sena.kokoshop.entidades.Venta;
import com.sena.kokoshop.interfaz.EmpleadoInterfaz;
import com.sena.kokoshop.interfaz.ProductoInterfaz;
import com.sena.kokoshop.interfaz.UsuarioInterfaz;
import com.sena.kokoshop.repositorio.UsuarioRepositorio;
import com.sena.kokoshop.service.VentaProductoService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaProductoService ventaService;

    @Autowired
    private UsuarioInterfaz usuarioInterfaz;

    @Autowired
    private EmpleadoInterfaz empleadoInterfaz;

    @Autowired
    private ProductoInterfaz productoInterfaz;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @GetMapping
    public String listarVentas(Model modelo) {
        modelo.addAttribute("ventasProductos", ventaService.listarTodasLasVentas());
        return "ventas/index";
    }

    @GetMapping("/nueva")
    public String crearVentaFormulario(Model modelo) {
        VentaProductoDTO ventaProductoDTO = new VentaProductoDTO();
        ventaProductoDTO.getProductosVenta().add(new ProductoVenta());

        modelo.addAttribute("ventaProductoDTO", ventaProductoDTO);
        modelo.addAttribute("clientes", usuarioInterfaz.listarTodosLosUsuarios());
        modelo.addAttribute("empleados", empleadoInterfaz.listarTodosLosEmpleados());
        modelo.addAttribute("productos", productoInterfaz.listarTodosLosProductos());
        return "ventas/crear_venta";
    }

    @PostMapping
    public String guardarVenta(@ModelAttribute("ventaProductoDTO") VentaProductoDTO ventaProductoDTO) {
        ventaService.guardarVentaConProductos(ventaProductoDTO);
        return "redirect:/ventas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEditar(@PathVariable Long id, Model modelo) {
        VentaProductoDTO vDto = ventaService.buscarVentaProductoDTO(id);
        if (vDto == null) {
            return "redirect:/ventas";
        }
        modelo.addAttribute("ventaProductoDTO", vDto);
        modelo.addAttribute("clientes", usuarioInterfaz.listarTodosLosUsuarios());
        modelo.addAttribute("empleados", empleadoInterfaz.listarTodosLosEmpleados());
        modelo.addAttribute("productos", productoInterfaz.listarTodosLosProductos());
        return "ventas/editar_venta";
    }

    @PostMapping("/actualizar/{idVenta}")
    public String actualizarVenta(@PathVariable Long idVenta,
                                  @ModelAttribute("ventaProductoDTO") VentaProductoDTO vDTO) {
        vDTO.getVenta().setIdVenta(idVenta);
        ventaService.actualizarVenta(vDTO);
        return "redirect:/ventas";
    }

    @GetMapping("/{id}")
    public String eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVentaProductos(id);
        return "redirect:/ventas";
    }

    @GetMapping("nuevo-producto")
    public String nuevoProductoFragment(Model model) {
        model.addAttribute("productoVenta", new ProductoVenta());
        return "fragments :: nuevo-producto";
    }

    

    @GetMapping("/imprimir/{id}")
    public void generarFactura(@PathVariable Long id, HttpServletResponse response) throws Exception {
        Venta venta = ventaService.obtenerVentaPorId(id);
        if (venta == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Venta no encontrada");
            return;
        }

        // Configurar la respuesta como PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=factura_" + id + ".pdf");

        // Crear documento PDF
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Fuente para el título
        Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font textFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
        Font boldFont = new Font(Font.HELVETICA, 12, Font.BOLD);

        // Encabezado de la factura
        Paragraph header = new Paragraph("KOKOSHOP", titleFont);
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);

        Paragraph address = new Paragraph("CC Pasaje Paisa Local 200\nCalle 10#11-08, Bogotá\nTeléfono: +57 321 9313578\nEmail: kokoshop1201@gmail.com", textFont);
        address.setAlignment(Element.ALIGN_CENTER);
        document.add(address);
        document.add(new Paragraph("\n"));

        // Información de la venta
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Paragraph saleInfo = new Paragraph("Fecha: " + dateFormat.format(venta.getFechaVenta()) + "\nTICKET NRO: " + id, textFont);
        saleInfo.setAlignment(Element.ALIGN_CENTER);
        document.add(saleInfo);
        document.add(new Paragraph("\n"));

        // Información del cliente
        Usuario cliente = venta.getCliente();
        Paragraph clientInfo = new Paragraph("Cliente: " + cliente.getNombre() + "\nApellido: " + cliente.getApellido() + "\nDocumento: " + cliente.getNumeroDocumento() + "\nTeléfono: " + cliente.getTelefono(), textFont);
        clientInfo.setAlignment(Element.ALIGN_CENTER);
        document.add(clientInfo);
        document.add(new Paragraph("\n"));

        // Tabla de productos
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3, 2, 2});

        PdfPCell cell;

        cell = new PdfPCell(new Phrase("Producto", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Cantidad", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Total", boldFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        List<ProductoVenta> productosVenta = venta.getProductos();
        for (ProductoVenta pv : productosVenta) {
            cell = new PdfPCell(new Phrase(pv.getProducto().getProducNom(), textFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(pv.getCantidad()), textFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("$" + (pv.getProducto().getProducPrecio() * pv.getCantidad()), textFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
        
        document.add(table);
        document.add(new Paragraph("\n"));

        // Mensaje de agradecimiento
        Paragraph thankYou = new Paragraph("Sr/Sra. " + cliente.getApellido() + "\n¡Gracias por su compra! Vuelva pronto", textFont);
        thankYou.setAlignment(Element.ALIGN_CENTER);
        document.add(thankYou);

        // Totales
        Paragraph total = new Paragraph("TOTAL A PAGAR: $" + venta.getPrecioTotal() + " COP", boldFont);
        total.setAlignment(Element.ALIGN_CENTER);
        document.add(total);

        document.close();
    }
}
