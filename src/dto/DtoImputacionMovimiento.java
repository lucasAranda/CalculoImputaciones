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
public class DtoImputacionMovimiento extends DtoImputacion{

    private String monvimiento;
    private String montoMovimiento;

    public DtoImputacionMovimiento() {
        super();
    }

    /**
     * Get the value of montoMovimiento
     *
     * @return the value of montoMovimiento
     */
    public String getMontoMovimiento() {
        return montoMovimiento;
    }

    /**
     * Set the value of montoMovimiento
     *
     * @param montoMovimiento new value of montoMovimiento
     */
    public void setMontoMovimiento(String montoMovimiento) {
        this.montoMovimiento = montoMovimiento;
    }

    /**
     * Get the value of monvimiento
     *
     * @return the value of monvimiento
     */
    public String getMonvimiento() {
        return monvimiento;
    }

    /**
     * Set the value of monvimiento
     *
     * @param monvimiento new value of monvimiento
     */
    public void setMonvimiento(String monvimiento) {
        this.monvimiento = monvimiento;
    }
}
