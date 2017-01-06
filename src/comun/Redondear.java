/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package comun;

/**
 *
 * @author suamkf
 */
public class Redondear {
     public static Float redondear (Double numeroAredondear, int cantidadDeDecimales){
        Double retorno = 0.0;
        if (cantidadDeDecimales == 0){
            retorno= Math.rint(numeroAredondear*1)/1;
        } else if (cantidadDeDecimales == 1){
            retorno= Math.rint(numeroAredondear*10)/10;
        }else if (cantidadDeDecimales == 2){
             retorno= Math.rint(numeroAredondear*100)/100;
        }else if (cantidadDeDecimales == 3){
             retorno= Math.rint(numeroAredondear*1000)/1000;
         }
        float retornoFloat = Float.parseFloat(String.valueOf(retorno));
        return retornoFloat;
    }
     
    public static Float redondearFloat (Float numeroAredondear, int cantidadDeDecimales){
        Double retorno = 0.0;
        if (cantidadDeDecimales == 0){
            retorno= Math.rint(numeroAredondear*1)/1;
        } else if (cantidadDeDecimales == 1){
            retorno= Math.rint(numeroAredondear*10)/10;
        }else if (cantidadDeDecimales == 2){
             retorno= Math.rint(numeroAredondear*100)/100;
        }else if (cantidadDeDecimales == 3){
             retorno= Math.rint(numeroAredondear*1000)/1000;
         }
        float retornoFloat = Float.parseFloat(String.valueOf(retorno));
        return retornoFloat;
    }
}
