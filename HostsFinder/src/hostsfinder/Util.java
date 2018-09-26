/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hostsfinder;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Clecio
 */
public class Util {
    public static String getHora(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date hora = (Date) Calendar.getInstance().getTime(); // Ou qualquer outra forma que tem
        String dataFormatada = sdf.format(hora);
        return dataFormatada;
    }
}
