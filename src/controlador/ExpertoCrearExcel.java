/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;

import dto.DtoImputacionCheque;
import dto.DtoImputacionMovimiento;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author maquina0
 */
public class ExpertoCrearExcel {
    
    private static final String[] titlesMovimientos = {
            "Cod Cliente", "Razon Social", "Cod Vendedor", "Vendedor", "Nro de Recibo",
            "Fecha Emision", "Importe", "Nro de Factura", "Fecha Emision", "Fecha Vto",
            "Importe", "Importe Cancelado", "Movimiento", "Monto Movimiento", "Diferencia FAC REC"
    };
    
    private static final String[] titlesCheques = {
            "Cod Cliente", "Razon Social", "Cod Vendedor", "Vendedor", "Nro de Recibo",
            "Fecha Emision", "Importe", "Nro de Factura", "Fecha Emision", "Fecha Vto",
            "Importe", "Importe Cancelado", "Nro de Cheque", "Importe", "Fecha Emision",
            "Fecha Cobro", "Diferencia FAC REC", "Diferencia FAC CHQ"
    };

    public void crearExcel(List<DtoImputacionMovimiento> imputaciones, String fechaDesde, String fechaHasta) throws FileNotFoundException, IOException {
        Workbook wb = new XSSFWorkbook();
        
        Map<String, CellStyle> styles = createStyles(wb);

        Sheet sheet = wb.createSheet("Resultado Consulta");
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

        //title row
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(45);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Consulta Movimientos");
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$O$1"));

        //header row
        Row headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        for (int i = 0; i < titlesMovimientos.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(titlesMovimientos[i]);
            headerCell.setCellStyle(styles.get("header"));
        }

        int rownum = 2;
        for (int i = 0; i < imputaciones.size(); i++) {
            Row row = sheet.createRow(rownum++);
            for (int j = 0; j < titlesMovimientos.length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellStyle(styles.get("cell"));
            }
        }
        int j = 0;
        for (DtoImputacionMovimiento imputacion : imputaciones) {
            Row row = sheet.getRow(2 + j++);
            row.getCell(0).setCellValue(imputacion.getCodCliente());
            row.getCell(1).setCellValue(imputacion.getRazonSocial());
            row.getCell(2).setCellValue(imputacion.getCodVendedor());
            row.getCell(3).setCellValue(imputacion.getNombreVendedor());
            row.getCell(4).setCellValue(imputacion.getNroRecibo());
            row.getCell(5).setCellValue(imputacion.getFechaRecibo());
            row.getCell(6).setCellValue(imputacion.getImporteRecibo());
            row.getCell(7).setCellValue(imputacion.getNroFactura());
            row.getCell(8).setCellValue(imputacion.getFechaEmisionFactura());
            row.getCell(9).setCellValue(imputacion.getFechaVtoFactura());
            row.getCell(10).setCellValue(imputacion.getImporteFactura());
            row.getCell(11).setCellValue(imputacion.getImporteCancelado());
            row.getCell(12).setCellValue(imputacion.getMonvimiento());
            row.getCell(13).setCellValue(imputacion.getMontoMovimiento());
            row.getCell(14).setCellValue(imputacion.getDiferenciaFacRec());
        }
        //set sample data
        /*for (int i = 0; i < remitos.size(); i++) {
            Row row = sheet.getRow(2 + i);
            for (j = 0; j < sample_data[i].length; j++) {
                if(sample_data[i][j] == null) continue;

                if(sample_data[i][j] instanceof String) {
                    row.getCell(j).setCellValue((String)sample_data[i][j]);
                } else {
                    row.getCell(j).setCellValue((Double)sample_data[i][j]);
                }
            }
        }*/

        //finally set column widths, the width is measured in units of 1/256th of a character width
        //sheet.setColumnWidth(0, 50*256); //30 characters wide
        for (int i = 0; i < 15; i++) {
            sheet.setColumnWidth(i, 20*256);  //6 characters wide
        }
        //sheet.setColumnWidth(10, 10*256); //10 characters wide

        // Write the output to a file
        String file = "../Cosulta de Movimientos del " + fechaDesde + " al " + fechaHasta + ".xls";
        if(wb instanceof XSSFWorkbook) file += "x";
        FileOutputStream out = new FileOutputStream(file);
        wb.write(out);
        out.close();
    }
    
