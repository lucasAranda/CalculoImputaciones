/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import dto.DtoImputacionCheque;
import dto.DtoImputacionMovimiento;
import dto.DtoVendedor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maquina0
 */
public class FachadaImputacion {

    private ConexionSQLServer conexion;

    public FachadaImputacion() {
        conexion = ConexionSQLServer.getConexion();
    }

    public List<String> buscarBasesDatos() {
        List<String> basesDatos = new ArrayList<>();
        try {
            ResultSet rs = conexion.obtenerBases();
            while (rs.next()) {
                if (rs.getString(1).startsWith("HORMICON")
                        || rs.getString(1).startsWith("COMPA")
                        || rs.getString(1).startsWith("FID")
                        ||rs.getString(1).startsWith("DESA")) {
                    basesDatos.add(rs.getString(1));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FachadaImputacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return basesDatos;
    }

    public boolean seleccionarEmpresa(String empresa) {
        return conexion.modificarConexion(empresa);
    }

    public List<DtoVendedor> obtenerVendedores() {
        List<DtoVendedor> vendedores = new ArrayList<>();
        try {
            ResultSet rs;
            PreparedStatement ps = conexion.conn.prepareStatement("SELECT COD_VENDED ,NOMBRE_VEN FROM GVA23");
            rs = ps.executeQuery();
            while (rs.next()) {
                DtoVendedor dto = new DtoVendedor();
                dto.setCodigoVendedor(rs.getString(1));
                dto.setNombreVendedor(rs.getString(2));
                vendedores.add(dto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FachadaImputacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vendedores;
    }

    public List<DtoImputacionMovimiento> buscarImputacionesMovimientoVendedor(String codVendedor, String fechaDesde, String fechaHasta) {
        List<DtoImputacionMovimiento> imputaciones = new ArrayList<>();
        try {
            ResultSet rs;
            PreparedStatement ps = conexion.conn.prepareStatement("SELECT CONVERT(VARCHAR(10),GVA07.FECHA_VTO, 103) AS 'FECHA VTO FACTURA'"
                    + ",CONVERT(VARCHAR(10),F_COMP_CAN, 103) AS 'FECHA RECIBO'"
                    + ",CAST(ROUND(GVA07.IMPORTE_VT, 2) AS NUMERIC (10,2)) AS 'IMPORTE FACTURA'"
                    + ",CAST(ROUND(IMPORT_CAN, 2) AS NUMERIC (10,2)) AS 'IMPORTE_CAN',"
                    + "GVA07.N_COMP AS 'NUMERO FACTURA',"
                    + "CONVERT(VARCHAR(10), FACTURAS.FECHA_EMIS, 103) AS 'FECHA FACTURA'"
                    + ",N_COMP_CAN AS 'NUMERO RECIBO'"
                    + ",CAST(ROUND(MONTO, 2) AS NUMERIC (10,2)) AS 'MONTO MOVIMIENTO'"
                    + ",CAST(ROUND(RECIBOS.IMPORTE, 2) AS NUMERIC (10,2)) AS 'IMPORTE RECIBO'"
                    + ",MOVIMIENTOS.DESCRIPCIO"
                    + ",MOVIMIENTOS.COD_GVA14"
                    + ",MOVIMIENTOS.RAZON_SOCI"
                    + ",MOVIMIENTOS.COD_VENDED"
                    + ",MOVIMIENTOS.NOMBRE_VEN "
                    + "FROM GVA07 "
                    + "INNER JOIN (SELECT ID_SBA05,MONTO,COD_COMP,MOVIMIENTO.COD_CTA,CUENTAS.DESCRIPCIO,FECHA,N_COMP,ID_SBA02,MOVIMIENTO.COD_GVA14,CLIENTES.RAZON_SOCI,CLIENTES.COD_VENDED,VENDEDORES.NOMBRE_VEN "
                    + "     FROM SBA05 AS MOVIMIENTO "
                    + "     INNER JOIN (SELECT COD_CTA,CTA_ASOCIA,DESCRIPCIO,SALDO_A_MO,SALDO_A_UN,SALDO_ACT,ID_SBA01 FROM SBA01) AS CUENTAS ON MOVIMIENTO.COD_CTA = CUENTAS.COD_CTA "
                    + "     INNER JOIN (SELECT COD_CLIENT,COD_VENDED,GRUPO_EMPR,RAZON_SOCI,COD_GVA14,ID_GVA14 FROM GVA14) AS CLIENTES ON MOVIMIENTO.COD_GVA14 = CLIENTES.COD_GVA14 "
                    + "     INNER JOIN (SELECT COD_VENDED,NOMBRE_VEN FROM GVA23) AS VENDEDORES ON CLIENTES.COD_VENDED = VENDEDORES.COD_VENDED "
                    + "     WHERE COD_COMP = 'REC' AND MOVIMIENTO.COD_CTA IN (1, 2)) AS MOVIMIENTOS ON N_COMP_CAN = MOVIMIENTOS.N_COMP "
                    + "INNER JOIN (SELECT COD_CLIENT,COD_VENDED,ESTADO,FECHA_EMIS,IMPORTE,N_COMP,T_COMP "
                    + "     FROM GVA12 WHERE T_COMP = 'REC') AS RECIBOS ON N_COMP_CAN = RECIBOS.N_COMP "
                    + "INNER JOIN (SELECT COD_CLIENT,COD_VENDED,ESTADO,FECHA_EMIS,IMPORTE,N_COMP,T_COMP "
                    + "     FROM GVA12 WHERE T_COMP = 'FAC') AS FACTURAS ON GVA07.N_COMP = FACTURAS.N_COMP "
                    + "WHERE T_COMP_CAN = 'REC' AND MOVIMIENTOS.COD_VENDED = ? AND F_COMP_CAN BETWEEN ? AND ? "
                    + "ORDER BY N_COMP_CAN");
            ps.setString(1, codVendedor);
            ps.setString(2, fechaDesde);
            ps.setString(3, fechaHasta);
            rs = ps.executeQuery();
            while (rs.next()) {
                DtoImputacionMovimiento imputacion = new DtoImputacionMovimiento();
                imputacion.setFechaVtoFactura(rs.getString(1));
                imputacion.setFechaRecibo(rs.getString(2));
                imputacion.setImporteFactura(rs.getString(3));
                imputacion.setImporteCancelado(rs.getString(4));
                imputacion.setNroFactura(rs.getString(5));
                imputacion.setFechaEmisionFactura(rs.getString(6));
                imputacion.setNroRecibo(rs.getString(7));
                imputacion.setMontoMovimiento(rs.getString(8));
                imputacion.setImporteRecibo(rs.getString(9));
                imputacion.setMonvimiento(rs.getString(10));
                imputacion.setCodCliente(rs.getString(11));
                imputacion.setRazonSocial(rs.getString(12));
                imputacion.setCodVendedor(rs.getString(13));
                imputacion.setNombreVendedor(rs.getString(14));
                imputaciones.add(imputacion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FachadaImputacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imputaciones;
    }

    public List<DtoImputacionMovimiento> buscarImputacionesMovimiento(String fechaDesde, String fechaHasta) {
        List<DtoImputacionMovimiento> imputaciones = new ArrayList<>();
        try {
            ResultSet rs;
            PreparedStatement ps = conexion.conn.prepareStatement("SELECT CONVERT(VARCHAR(10),GVA07.FECHA_VTO, 103) AS 'FECHA VTO FACTURA'"
                    + ",CONVERT(VARCHAR(10),F_COMP_CAN, 103) AS 'FECHA RECIBO'"
                    + ",CAST(ROUND(GVA07.IMPORTE_VT, 2) AS NUMERIC (10,2)) AS 'IMPORTE FACTURA'"
                    + ",CAST(ROUND(IMPORT_CAN, 2) AS NUMERIC (10,2)) AS 'IMPORTE_CAN',"
                    + "GVA07.N_COMP AS 'NUMERO FACTURA',"
                    + "CONVERT(VARCHAR(10), FACTURAS.FECHA_EMIS, 103) AS 'FECHA FACTURA'"
                    + ",N_COMP_CAN AS 'NUMERO RECIBO'"
                    + ",CAST(ROUND(MONTO, 2) AS NUMERIC (10,2)) AS 'MONTO MOVIMIENTO'"
                    + ",CAST(ROUND(RECIBOS.IMPORTE, 2) AS NUMERIC (10,2)) AS 'IMPORTE RECIBO'"
                    + ",MOVIMIENTOS.DESCRIPCIO"
                    + ",MOVIMIENTOS.COD_GVA14"
                    + ",MOVIMIENTOS.RAZON_SOCI"
                    + ",MOVIMIENTOS.COD_VENDED"
                    + ",MOVIMIENTOS.NOMBRE_VEN "
                    + "FROM GVA07 "
                    + "INNER JOIN (SELECT ID_SBA05,MONTO,COD_COMP,MOVIMIENTO.COD_CTA,CUENTAS.DESCRIPCIO,FECHA,N_COMP,ID_SBA02,MOVIMIENTO.COD_GVA14,CLIENTES.RAZON_SOCI,CLIENTES.COD_VENDED,VENDEDORES.NOMBRE_VEN "
                    + "     FROM SBA05 AS MOVIMIENTO "
                    + "     INNER JOIN (SELECT COD_CTA,CTA_ASOCIA,DESCRIPCIO,SALDO_A_MO,SALDO_A_UN,SALDO_ACT,ID_SBA01 FROM SBA01) AS CUENTAS ON MOVIMIENTO.COD_CTA = CUENTAS.COD_CTA "
                    + "     INNER JOIN (SELECT COD_CLIENT,COD_VENDED,GRUPO_EMPR,RAZON_SOCI,COD_GVA14,ID_GVA14 FROM GVA14) AS CLIENTES ON MOVIMIENTO.COD_GVA14 = CLIENTES.COD_GVA14 "
                    + "     INNER JOIN (SELECT COD_VENDED,NOMBRE_VEN FROM GVA23) AS VENDEDORES ON CLIENTES.COD_VENDED = VENDEDORES.COD_VENDED "
                    + "     WHERE COD_COMP = 'REC' AND MOVIMIENTO.COD_CTA IN (1, 2)) AS MOVIMIENTOS ON N_COMP_CAN = MOVIMIENTOS.N_COMP "
                    + "INNER JOIN (SELECT COD_CLIENT,COD_VENDED,ESTADO,FECHA_EMIS,IMPORTE,N_COMP,T_COMP "
                    + "     FROM GVA12 WHERE T_COMP = 'REC') AS RECIBOS ON N_COMP_CAN = RECIBOS.N_COMP "
                    + "INNER JOIN (SELECT COD_CLIENT,COD_VENDED,ESTADO,FECHA_EMIS,IMPORTE,N_COMP,T_COMP "
                    + "     FROM GVA12 WHERE T_COMP = 'FAC') AS FACTURAS ON GVA07.N_COMP = FACTURAS.N_COMP "
                    + "WHERE T_COMP_CAN = 'REC' AND F_COMP_CAN BETWEEN ? AND ? "
                    + "ORDER BY N_COMP_CAN");
            ps.setString(1, fechaDesde);
            ps.setString(2, fechaHasta);
            rs = ps.executeQuery();
            while (rs.next()) {
                DtoImputacionMovimiento imputacion = new DtoImputacionMovimiento();
                imputacion.setFechaVtoFactura(rs.getString(1));
                imputacion.setFechaRecibo(rs.getString(2));
                imputacion.setImporteFactura(rs.getString(3));
                imputacion.setImporteCancelado(rs.getString(4));
                imputacion.setNroFactura(rs.getString(5));
                imputacion.setFechaEmisionFactura(rs.getString(6));
                imputacion.setNroRecibo(rs.getString(7));
                imputacion.setMontoMovimiento(rs.getString(8));
                imputacion.setImporteRecibo(rs.getString(9));
                imputacion.setMonvimiento(rs.getString(10));
                imputacion.setCodCliente(rs.getString(11));
                imputacion.setRazonSocial(rs.getString(12));
                imputacion.setCodVendedor(rs.getString(13));
                imputacion.setNombreVendedor(rs.getString(14));
                imputaciones.add(imputacion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FachadaImputacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imputaciones;
    }

    public List<DtoImputacionCheque> buscarImputacionesCheque(String fechaDesde, String fechaHasta) {
        List<DtoImputacionCheque> imputaciones = new ArrayList<>();
        try {
            ResultSet rs;
            PreparedStatement ps = conexion.conn.prepareStatement("SELECT FACTURAS.COD_CLIENT AS 'CODIGO CLIENTE'"
                    + ",CLIENTES.RAZON_SOCI AS 'RAZON SOCIAL'"
                    + ",FACTURAS.COD_VENDED AS 'CODIGO VENDEDOR'"
                    + ",VENDEDORES.NOMBRE_VEN AS 'NOMBRE VENDEDOR'"
                    + ",CONVERT(VARCHAR(10),FACTURAS.FECHA_EMIS, 103) AS 'FECHA EMISION FACTURA'"
                    + ",CONVERT(VARCHAR(10),GVA07.FECHA_VTO, 103) AS 'FECHA VTO FACTURA'"
                    + ",CONVERT(VARCHAR(10),F_COMP_CAN, 103) AS 'FECHA RECIBO'"
                    + ",CAST(ROUND(GVA07.IMPORTE_VT, 2) AS NUMERIC (10,2)) AS 'IMPORTE FACTURA'"
                    + ",CAST(ROUND(IMPORT_CAN, 2) AS NUMERIC (10,2)) AS 'IMPORTE_CAN'"
                    + ",GVA07.N_COMP AS 'NUMERO FACTURA'"
                    + ",CAST(ROUND(RECIBOS.IMPORTE, 2) AS NUMERIC (10,2)) AS 'IMPORTE RECIBO'"
                    + ",N_COMP_CAN AS 'NUMERO RECIBO'"
                    + ",CONVERT(VARCHAR(10),CAST(CHEQUES.N_CHEQUE AS INT)) AS 'NUMERO CHEQUE'"
                    + ",CAST(ROUND(CHEQUES.IMPORTE_CH, 2) AS NUMERIC (10,2)) AS 'IMPORTE CHEQUE'"
                    + ",CONVERT(VARCHAR(10),CHEQUES.F_EMISION, 103) AS 'FECHA EMISION CHEQUE'"
                    + ",CONVERT(VARCHAR(10),CHEQUES.FECHA_CHEQ, 103) AS 'FECHA COBRO CHEQUE' "
                    + "FROM GVA07 "
                    + "INNER JOIN (SELECT COD_CLIENT,COD_VENDED,ESTADO,FECHA_EMIS,IMPORTE,N_COMP,T_COMP "
                    + "     FROM GVA12 WHERE T_COMP = 'FAC') AS FACTURAS ON GVA07.N_COMP = FACTURAS.N_COMP "
                    + "LEFT OUTER JOIN (SELECT ID_SBA14,ESTADO,F_EMISION,FECHA_CHEQ,FECHA_REC,IMPORTE_CH,N_CHEQUE,N_COMP_REC,T_COMP_REC,TIPO_CHEQU "
                    + "     FROM SBA14) AS CHEQUES ON N_COMP_CAN = CHEQUES.N_COMP_REC "
                    + "INNER JOIN (SELECT COD_CLIENT,COD_VENDED,ESTADO,FECHA_EMIS,IMPORTE,N_COMP,T_COMP "
                    + "     FROM GVA12 WHERE T_COMP = 'REC') AS RECIBOS ON N_COMP_CAN = RECIBOS.N_COMP "
                    + "INNER JOIN (SELECT COD_VENDED,NOMBRE_VEN "
                    + "     FROM GVA23) AS VENDEDORES ON FACTURAS.COD_VENDED = VENDEDORES.COD_VENDED "
                    + "INNER JOIN (SELECT COD_CLIENT,COD_VENDED,GRUPO_EMPR,RAZON_SOCI,COD_GVA14,ID_GVA14 "
                    + "     FROM GVA14) AS CLIENTES ON FACTURAS.COD_CLIENT = CLIENTES.COD_CLIENT "
                    + "WHERE T_COMP_CAN = 'REC' AND F_COMP_CAN BETWEEN ? AND ? "
                    + "ORDER BY N_COMP_CAN");
            ps.setString(1, fechaDesde);
            ps.setString(2, fechaHasta);
            rs = ps.executeQuery();
            while (rs.next()) {
                DtoImputacionCheque dto = new DtoImputacionCheque();
                dto.setCodCliente(rs.getString(1));
                dto.setRazonSocial(rs.getString(2));
                dto.setCodVendedor(rs.getString(3));
                dto.setNombreVendedor(rs.getString(4));
                dto.setFechaEmisionFactura(rs.getString(5));
                dto.setFechaVtoFactura(rs.getString(6));
                dto.setFechaRecibo(rs.getString(7));
                dto.setImporteFactura(rs.getString(8));
                dto.setImporteCancelado(rs.getString(9));
                dto.setNroFactura(rs.getString(10));
                dto.setImporteRecibo(rs.getString(11));
                dto.setNroRecibo(rs.getString(12));
                dto.setNroCheque(rs.getString(13));
                dto.setImporteCheque(rs.getString(14));
                dto.setFechaEmisCheque(rs.getString(15));
                dto.setFechaCobroCheque(rs.getString(16));
                imputaciones.add(dto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FachadaImputacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imputaciones;
    }

    public List<DtoImputacionCheque> buscarImputacionesChequeVendedor(String codVendedor, String fechaDesde, String fechaHasta) {
        List<DtoImputacionCheque> imputaciones = new ArrayList<>();
        try {
            ResultSet rs;
            PreparedStatement ps = conexion.conn.prepareStatement("SELECT FACTURAS.COD_CLIENT AS 'CODIGO CLIENTE'"
                    + ",CLIENTES.RAZON_SOCI AS 'RAZON SOCIAL'"
                    + ",FACTURAS.COD_VENDED AS 'CODIGO VENDEDOR'"
                    + ",VENDEDORES.NOMBRE_VEN AS 'NOMBRE VENDEDOR'"
                    + ",CONVERT(VARCHAR(10),FACTURAS.FECHA_EMIS, 103) AS 'FECHA EMISION FACTURA'"
                    + ",CONVERT(VARCHAR(10),GVA07.FECHA_VTO, 103) AS 'FECHA VTO FACTURA'"
                    + ",CONVERT(VARCHAR(10),F_COMP_CAN, 103) AS 'FECHA RECIBO'"
                    + ",CAST(ROUND(GVA07.IMPORTE_VT, 2) AS NUMERIC (10,2)) AS 'IMPORTE FACTURA'"
                    + ",CAST(ROUND(IMPORT_CAN, 2) AS NUMERIC (10,2)) AS 'IMPORTE_CAN'"
                    + ",GVA07.N_COMP AS 'NUMERO FACTURA'"
                    + ",CAST(ROUND(RECIBOS.IMPORTE, 2) AS NUMERIC (10,2)) AS 'IMPORTE RECIBO'"
                    + ",N_COMP_CAN AS 'NUMERO RECIBO'"
                    + ",CONVERT(VARCHAR(10),CAST(CHEQUES.N_CHEQUE AS INT)) AS 'NUMERO CHEQUE'"
                    + ",CAST(ROUND(CHEQUES.IMPORTE_CH, 2) AS NUMERIC (10,2)) AS 'IMPORTE CHEQUE'"
                    + ",CONVERT(VARCHAR(10),CHEQUES.F_EMISION, 103) AS 'FECHA EMISION CHEQUE'"
                    + ",CONVERT(VARCHAR(10),CHEQUES.FECHA_CHEQ, 103) AS 'FECHA COBRO CHEQUE' "
                    + "FROM GVA07 "
                    + "INNER JOIN (SELECT COD_CLIENT,COD_VENDED,ESTADO,FECHA_EMIS,IMPORTE,N_COMP,T_COMP "
                    + "     FROM GVA12 WHERE T_COMP = 'FAC') AS FACTURAS ON GVA07.N_COMP = FACTURAS.N_COMP "
                    + "LEFT OUTER JOIN (SELECT ID_SBA14,ESTADO,F_EMISION,FECHA_CHEQ,FECHA_REC,IMPORTE_CH,N_CHEQUE,N_COMP_REC,T_COMP_REC,TIPO_CHEQU "
                    + "     FROM SBA14) AS CHEQUES ON N_COMP_CAN = CHEQUES.N_COMP_REC "
                    + "INNER JOIN (SELECT COD_CLIENT,COD_VENDED,ESTADO,FECHA_EMIS,IMPORTE,N_COMP,T_COMP "
                    + "     FROM GVA12 WHERE T_COMP = 'REC') AS RECIBOS ON N_COMP_CAN = RECIBOS.N_COMP "
                    + "INNER JOIN (SELECT COD_VENDED,NOMBRE_VEN "
                    + "     FROM GVA23) AS VENDEDORES ON FACTURAS.COD_VENDED = VENDEDORES.COD_VENDED "
                    + "INNER JOIN (SELECT COD_CLIENT,COD_VENDED,GRUPO_EMPR,RAZON_SOCI,COD_GVA14,ID_GVA14 "
                    + "     FROM GVA14) AS CLIENTES ON FACTURAS.COD_CLIENT = CLIENTES.COD_CLIENT "
                    + "WHERE T_COMP_CAN = 'REC' AND FACTURAS.COD_VENDED = ? AND F_COMP_CAN BETWEEN ? AND ? "
                    + "ORDER BY N_COMP_CAN");
            ps.setString(1, codVendedor);
            ps.setString(2, fechaDesde);
            ps.setString(3, fechaHasta);
            rs = ps.executeQuery();
            while (rs.next()) {
                DtoImputacionCheque dto = new DtoImputacionCheque();
                dto.setCodCliente(rs.getString(1));
                dto.setRazonSocial(rs.getString(2));
                dto.setCodVendedor(rs.getString(3));
                dto.setNombreVendedor(rs.getString(4));
                dto.setFechaEmisionFactura(rs.getString(5));
                dto.setFechaVtoFactura(rs.getString(6));
                dto.setFechaRecibo(rs.getString(7));
                dto.setImporteFactura(rs.getString(8));
                dto.setImporteCancelado(rs.getString(9));
                dto.setNroFactura(rs.getString(10));
                dto.setImporteRecibo(rs.getString(11));
                dto.setNroRecibo(rs.getString(12));
                dto.setNroCheque(rs.getString(13));
                dto.setImporteCheque(rs.getString(14));
                dto.setFechaEmisCheque(rs.getString(15));
                dto.setFechaCobroCheque(rs.getString(16));
                imputaciones.add(dto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FachadaImputacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imputaciones;
    }
}
