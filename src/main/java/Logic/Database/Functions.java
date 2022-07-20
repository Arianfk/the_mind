package Logic.Database;

import Logic.Models.Subject;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Functions {


    public static String toStringSubject(ArrayList<Subject> niaz){
        if(niaz==null){
            return "";
        }
        String out="";
        for(Subject x: niaz){
            out+=x.getSubjectsNumber()+"";
        }
        return out;
    }


    public static String toStringDayOfWeek(ArrayList<DayOfWeek> dayOfWeeks){
        String out="";
        for(DayOfWeek x: dayOfWeeks){
            out+=x.name();
        }
        return out;
    }

    public static String toStringDate (LocalDateTime localDate){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH");
        return localDate.format(dtf);
    }

    public static LocalDateTime toLocalDateTimeDate(String date){
        if(date==null){
            return LocalDateTime.MIN;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH");
        return LocalDateTime.parse(date,dtf);
    }


    public static String currentTime (){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd HH");
        String out= LocalDateTime.now().format(dtf);
        return out;

    }






}
