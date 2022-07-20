package Logic.Requests;

import Logic.Database.CollageDataBase;
import Logic.Database.Functions;
import Logic.Models.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MinorReQuest extends Request {


    String collageIdMinoringTo;
    String collageIdMinoringFrom;
    final public static Double minGPA_Needed=-1.0;

    public MinorReQuest(Student student,Collage collage) {
        super(student);
        this.collageIdMinoringTo=collage.getCollageId();
        this.collageIdMinoringFrom=student.getCollage().getCollageId();
        this.requestSC=RequestSC.minor;
        this.professorId=getProfessorId();
        this.secondProfessorId=getSecondProfessorId();
        if(okGPA()){
            this.stateOfRequest=StateOfRequest.registered;
        }
        else{
            this.stateOfRequest=StateOfRequest.lowGPA;
            log.info("failed because of low GPA");
        }
        log.info("minor request");


    }



    public boolean okGPA(){
        Student student=this.getStudent();
        return student.getGPA()>minGPA_Needed;
    }



    //getters and setters:

    //collage :

    public String getCollageIdMinoringFrom() {
        return collageIdMinoringFrom;
    }

    public String getCollageIdMinoringTo() {
        return collageIdMinoringTo;
    }

    public void setCollageIdMinoringFrom(String collageIdMinoringFrom) {
        this.collageIdMinoringFrom = collageIdMinoringFrom;
    }

    public void setCollageIdMinoringTo(String collageIdMinoringTo) {
        this.collageIdMinoringTo = collageIdMinoringTo;
    }

    public Collage getCollageMinoringFrom() {
        return CollageDataBase.searchById(collageIdMinoringFrom);
    }

    public Collage getCollageMinoringTo() {
        return CollageDataBase.searchById(collageIdMinoringTo);
    }
    public void setCollageMinoringFrom(Collage collageMinoringFrom) {
        this.collageIdMinoringFrom = collageMinoringFrom.getCollageId();
    }

    public void setCollageMinoringTo(Collage collageMinoringTo) {
        this.collageIdMinoringTo = collageMinoringTo.getCollageId();
    }


    @Override
    public String getProfessorId() {
        return getProfessor().getProfessorNumber();
    }

    @Override
    public String getSecondProfessorId() {
        if(getSecondProfessor()!=null){
            return getSecondProfessor().getProfessorNumber();
        }
        else{
            return null;
        }
    }

    @Override
    public Professor getProfessor() {
        Collage collage=getCollageMinoringTo();
        return collage.getEducationViceChair();
    }
    

    @Override
    public Professor getSecondProfessor() {
        Collage collage=getCollageMinoringFrom();
        return collage.getEducationViceChair();
    }




/*
    public MinorReQuest(Student student, Collage collage){
        this.student=student;
        this.collageMinoringTo=collage;
        this.collageMinoringFrom=student.getCollage();
    }


    public void setStateOfRequest() {
        if (student.getGPA()<minGPA_Needed){
            stateOfRequest=StateOfRequest.lowGPA;
        }
        else{
            Professor educationViceChair_collageMinoringTo= collageMinoringTo.getEducationViceChair();
            Professor educationViceChair_collageMinoringFrom=collageMinoringFrom.getEducationViceChair();
            //educationViceChair_collageMinoringFrom.MinorRequests.add(student);//todo
            //educationViceChair_collageMinoringTo.MinorRequests.add(student);
            // stateOfRequest=StateOfRequest.registered;

        }
    }

 */

}
