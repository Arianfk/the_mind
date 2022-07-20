package Logic.Requests;

import Logic.Database.ProfessorDataBase;
import Logic.Database.RequestDataBase;
import Logic.Database.StudentDataBase;
import Logic.Models.Professor;
import Logic.Models.Student;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Request {

    public static Logger log= LogManager.getLogger(Request.class);

    public String requestId;
    public String professorId;
    public String secondProfessorId;
    public String studentId;
    protected StateOfRequest stateOfRequest;

    public RequestSC requestSC;

    public RequestSC getRequestSC() {
        return requestSC;
    }

    public static Integer getLastRequestId() {
        return lastRequestId;
    }

    public void setRequestSC(RequestSC requestSC) {
        this.requestSC = requestSC;
    }

    public static void setLastRequestId(Integer lastRequestId) {
        Request.lastRequestId = lastRequestId;
    }

    public Request(Student student){
        this.studentId=student.getStudentsNumber();
        this.requestId=generateRequestId();
        student.addRequestId(requestId);
        RequestDataBase.getRequests().add(this);
        //RequestDataBase.requests.add(this);
    }


    public static Integer lastRequestId=1;
    public static String generateRequestId(){
        lastRequestId++;
        return lastRequestId+"";
    }



    //getters and setters:

    //requestId

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    //state of request:

    public void setStateOfRequest(StateOfRequest stateOfRequest) {
        this.stateOfRequest = stateOfRequest;
    }

    public StateOfRequest getStateOfRequest() {
        return stateOfRequest;
    }

    // student:

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Student getStudent() {
        return StudentDataBase.searchByStudentNumber(studentId);
    }

    //professor:

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }

    public Professor getProfessor() {
        return ProfessorDataBase.searchById(professorId);
    }

    public String getSecondProfessorId() {
        if (secondProfessorId==null){
            secondProfessorId="";
        }
        return secondProfessorId;
    }

    public void setSecondProfessorId(String secondProfessorId) {
        this.secondProfessorId = secondProfessorId;
    }
    public Professor getSecondProfessor() {
        return ProfessorDataBase.searchById(getSecondProfessorId());
    }

    public void acceptRequest(){
        stateOfRequest=StateOfRequest.accepted;
    }



}
