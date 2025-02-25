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
        document.setMargins(-70, -70, 40, 40);
        document.open();

        PdfPTable tablaTitulo = new PdfPTable(1);
        PdfPCell celda = null;

        Font fuenteTitulo = FontFactory.getFont("Helvetica", 16, Color.BLACK);
        Font fuenteTituloColumna = FontFactory.getFont("Helvetica", 12, Color.BLACK);
        celda = new PdfPCell(new Phrase("USUARIOS", fuenteTitulo));
        // celda.setBorder(0);
        celda.setBackgroundColor(new Color(157, 157, 157));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);

        tablaTitulo.addCell(celda);
        // tablaTitulo.setSpacingAfter(30);

        PdfPTable tablaUsuarios = new PdfPTable(9);

        tablaUsuarios.setWidths(new float[] { 0.8f, 1f, 1f, 0.7f, 1.4f, 2.5f, 1.5f, 3.5f, 1.4f });

        celda = new PdfPCell(new Phrase("ID", fuenteTituloColumna));
        celda.setBackgroundColor(Color.lightGray);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        // celda.setPadding(10);
        tablaUsuarios.addCell(celda);

        celda = new PdfPCell(new Phrase("Nombre", fuenteTituloColumna));
        celda.setBackgroundColor(Color.lightGray);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        tablaUsuarios.addCell(celda);

        celda = new PdfPCell(new Phrase("Apellido", fuenteTituloColumna));
        celda.setBackgroundColor(Color.lightGray);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        tablaUsuarios.addCell(celda);

        celda = new PdfPCell(new Phrase("Tipo Doc", fuenteTituloColumna));
        celda.setBackgroundColor(Color.lightGray);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        tablaUsuarios.addCell(celda);

        celda = new PdfPCell(new Phrase("Numero de Documento", fuenteTituloColumna));
        celda.setBackgroundColor(Color.lightGray);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        tablaUsuarios.addCell(celda);

        celda = new PdfPCell(new Phrase("Dirección", fuenteTituloColumna));
        celda.setBackgroundColor(Color.lightGray);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        tablaUsuarios.addCell(celda);

        celda = new PdfPCell(new Phrase("Ciudad", fuenteTituloColumna));
        celda.setBackgroundColor(Color.lightGray);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        tablaUsuarios.addCell(celda);

        celda = new PdfPCell(new Phrase("Correo Electronico", fuenteTituloColumna));
        celda.setBackgroundColor(Color.lightGray);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        tablaUsuarios.addCell(celda);

        celda = new PdfPCell(new Phrase("Telefono", fuenteTituloColumna));
        celda.setBackgroundColor(Color.lightGray);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        tablaUsuarios.addCell(celda);

        listadoUsuarios.forEach(usuario -> {
            tablaUsuarios.addCell(String.valueOf(usuario.getUsuarioID()));
            tablaUsuarios.addCell(usuario.getNombre());
            tablaUsuarios.addCell(usuario.getApellido());
            tablaUsuarios.addCell(usuario.getTipoDocumento());
            tablaUsuarios.addCell(usuario.getNumeroDocumento());
            tablaUsuarios.addCell(usuario.getDireccion());
            tablaUsuarios.addCell(usuario.getCiudad());
            tablaUsuarios.addCell(usuario.getEmail());
            tablaUsuarios.addCell(usuario.getTelefono());

        });
        document.add(tablaTitulo);
        document.add(tablaUsuarios);
    }

}
