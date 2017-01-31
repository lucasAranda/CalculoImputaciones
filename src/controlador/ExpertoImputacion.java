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
import dto.DtoResultadoConsulta;
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
    private List<String> empresas = new ArrayList<>();
    private List<DtoVendedor> vendedores = new ArrayList<>();
    private List<DtoImputacionMovimiento> imputacionesMovimiento = new ArrayList<>();
    private List<DtoImputacionCheque> imputacionesCheque = new ArrayList<>();
    private Double total;

    public ExpertoImputacion() {
        fachadaImputacion = new FachadaImputacion();
    }

    public List<String> buscarBasesDatos() {
        empresas = fachadaImputacion.buscarBasesDatos();
        return empresas;
    }

    public List<DtoVendedor> obtenerVendedores(String empresa) {
        if (fachadaImputacion.seleccionarEmpresa(empresa)) {
            vendedores = fachadaImputacion.obtenerVendedores();
        }
        System.out.println("Cantidad: " + vendedores.size());
        return vendedores;
    }

    public List<DtoVendedor> obtenerVendedores() {
        if (fachadaImputacion.seleccionarEmpresa("DESARROLLADORA_DEL_ESTE")) {
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

    public DtoResultadoConsulta obtenerImputacionesCheque(String fechaDesde, String fechaHasta) {
        DtoResultadoConsulta resultado = new DtoResultadoConsulta();
        List<DtoImputacionCheque> impChqAux;
        imputacionesCheque = new ArrayList<>();
        total = 0.0;
        for (String empresa : buscarBasesDatos()) {
            fachadaImputacion.seleccionarEmpresa(empresa);
            impChqAux = fachadaImputacion.buscarImputacionesCheque(fechaDesde, fechaHasta);
            for (DtoImputacionCheque dto : impChqAux) {
                dto.setEmpresa(empresa);
            }
            imputacionesCheque.addAll(impChqAux);
            total += fachadaImputacion.buscarTotalCancelado(fechaDesde, fechaHasta);
        }
        for (DtoImputacionCheque dto : imputacionesCheque) {
            try {
                dto.setDiferenciaFacRec(String.valueOf(DiferenciaEntreFechas.calcularDiferenciaBarras(dto.getFechaEmisionFactura(), dto.getFechaRecibo())));
                dto.setPonderacionDifFacRec(String.valueOf(calcularPonderacionDifFechas(total, Double.valueOf(dto.getImporteFactura()), Integer.valueOf(dto.getDiferenciaFacRec()))));
                
                if (dto.getFechaCobroCheque() != null) {
                    dto.setDiferenciaFacChq(String.valueOf(DiferenciaEntreFechas.calcularDiferenciaBarras(dto.getFechaEmisionFactura(), dto.getFechaCobroCheque())));
                    dto.setPonderacionDifFacChq(String.valueOf(calcularPonderacionDifFechas(total, Double.valueOf(dto.getImporteCheque()), Integer.valueOf(dto.getDiferenciaFacChq()))));
                } else {
                    dto.setDiferenciaFacChq(dto.getDiferenciaFacRec());
                    dto.setPonderacionDifFacChq(dto.getPonderacionDifFacRec());
                }
            } catch (ExcepcionesComunes ex) {
                Logger.getLogger(ExpertoImputacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        resultado.setTotal(total);
        resultado.setImputacionesCheque(imputacionesCheque);
        return resultado;
    }

    public DtoResultadoConsulta obtenerImputacionesChequeVendedor(String codVendedor, String fechaDesde, String fechaHasta) {
        DtoResultadoConsulta resultado = new DtoResultadoConsulta();
        List<DtoImputacionCheque> impChqAux;
        imputacionesCheque = new ArrayList<>();
        total = 0.0;
        for (String empresa : buscarBasesDatos()) {
            System.out.println(empresa);
            fachadaImputacion.seleccionarEmpresa(empresa);
            impChqAux = fachadaImputacion.buscarImputacionesChequeVendedor(codVendedor, fechaDesde, fechaHasta);
            for (DtoImputacionCheque dto : impChqAux) {
                dto.setEmpresa(empresa);
            }
            imputacionesCheque.addAll(impChqAux);
            total += fachadaImputacion.buscarTotalCanceladoVendedor(codVendedor, fechaDesde, fechaHasta);
        }
        for (DtoImputacionCheque dto : imputacionesCheque) {
            try {
                dto.setDiferenciaFacRec(String.valueOf(DiferenciaEntreFechas.calcularDiferenciaBarras(dto.getFechaEmisionFactura(), dto.getFechaRecibo())));
                dto.setPonderacionDifFacRec(String.valueOf(calcularPonderacionDifFechas(total, Double.valueOf(dto.getImporteFactura()), Integer.valueOf(dto.getDiferenciaFacRec()))));
                
                if (dto.getFechaCobroCheque() != null) {
                    dto.setDiferenciaFacChq(String.valueOf(DiferenciaEntreFechas.calcularDiferenciaBarras(dto.getFechaEmisionFactura(), dto.getFechaCobroCheque())));
                    dto.setPonderacionDifFacChq(String.valueOf(calcularPonderacionDifFechas(total, Double.valueOf(dto.getImporteCheque()), Integer.valueOf(dto.getDiferenciaFacChq()))));
                } else {
                    dto.setDiferenciaFacChq(dto.getDiferenciaFacRec());
                    dto.setPonderacionDifFacChq(dto.getPonderacionDifFacRec());
                }
            } catch (ExcepcionesComunes ex) {
                Logger.getLogger(ExpertoImputacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        resultado.setTotal(total);
        resultado.setImputacionesCheque(imputacionesCheque);
        return resultado;
    }
    
    public Double calcularPonderacionDifFechas(double importeTotal, double importe, int diferencia){
        return (importe/importeTotal) * diferencia;
    }
}
