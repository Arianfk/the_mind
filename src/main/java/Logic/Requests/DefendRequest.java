package Logic.Requests;

import Logic.Database.Functions;
import Logic.Models.Student;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;

public class DefendRequest extends Request{


    String defendDateTime;


    public DefendRequest(Student student){
        super(student);
        this.requestSC=RequestSC.defend;
        this.stateOfRequest=StateOfRequest.registered;
        setDefendTime();
        log.info("defend request");
    }


    public void setDefendTime(){
        LocalDateTime time=LocalDateTime.now();
        LocalDateTime defendTime = time.plusMonths(6);
        this.defendDateTime= Functions.toStringDate(defendTime);
    }

    public String getDefendDateTime() {
        return defendDateTime;
    }
}
