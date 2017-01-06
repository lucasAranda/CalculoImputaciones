/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package comun;

import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author suamkf
 */
public class MostrarMensaje {
    public static void mostrarMensaje (String mensaje, List <JFrame> pantallas){
        Mensaje mensaje1 = new Mensaje(pantallas);
        mensaje1.setMensaje(mensaje);
        mensaje1.setVisible(true);
    }
}
