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
import com.sena.kokoshop.entidades.Usuario;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("/usuarios/index")
public class ListarUsuariosPdf extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        @SuppressWarnings("unchecked")
        List<Usuario> listadoUsuarios = (List<Usuario>) model.get("usuarios");

        document.setPageSize(PageSize.LETTER.rotate());
        document.open();

        PdfPTable tablaTitulo = new PdfPTable(1);
        PdfPCell celda = null;

        Font fuenteTitulo = FontFactory.getFont("Helvetica", 16, Color.BLACK);

        celda = new PdfPCell(new Phrase("USUARIOS", fuenteTitulo));
        //celda.setBorder(0);
        celda.setBackgroundColor(new Color(157, 157, 157));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);

        tablaTitulo.addCell(celda);
        //tablaTitulo.setSpacingAfter(30);

        PdfPTable tablaUsuarios = new PdfPTable(10);

        listadoUsuarios.forEach(usuario -> {
            tablaUsuarios.addCell(String.valueOf(usuario.getUsuarioID()));
            tablaUsuarios.addCell(usuario.getNombre());
            tablaUsuarios.addCell(usuario.getApellido());
            tablaUsuarios.addCell(usuario.getTipoDocumento());
            tablaUsuarios.addCell(usuario.getNumeroDocumento());
            tablaUsuarios.addCell(usuario.getDireccion());
            tablaUsuarios.addCell(usuario.getCiudad());
            tablaUsuarios.addCell(usuario.getEstado());
            tablaUsuarios.addCell(usuario.getCorreoElectronico());
            tablaUsuarios.addCell(usuario.getTelefono());

        });
        document.add(tablaTitulo);
        document.add(tablaUsuarios);
    }

}
