package Logic.Models;

import Logic.Database.Functions;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.temporal.WeekFields;
import java.util.ArrayList;

public class Schedule {



    public Time fromTime;
    public Time toTime;
    public ArrayList<DayOfWeek> dayOfWeeks;
    //public String scheduleId;




    @Override
    public String toString() {
        return "Schedule{" +
                "fromTime=" + fromTime +
                ", toTime=" + toTime +
                ", dayOfWeeks=" + Functions.toStringDayOfWeek(dayOfWeeks) +
                '}';
    }


}
