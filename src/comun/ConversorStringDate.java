/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comun;

import excepciones.ExcepcionesComunes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maquina0
 */
public class ConversorStringDate {

    public static Date convertirCadenaAFecha(String fecha) throws ExcepcionesComunes {
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaConvertida = null;
        if (fecha == null) {
            fechaConvertida = new Date();
            return fechaConvertida;
        } else {
            try {
                fechaConvertida = formatoDeFecha.parse(fecha);
            } catch (ParseException ex) {
                return convertirCadenaAHora(fecha);//prueba si es sólo hora
            }
            return fechaConvertida;
        }
    }

    public static Date convertirCadenaAFechaBarra(String fecha) throws ExcepcionesComunes {
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaConvertida = null;
        if (fecha == null) {
            fechaConvertida = new Date();
            return fechaConvertida;
        } else {
            try {
                fechaConvertida = formatoDeFecha.parse(fecha);
            } catch (ParseException ex) {
                Logger.getLogger(ConversorStringDate.class.getName()).log(Level.SEVERE, null, ex);
            }
            return fechaConvertida;
        }
    }

    public static Date convertirCadenaAFechaHora(String fechaYHora) throws ExcepcionesComunes {
        SimpleDateFormat formatoDeFechaYHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date fechaYHoraConvertida = null;
        if (fechaYHora == null) {
            fechaYHoraConvertida = new Date();
            return fechaYHoraConvertida;
        } else {
            try {
                fechaYHoraConvertida = formatoDeFechaYHora.parse(fechaYHora);
            } catch (ParseException ex) {
                return convertirCadenaAFecha(fechaYHora);//prueba si es sólo fecha
            }
            return fechaYHoraConvertida;
        }
    }

    public static Date convertirCadenaAHora(String Hora) throws ExcepcionesComunes {
        SimpleDateFormat formatoDeFechaYHora = new SimpleDateFormat("HH:mm:ss");
        Date fechaYHoraConvertida = null;
        if (Hora == null) {
            fechaYHoraConvertida = new Date();
            return fechaYHoraConvertida;
        } else {
            try {
                fechaYHoraConvertida = formatoDeFechaYHora.parse(Hora);
            } catch (ParseException ex) {
                throw new ExcepcionesComunes(ExcepcionesComunes.FORMATO_FECHA_INVALIDO);
            }
            return fechaYHoraConvertida;
        }
    }

    public static int diferenciaEnMinutos(Date fecha1, Date fecha2) {
        int diferencia = 0;
        Calendar fechaInicio = Calendar.getInstance();
        Calendar fechaFin = Calendar.getInstance();
        fechaInicio.setTime(fecha1);
        fechaFin.setTime(fecha2);
        diferencia = (fechaFin.get(Calendar.MINUTE) - fechaInicio.get(Calendar.MINUTE));
        return diferencia;
    }

    public static String convertirFechaHoraAString(Date fechaYHora) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String fecha;
        if (fechaYHora != null) {
            fecha = formato.format(fechaYHora);
        } else {
            fecha = "";
        }

        return fecha;
    }

    public static String convertirFechaAString(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fechaString;
        if (fecha != null) {
            fechaString = formato.format(fecha);
        } else {
            fechaString = "";
        }

        return fechaString;
    }

    public static String convertirHoraAString(Date hora) {
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
        String fecha = formato.format(hora);
        return fecha;
    }

    public static String convertirFechaAStringDesdeAnio(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = formato.format(fecha);
        return fechaFormateada;
    }

    public static String formatearFecha(String fecha) {
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaConvertida = null;
        if (fecha == null) {
            fechaConvertida = new Date();
            formatoDeFecha = new SimpleDateFormat("dd-MM");
            return formatoDeFecha.format(fechaConvertida);
        } else {
            try {
                fechaConvertida = formatoDeFecha.parse(fecha);
                formatoDeFecha = new SimpleDateFormat("dd-MM");
                return formatoDeFecha.format(fechaConvertida);
            } catch (ParseException ex) {
                return "";
            }
        }
    }
}
