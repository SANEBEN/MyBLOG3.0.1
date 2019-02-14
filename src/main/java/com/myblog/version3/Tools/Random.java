package com.myblog.version3.Tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Random {
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    public static String getTimeFormat1(){
        SimpleDateFormat format = new SimpleDateFormat("y-M-d h:m:s a E");
        return format.format(new Date());
    }

    public static String getTimeFormt2(){
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd hh:mm:ss a E");
        return format.format(new Date());
    }

    public static String getTimeFormat3(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(new Date());
    }
}
