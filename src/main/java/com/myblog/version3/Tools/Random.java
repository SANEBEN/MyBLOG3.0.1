package com.myblog.version3.Tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Random {
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    public static String getTimeFormat1(Date date){
        SimpleDateFormat format = new SimpleDateFormat("y-M-d h:m:s a E");
        return format.format(date);
    }

    public static String getTimeFormt2(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd hh:mm:ss a E");
        return format.format(date);
    }

    public static String getTimeFormat3(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(date);
    }

    public static String forArticle(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_hh.mm.ss");
        return format.format(date);
    }
}
