package lv.rtu.domain;

import java.sql.Timestamp;
import java.util.Date;

public class NameGenerator {

    public static String getName(){
        Date date = new java.util.Date();
        String time = new Timestamp(date.getTime()).toString();
        return(time.replaceAll("[^A-Za-z0-9 ]+", "_"));
    }

}
