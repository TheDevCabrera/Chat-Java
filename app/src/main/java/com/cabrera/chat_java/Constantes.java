package com.cabrera.chat_java;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;
import java.util.zip.DataFormatException;

public class Constantes {
    public static long obneterTiempoD() { return System.currentTimeMillis(); }

    public static String formatoFecha( Long tiempo){
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(tiempo);

        String fecha = DateFormat.format("dd/MM/yyyy", calendar).toString();
        return fecha;
    }
}
