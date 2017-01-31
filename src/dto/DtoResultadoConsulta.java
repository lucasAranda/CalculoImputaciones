/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dto;

import java.util.List;

/**
 *
 * @author maquina0
 */
public class DtoResultadoConsulta {
    
    private Double total;
    private List<DtoImputacionCheque> imputacionesCheque;

    /**
     * Get the value of imputacionesCheque
     *
     * @return the value of imputacionesCheque
     */
    public List<DtoImputacionCheque> getImputacionesCheque() {
        return imputacionesCheque;
    }

    /**
     * Set the value of imputacionesCheque
     *
     * @param imputacionesCheque new value of imputacionesCheque
     */
    public void setImputacionesCheque(List<DtoImputacionCheque> imputacionesCheque) {
        this.imputacionesCheque = imputacionesCheque;
    }
    

    /**
     * Get the value of total
     *
     * @return the value of total
     */
    public Double getTotal() {
        return total;
    }

    /**
     * Set the value of total
     *
     * @param total new value of total
     */
    public void setTotal(Double total) {
        this.total = total;
    }
}
