/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import comun.DiferenciaEntreFechas;
import database.FachadaImputacion;
import dto.DtoImputacionCheque;
import dto.DtoImputacionMovimiento;
import dto.DtoVendedor;
import excepciones.ExcepcionesComunes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maquina0
 */
public class ExpertoImputacion {

    private FachadaImputacion fachadaImputacion;
    private List<DtoVendedor> vendedores = new ArrayList<>();
    private List<DtoImputacionMovimiento> imputacionesMovimiento = new ArrayList<>();
    private List<DtoImputacionCheque> imputacionesCheque = new ArrayList<>();

    public ExpertoImputacion() {
        fachadaImputacion = new FachadaImputacion();
    }

    public List<String> buscarBasesDatos() {
        return fachadaImputacion.buscarBasesDatos();
    }

    public List<DtoVendedor> obtenerVendedores(String empresa) {
        if (fachadaImputacion.seleccionarEmpresa(empresa)) {
            vendedores = fachadaImputacion.obtenerVendedores();
        }
        System.out.println("Cantidad: " + vendedores.size());
        return vendedores;
    }

    public String obtenerCodVendedor(String vendedor) {
        String codVendedor = "00";
        for (DtoVendedor dto : vendedores) {
            if (dto.getNombreVendedor().equals(vendedor)) {
                codVendedor = dto.getCodigoVendedor();
                break;
            }
        }
        return codVendedor;
    }

    public List<DtoImputacionMovimiento> obtenerImputacionesMovimientoVendedor(String codVendedor, String fechaDesde, String fechaHasta) {
        imputacionesMovimiento = fachadaImputacion.buscarImputacionesMovimientoVendedor(codVendedor, fechaDesde, fechaHasta);
        for (DtoImputacionMovimiento dto : imputacionesMovimiento) {
            try {
                dto.setDiferenciaFacRec(String.valueOf(DiferenciaEntreFechas.calcularDiferenciaBarras(dto.getFechaEmisionFactura(), dto.getFechaRecibo())));
            } catch (ExcepcionesComunes ex) {
                Logger.getLogger(ExpertoImputacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return imputacionesMovimiento;
    }

    public List<DtoImputacionMovimiento> obtenerImputacionesMovimiento(String fechaDesde, String fechaHasta) {
        imputacionesMovimiento = fachadaImputacion.buscarImputacionesMovimiento(fechaDesde, fechaHasta);
        for (DtoImputacionMovimiento dto : imputacionesMovimiento) {
            try {
                dto.setDiferenciaFacRec(String.valueOf(DiferenciaEntreFechas.calcularDiferenciaBarras(dto.getFechaEmisionFactura(), dto.getFechaRecibo())));
            } catch (ExcepcionesComunes ex) {
                Logger.getLogger(ExpertoImputacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return imputacionesMovimiento;
    }

    public List<DtoImputacionCheque> obtenerImputacionesCheque(String fechaDesde, String fechaHasta) {
        imputacionesCheque = fachadaImputacion.buscarImputacionesCheque(fechaDesde, fechaHasta);
        for (DtoImputacionCheque dto : imputacionesCheque) {
            try {
                dto.setDiferenciaFacRec(String.valueOf(DiferenciaEntreFechas.calcularDiferenciaBarras(dto.getFechaEmisionFactura(), dto.getFechaRecibo())));
                if (dto.getFechaCobroCheque() != null) {
                    dto.setDiferenciaFacChq(String.valueOf(DiferenciaEntreFechas.calcularDiferenciaBarras(dto.getFechaEmisionFactura(), dto.getFechaCobroCheque())));
                }
            } catch (ExcepcionesComunes ex) {
                Logger.getLogger(ExpertoImputacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return imputacionesCheque;
    }

    public List<DtoImputacionCheque> obtenerImputacionesChequeVendedor(String codVendedor, String fechaDesde, String fechaHasta) {
        imputacionesCheque = fachadaImputacion.buscarImputacionesChequeVendedor(codVendedor, fechaDesde, fechaHasta);
        for (DtoImputacionCheque dto : imputacionesCheque) {
            try {
                dto.setDiferenciaFacRec(String.valueOf(DiferenciaEntreFechas.calcularDiferenciaBarras(dto.getFechaEmisionFactura(), dto.getFechaRecibo())));
                if (dto.getFechaCobroCheque() != null) {
                    dto.setDiferenciaFacChq(String.valueOf(DiferenciaEntreFechas.calcularDiferenciaBarras(dto.getFechaEmisionFactura(), dto.getFechaCobroCheque())));
                }
            } catch (ExcepcionesComunes ex) {
                Logger.getLogger(ExpertoImputacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return imputacionesCheque;
    }
}
