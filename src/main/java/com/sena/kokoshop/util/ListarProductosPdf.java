package com.sena.kokoshop.util;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sena.kokoshop.dto.ProductoTallaDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("productos/index")
public class ListarProductosPdf extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<ProductoTallaDTO> listadoProductos = (List<ProductoTallaDTO>) model.get("productosTallas");

        // PdfPTable tablaProductos = new

        // listadoProductos.forEach(producto ->{});
        throw new UnsupportedOperationException("Unimplemented method 'buildPdfDocument'");
    }

}
