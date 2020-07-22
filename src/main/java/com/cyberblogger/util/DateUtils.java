package com.cyberblogger.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by foxi.chen on 11/02/20.
 *
 * @author foxi.chen
 */
public class DateUtils {

    public static  String formatDate(Date date, String format)throws ParseException {
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      return sdf.format(date);
    }

    public static Date parseDate(String strDate, String format) throws ParseException{
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      return sdf.parse(strDate);
    }

}
