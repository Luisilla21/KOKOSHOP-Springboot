package com.sena.kokoshop.util;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.sena.kokoshop.entidades.Usuario;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("/usuarios/index.xlsx")
public class ListarUsuariosExcel extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=\"listado-usuarios.xlsx\"");
        Sheet hoja = workbook.createSheet("Usuarios");

        Row filaTitulo = hoja.createRow(0);

        Cell celda = filaTitulo.createCell(0);

        celda.setCellValue("LISTADO DE USUARIOS");


        Row filaData = hoja.createRow(2);
        String[] columnas = { "ID", "NOMBRE", "APELLIDOS", "TIPO DOC", "NUMERO DOC", "DIRECCIÓN", "CIUDAD",
                "CORREO ELECTRONICO", "TELEFONO" };

        for (int i = 0; i < columnas.length; i++) {
            celda = filaData.createCell(i);
            celda.setCellValue(columnas[i]);

        }

        List<Usuario> listadoUsuarios = (List<Usuario>) model.get("usuarios");

        int numFila = 3;
        for (Usuario usuario : listadoUsuarios) {
            filaData = hoja.createRow(numFila);
            filaData.createCell(0).setCellValue(usuario.getUsuarioID());
            filaData.createCell(1).setCellValue(usuario.getNombre());
            filaData.createCell(2).setCellValue(usuario.getApellido());
            filaData.createCell(3).setCellValue(usuario.getTipoDocumento());
            filaData.createCell(4).setCellValue(usuario.getNumeroDocumento());
            filaData.createCell(5).setCellValue(usuario.getDireccion());
            filaData.createCell(6).setCellValue(usuario.getCiudad());
            filaData.createCell(7).setCellValue(usuario.getCorreoElectronico());
            filaData.createCell(8).setCellValue(usuario.getTelefono());

            numFila ++;
        }
    }

}
