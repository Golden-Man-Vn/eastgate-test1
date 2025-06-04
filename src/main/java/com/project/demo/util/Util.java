package com.project.demo.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
    public static Timestamp from(String s, String formatter){
        Timestamp ret = null;
        try {
            LocalDateTime dateTime = LocalDateTime.parse(s, DateTimeFormatter.ofPattern(formatter));
            ret = Timestamp.valueOf(dateTime);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return ret;
    }

    public static Integer parse(String s){
        Integer ret = null;
        try {
            ret = Integer.parseInt(s);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return ret;
    }
}
