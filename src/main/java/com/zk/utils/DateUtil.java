package com.zk.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private DateUtil() {}

    public static String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhMMss");
        return sdf.format(date);
    }

}

