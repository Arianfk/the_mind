package Logic.Models;

import Logic.Database.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Subject {



    protected String subjectId;
    protected String subjectsNumber;
    public String collageId;
    public String professorId;
    public String termId;
    protected Integer vahed;
    protected Grade grade;
    //public ArrayList<String> studentsId;
    public ArrayList<String> TAsId;
    public String examDateId;
    public ArrayList<String> pishNiazIds;
    public ArrayList<String> hamNiazIds;
    public String schedule;
    public boolean ended;
    public ScoreState scoreState;






    public static Integer first=1;
    public static Integer generateSubjectId(){
        return ++first;

    }

    //constructors

    public Subject(Collage collage){
        this.subjectsNumber=generateSubjectId()+"";
        this.collageId=collage.getCollageId();
        this.grade=Grade.all;
        this.scoreState=ScoreState.no;

        //SubjectDataBase.subjects.add(this);


    }

    public HashMap<Student,Score>  getStudentScoreHashMap(){
        return SubjectDataBase.studentScoreForASubject(this);
    }


    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }


    //getters&setters

    //score state:

    public ScoreState getScoreState() {
        return scoreState;
    }

    public void setScoreState(ScoreState scoreState) {
        this.scoreState = scoreState;
    }
    //name:

    public String getSubjectId() {
        return subjectId;
    }


    //id :


    public String getSubjectsNumber() {
        return subjectsNumber;
    }

    public void setSubjectsNumber(String subjectsNumber) {
        this.subjectsNumber = subjectsNumber;
    }

    //collage:

    public Collage getCollage() {
        return CollageDataBase.searchById(collageId);
    }

    public String getCollageId() {
        return collageId;
    }

    public void setCollageId(String collageId) {
        this.collageId = collageId;
    }

    //niaz:

    public ArrayList<Subject> getPishNiaz() {
        return SubjectDataBase.convertToSubjectArraylistThisStringArraylistOfIds(pishNiazIds);
    }

    public ArrayList<Subject> getHamNiaz() {
        return SubjectDataBase.convertToSubjectArraylistThisStringArraylistOfIds(hamNiazIds);
    }

    public ArrayList<String> getHamNiazIds() {
        return hamNiazIds;
    }

    public ArrayList<String> getPishNiazIds() {
        return pishNiazIds;
    }

    public void setPishNiazIds(ArrayList<String> pishNiazIds) {
        this.pishNiazIds = pishNiazIds;
    }

    public void setHamNiazIds(ArrayList<String> hamNiazIds) {
        this.hamNiazIds = hamNiazIds;
    }

    //professor:

    public Professor getProfessor() {
        return ProfessorDataBase.searchById(professorId);
    }

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }
    //vahed:

    public Integer getVahed() {
        return vahed;
    }

    public void setVahed(Integer vahed) {
        this.vahed = vahed;
    }

    //grade:

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    //term :

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    //students:

    public ArrayList<Student> getStudents() {
        ArrayList<Student> studentsHaveTheSubject=new ArrayList<>();
        for(Student student: StudentDataBase.getStudents()){
            ArrayList<Subject> subjectArrayList=student.getSubjects();
            if(subjectArrayList!=null && subjectArrayList.contains(this)){

                studentsHaveTheSubject.add(student);
            }
        }
        return studentsHaveTheSubject;


        //return StudentDataBase.haveThisSubject(this);
    }

    //TA :


    public ArrayList<Student> getTAs() {
        return StudentDataBase.convertToStudentArraylistThisStringArraylistOfIds(TAsId);
    }

    public ArrayList<String> getTAsId() {
        return TAsId;
    }

    public void setTAsId(ArrayList<String> TAsId) {
        this.TAsId = TAsId;
    }


    // exam date:

    public LocalDateTime getExamDate() {
        return Functions.toLocalDateTimeDate(examDateId);
    }

    public String getExamDateId() {
        return examDateId;
    }

    public void setExamDateId(String examDateId) {
        this.examDateId = examDateId;
    }

    public void setExamDate(LocalDateTime examDate) {
        this.examDateId=Functions.toStringDate(examDate);
    }


    //ended

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public boolean isEnded() {
        return ended;
    }

    //moreInfo

    public Double GPA(){
        return SubjectDataBase.calculateGPA(this);
    }

    public Double GPANoConsideringFailedStudents(){
        return SubjectDataBase.calculateGPAForNotFailedStudents(this);
    }

    public Integer numberOfPassedStudents(){
        return SubjectDataBase.numberOfPassedStudents(this);
    }

    public Integer numberOfFailedStudents(){
        return SubjectDataBase.numberOfFailedStudents(this);
    }


}

