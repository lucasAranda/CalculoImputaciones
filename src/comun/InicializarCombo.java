/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package comun;

import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author suamkf
 */
public class InicializarCombo {
    public static DefaultComboBoxModel inicializarCombo(DefaultComboBoxModel boxModel,String primerItem){
        boxModel = new DefaultComboBoxModel();
        boxModel.addElement(primerItem);
        return boxModel;
    }
}
