/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dto;

/**
 *
 * @author maquina0
 */
public class DtoVendedor {
    
    private String codigoVendedor;
    private String nombreVendedor;

    public DtoVendedor() {
    }
    
    /**
     * Get the value of nombreVendedor
     *
     * @return the value of nombreVendedor
     */
    public String getNombreVendedor() {
        return nombreVendedor;
    }

    /**
     * Set the value of nombreVendedor
     *
     * @param nombreVendedor new value of nombreVendedor
     */
    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }


    /**
     * Get the value of codigoVendedor
     *
     * @return the value of codigoVendedor
     */
    public String getCodigoVendedor() {
        return codigoVendedor;
    }

    /**
     * Set the value of codigoVendedor
     *
     * @param codigoVendedor new value of codigoVendedor
     */
    public void setCodigoVendedor(String codigoVendedor) {
        this.codigoVendedor = codigoVendedor;
    }

}
