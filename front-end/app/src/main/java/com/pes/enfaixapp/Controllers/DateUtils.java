package com.pes.enfaixapp.Controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by carlos on 10/12/2016.
 */

public final class DateUtils {

    //"E, dd MMM yyyy HH:mm:ss ZZZZ" to "dd/MM/YYYY"
    public static final String parseStringCompleteToStringSimple(String dateComplet) throws Exception {

        String cutDate = dateComplet.substring(5, 25);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
        try {
            cal.setTime(formatter.parse(cutDate));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new Exception("Internal Error: Converter Calendar to String");
        }
        return getSimpleString(cal);
    }

    // dd/MM/YYYY
    public static final String getSimpleString(Calendar cal) {
        String d, m, y;
        Integer year = cal.get(Calendar.YEAR);
        y = year.toString();
        Integer month = cal.get(Calendar.MONTH)+1;
        if(month < 10) m = month.toString();
        else m = "0" + month.toString();
        Integer day = cal.get(Calendar.DAY_OF_MONTH);
        if(day < 10) d = day.toString();
        else d = "0" + day.toString();
        String date =  d + "/" + m + "/" + y;
        return  date;
    }

    // YYYY/MM/dd
    public static final String getSimpleStringDB(Calendar cal) {

        Integer year = cal.get(Calendar.YEAR);
        Integer month = cal.get(Calendar.MONTH);
        Integer day = cal.get(Calendar.DAY_OF_MONTH);
        StringBuilder date = new StringBuilder().append(year).append("/").append(month+1).append("/").append(day);
        return String.valueOf(date);
    }
}
