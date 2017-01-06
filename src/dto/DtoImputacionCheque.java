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
public class DtoImputacionCheque extends DtoImputacion{
    
    private String nroCheque;
    private String fechaEmisCheque;
    private String fechaCobroCheque;
    private String importeCheque;
    private String diferenciaFacChq;

    public DtoImputacionCheque() {
        super();
    }

    /**
     * Get the value of diferenciaFacChq
     *
     * @return the value of diferenciaFacChq
     */
    public String getDiferenciaFacChq() {
        return diferenciaFacChq;
    }

    /**
     * Set the value of diferenciaFacChq
     *
     * @param diferenciaFacChq new value of diferenciaFacChq
     */
    public void setDiferenciaFacChq(String diferenciaFacChq) {
        this.diferenciaFacChq = diferenciaFacChq;
    }

    /**
     * Get the value of importeCheque
     *
     * @return the value of importeCheque
     */
    public String getImporteCheque() {
        return importeCheque;
    }

    /**
     * Set the value of importeCheque
     *
     * @param importeCheque new value of importeCheque
     */
    public void setImporteCheque(String importeCheque) {
        this.importeCheque = importeCheque;
    }

    /**
     * Get the value of fechaCobroCheque
     *
     * @return the value of fechaCobroCheque
     */
    public String getFechaCobroCheque() {
        return fechaCobroCheque;
    }

    /**
     * Set the value of fechaCobroCheque
     *
     * @param fechaCobroCheque new value of fechaCobroCheque
     */
    public void setFechaCobroCheque(String fechaCobroCheque) {
        this.fechaCobroCheque = fechaCobroCheque;
    }

    /**
     * Get the value of fechaEmisCheque
     *
     * @return the value of fechaEmisCheque
     */
    public String getFechaEmisCheque() {
        return fechaEmisCheque;
    }

    /**
     * Set the value of fechaEmisCheque
     *
     * @param fechaEmisCheque new value of fechaEmisCheque
     */
    public void setFechaEmisCheque(String fechaEmisCheque) {
        this.fechaEmisCheque = fechaEmisCheque;
    }

    /**
     * Get the value of nroCheque
     *
     * @return the value of nroCheque
     */
    public String getNroCheque() {
        return nroCheque;
    }

    /**
     * Set the value of nroCheque
     *
     * @param nroCheque new value of nroCheque
     */
    public void setNroCheque(String nroCheque) {
        this.nroCheque = nroCheque;
    }
}
