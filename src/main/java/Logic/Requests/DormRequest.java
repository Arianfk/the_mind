package Logic.Requests;

import Logic.Models.Student;

public class DormRequest extends Request{


    public DormRequest(Student student){
        super(student);
        this.requestSC=RequestSC.dorm;
        setStateOfRequest();
        log.info("dorm request");
    }


    public void setStateOfRequest(){
        if(randomNumber()){
            this.stateOfRequest=StateOfRequest.accepted;
        }
        else{
            this.stateOfRequest=StateOfRequest.failed;
        }
    }


    public static boolean randomNumber(){
        double random=Math.random();
        return random > 0.5;
    }



}