    /**
     * Create a library of cell styles
     */
    private static Map<String, CellStyle> createStyles(Workbook wb){
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)18);
        titleFont.setBold(true);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(titleFont);
        styles.put("title", style);

        Font monthFont = wb.createFont();
        monthFont.setFontHeightInPoints((short)11);
        monthFont.setColor(IndexedColors.WHITE.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(monthFont);
        style.setWrapText(true);
        styles.put("header", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put("cell", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula_2", style);

        return styles;
    }

    public void crearExcelCheques(List<DtoImputacionCheque> imputaciones, String fechaDesde, String fechaHasta) throws FileNotFoundException, IOException {
        Workbook wb = new XSSFWorkbook();
        
        Map<String, CellStyle> styles = createStyles(wb);

        Sheet sheet = wb.createSheet("Resultado Consulta");
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

        //title row
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(45);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Consulta Imputaciones");
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$R$1"));

        //header row
        Row headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        for (int i = 0; i < titlesCheques.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(titlesCheques[i]);
            headerCell.setCellStyle(styles.get("header"));
        }

        int rownum = 2;
        for (int i = 0; i < imputaciones.size(); i++) {
            Row row = sheet.createRow(rownum++);
            for (int j = 0; j < titlesCheques.length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellStyle(styles.get("cell"));
            }
        }
        int j = 0;
        for (DtoImputacionCheque imputacion : imputaciones) {
            Row row = sheet.getRow(2 + j++);
            row.getCell(0).setCellValue(imputacion.getCodCliente());
            row.getCell(1).setCellValue(imputacion.getRazonSocial());
            row.getCell(2).setCellValue(imputacion.getCodVendedor());
            row.getCell(3).setCellValue(imputacion.getNombreVendedor());
            row.getCell(4).setCellValue(imputacion.getNroRecibo());
            row.getCell(5).setCellValue(imputacion.getFechaRecibo());
            row.getCell(6).setCellValue(imputacion.getImporteRecibo());
            row.getCell(7).setCellValue(imputacion.getNroFactura());
            row.getCell(8).setCellValue(imputacion.getFechaEmisionFactura());
            row.getCell(9).setCellValue(imputacion.getFechaVtoFactura());
            row.getCell(10).setCellValue(imputacion.getImporteFactura());
            row.getCell(11).setCellValue(imputacion.getImporteCancelado());
            row.getCell(12).setCellValue(imputacion.getNroCheque());
            row.getCell(13).setCellValue(imputacion.getImporteCheque());
            row.getCell(14).setCellValue(imputacion.getFechaEmisCheque());
            row.getCell(15).setCellValue(imputacion.getFechaCobroCheque());
            row.getCell(16).setCellValue(imputacion.getDiferenciaFacRec());
            row.getCell(17).setCellValue(imputacion.getDiferenciaFacChq());
        }
        //set sample data
        /*for (int i = 0; i < remitos.size(); i++) {
            Row row = sheet.getRow(2 + i);
            for (j = 0; j < sample_data[i].length; j++) {
                if(sample_data[i][j] == null) continue;

                if(sample_data[i][j] instanceof String) {
                    row.getCell(j).setCellValue((String)sample_data[i][j]);
                } else {
                    row.getCell(j).setCellValue((Double)sample_data[i][j]);
                }
            }
        }*/

        //finally set column widths, the width is measured in units of 1/256th of a character width
        //sheet.setColumnWidth(0, 50*256); //30 characters wide
        for (int i = 0; i < 18; i++) {
            sheet.setColumnWidth(i, 20*256);  //6 characters wide
        }
        //sheet.setColumnWidth(10, 10*256); //10 characters wide

        // Write the output to a file
        String file = "../Cosulta de Imputaciones del " + fechaDesde + " al " + fechaHasta + ".xls";
        if(wb instanceof XSSFWorkbook) file += "x";
        FileOutputStream out = new FileOutputStream(file);
        wb.write(out);
        out.close();
    }
}
