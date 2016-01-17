package biz.podoliako.carwash.services.utils;


import java.io.PrintWriter;
import java.io.StringWriter;

public class GeneralUtils {

    public static String stackTraceToString(Throwable e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
