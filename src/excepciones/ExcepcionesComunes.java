/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 *
 * @author HCP03
 */
public class ExcepcionesComunes extends Exception{
    public static String FORMATO_FECHA_INVALIDO= "Formato de fecha inválido";
    public ExcepcionesComunes(){
        
    }
    
    public ExcepcionesComunes(String msj){
        super(msj);
    }
    
}
