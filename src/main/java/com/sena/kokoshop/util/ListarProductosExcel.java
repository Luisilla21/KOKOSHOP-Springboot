package com.sena.kokoshop.util;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.sena.kokoshop.entidades.Producto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("/productos/inventario.xlsx")
public class ListarProductosExcel extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=\"inventario-productos.xlsx\"");
        Sheet hoja = workbook.createSheet("Inventario");

        Row filaTitulo = hoja.createRow(0);
        Cell celda = filaTitulo.createCell(0);
        celda.setCellValue("INVENTARIO DE PRODUCTOS");

        Row filaData = hoja.createRow(2);
        String[] columnas = { "ID", "Nombre", "Descripción", "Precio", "Cantidad", "Tipo de Prenda" };

        for (int i = 0; i < columnas.length; i++) {
            celda = filaData.createCell(i);
            celda.setCellValue(columnas[i]);
        }

        @SuppressWarnings("unchecked")
        List<Producto> productos = (List<Producto>) model.get("productos");

        int numFila = 3;
        for (Producto producto : productos) {
            filaData = hoja.createRow(numFila);
            filaData.createCell(0).setCellValue(producto.getIdProducto());
            filaData.createCell(1).setCellValue(producto.getProducNom());
            filaData.createCell(2).setCellValue(producto.getProducDescripcion());
            filaData.createCell(3).setCellValue(producto.getProducPrecio());
            filaData.createCell(4).setCellValue(producto.getCantidad());
            filaData.createCell(5).setCellValue(producto.getTipoPrenda());
            numFila++;
        }
    }
}
