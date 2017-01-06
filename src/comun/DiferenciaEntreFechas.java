/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comun;

import excepciones.ExcepcionesComunes;
import java.util.Date;

/**
 *
 * @author maquina0
 */
public class DiferenciaEntreFechas {

    public static int calcularDiferencia(String fechaDesde, String fechaHasta) throws ExcepcionesComunes {

        int retorno = 1;
        Date desde = ConversorStringDate.convertirCadenaAFecha(fechaDesde);
        Date hasta = ConversorStringDate.convertirCadenaAFecha(fechaHasta);

        Long resultado = ((desde.getTime() - hasta.getTime()) / 86400000);

        if (resultado > 0) {

            retorno = Integer.parseInt(String.valueOf(resultado));
        }
        return retorno;
    }

    public static int calcularDiferenciaBarras(String fechaDesde, String fechaHasta) throws ExcepcionesComunes {

        int retorno;
        Date desde = ConversorStringDate.convertirCadenaAFechaBarra(fechaDesde);
        Date hasta = ConversorStringDate.convertirCadenaAFechaBarra(fechaHasta);

        Long resultado = ((hasta.getTime() - desde.getTime()) / 86400000);

        retorno = Integer.parseInt(String.valueOf(resultado));

        return retorno;
    }

    public static int calcularDiferenciaMinutos(Date desde, Date hasta) {

        int retorno = 0;
        Long resultado = ((hasta.getTime() - desde.getTime()) / 60000);

        if (resultado >= 0) {
            retorno = resultado.intValue();
        }
        return retorno;
    }

    public static int calcularDiferenciaReal(String fechaDesde, String fechaHasta) throws ExcepcionesComunes {
        int retorno = 0;
        Date desde = ConversorStringDate.convertirCadenaAFecha(fechaDesde);
        Date hasta = ConversorStringDate.convertirCadenaAFecha(fechaHasta);

        Long resultado = ((desde.getTime() - hasta.getTime()) / 86400000);

        retorno = Integer.parseInt(String.valueOf(resultado));

        return Math.abs(retorno);
    }
}
