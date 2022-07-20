package Logic;


import Logic.Models.Subject;
import Logic.Models.User;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Date {


    public static LocalDateTime currentTime(){
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return now;
        //System.out.println(dtf.format(now));
    }
    public static void setUserEntranceTime(User user){
        LocalDateTime now =currentTime();
        user.setTimeOfLastEntrance(now);
    }

    public static boolean setExamDate(LocalDateTime localDateTime, Subject subject){
        //subject.setExamDate(localDateTime);
        return true;
    }

    public static boolean setClassTime(){

        return true;

    }

}
