package com.sena.kokoshop.util;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sena.kokoshop.entidades.Venta;
import com.sena.kokoshop.entidades.ProductoVenta;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("/ventas/factura")
public class FacturaPdf extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        Venta venta = (Venta) model.get("venta");
        List<ProductoVenta> productos = venta.getProductos();

        document.setPageSize(PageSize.A4);
        document.open();

        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Color.BLACK);
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK);
        
        // Título
        PdfPTable tablaTitulo = new PdfPTable(1);
        tablaTitulo.setWidthPercentage(100);
        PdfPCell celdaTitulo = new PdfPCell(new Phrase("Factura de Venta", fontTitulo));
        celdaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdaTitulo.setBorder(0);
        celdaTitulo.setPadding(10);
        tablaTitulo.addCell(celdaTitulo);

        document.add(tablaTitulo);

        // Datos del cliente y venta
        PdfPTable tablaDatos = new PdfPTable(2);
        tablaDatos.setWidthPercentage(100);
        tablaDatos.setSpacingBefore(20);

        tablaDatos.addCell(new Phrase("Cliente: " + venta.getCliente().getNombre(), fontNormal));
        tablaDatos.addCell(new Phrase("Fecha: " + venta.getFechaVenta(), fontNormal));
        tablaDatos.addCell(new Phrase("Tipo de Venta: " + venta.getTipoVenta(), fontNormal));
        tablaDatos.addCell(new Phrase("Estado: " + venta.getEstadoVenta(), fontNormal));

        document.add(tablaDatos);

        // Tabla de productos
        PdfPTable tablaProductos = new PdfPTable(4);
        tablaProductos.setWidthPercentage(100);
        tablaProductos.setSpacingBefore(20);
        tablaProductos.addCell(new Phrase("Producto", fontNormal));
        tablaProductos.addCell(new Phrase("Cantidad", fontNormal));
        tablaProductos.addCell(new Phrase("Precio Unitario", fontNormal));
        tablaProductos.addCell(new Phrase("Subtotal", fontNormal));

        for (ProductoVenta pv : productos) {
            tablaProductos.addCell(pv.getProducto().getProducNom());
            tablaProductos.addCell(String.valueOf(pv.getCantidad()));
            tablaProductos.addCell(String.valueOf(pv.getProducto().getProducPrecio()));
            tablaProductos.addCell(String.valueOf(pv.getCantidad() * pv.getProducto().getProducPrecio()));
        }

        document.add(tablaProductos);

        // Total
        PdfPTable tablaTotal = new PdfPTable(1);
        tablaTotal.setWidthPercentage(100);
        tablaTotal.setSpacingBefore(20);
        PdfPCell celdaTotal = new PdfPCell(new Phrase("Total: $" + venta.getPrecioTotal(), fontTitulo));
        celdaTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
        celdaTotal.setBorder(0);
        celdaTotal.setPadding(10);
        tablaTotal.addCell(celdaTotal);

        document.add(tablaTotal);
    }
}
