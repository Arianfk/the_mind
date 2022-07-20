package Logic.Models;

import Logic.Database.*;
import Logic.Requests.MinorReQuest;
import Logic.Requests.QuitRequest;
import Logic.Requests.Request;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Student extends User{

    public static Logger log= LogManager.getLogger(Student.class);


    public ArrayList<String> subjectsAsTaId;
    public HashMap<String,String> subjectScoreHashMapId=new HashMap<>();
    protected String studentId;
    protected Double GPA;
    public String supervisorId;
    protected Integer yearOfEntrance;
    protected Grade grade;
    protected State state;
    public String satSabtNameString;
    protected MojavesTahsili vasiatTahsili;
    protected VasietSabtName vasietSabtName;
    public ArrayList<String> requestIds;


    //getters and setters :

    // student number:


    public String getStudentId() {
        return studentId;
    }

    // GPA :
    public Double getGPA() {
        GPA=StudentDataBase.calculateGPA(this);
        return GPA;
    }

    public void setGPA(Double GPA) {
        this.GPA = GPA;
    }

    // supervisor:

    public Professor getSupervisor() {
        return ProfessorDataBase.searchById(supervisorId);
    }

    public String getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
    }



    //tahsil info


    public void setVasietSabtName(VasietSabtName vasietSabtName) {
        this.vasietSabtName = vasietSabtName;
    }

    public VasietSabtName getVasietSabtName() {
        return vasietSabtName;
    }

    public void setVasiatTahsili(MojavesTahsili vasiatTahsili) {
        this.vasiatTahsili = vasiatTahsili;
    }

    public MojavesTahsili getVasiatTahsili() {
        return vasiatTahsili;
    }


    public Integer getYearOfEntrance() {
        return yearOfEntrance;
    }

    public void setYearOfEntrance(Integer yearOfEntrance) {
        this.yearOfEntrance = yearOfEntrance;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getSatSabtNameString() {
        return satSabtNameString;
    }
    public LocalDateTime getSatSabtName() {
        return Functions.toLocalDateTimeDate(satSabtNameString);
    }

    public void setSatSabtNameString(String satSabtNameString) {
        this.satSabtNameString = satSabtNameString;
    }

    public void setSatSabtNameString(LocalDateTime localDateTime) {
        this.satSabtNameString = Functions.toStringDate(localDateTime);
    }




    //vaheds:

    public Integer getPassedVaheds(){
        return StudentDataBase.passedVaheds(this);
    }


    //subjects as Ta:

    public ArrayList<String> getSubjectsAsTaId() {
        return subjectsAsTaId;
    }

    public void setSubjectsAsTaId(ArrayList<String> subjectsAsTaId) {
        this.subjectsAsTaId = subjectsAsTaId;
    }

    public ArrayList<Subject> getSubjectsAsTa() {
        return SubjectDataBase.convertToSubjectArraylistThisStringArraylistOfIds(subjectsAsTaId);
    }

    //subjects as student

    public HashMap<String, String> getSubjectScoreHashMapId() {
        if(subjectScoreHashMapId==null){
            subjectScoreHashMapId=new HashMap<>();
        }
        return subjectScoreHashMapId;
    }


    public HashMap<Subject, Score> getSubjectScoreHashMap() {
         HashMap<Subject,Score> subjectScoreHashMap=new HashMap<>();
         Set<String> subjectIds=this.getSubjectScoreHashMapId().keySet();
         for(String subjectId: subjectIds){
             Subject subject=SubjectDataBase.searchByNumber(subjectId);
             String scoreId=subjectScoreHashMapId.get(subjectId);
             Score score=ScoreDataBase.searchByScoreId(scoreId);
             subjectScoreHashMap.put(subject,score);
         }
         return subjectScoreHashMap;
    }



    public void setSubjectScoreHashMapId(HashMap<Subject,Score> subjectScoreHashMap){
        HashMap<String,String> subjectScoreHashMapIds=new HashMap<>();
        Set<Subject> subjects=subjectScoreHashMap.keySet();
        for(Subject subject: subjects){
            String subjectId=subject.getSubjectsNumber();
            Score score=subjectScoreHashMap.get(subject);
            String scoreId=score.getScoreId();
            subjectScoreHashMapIds.put(subjectId,scoreId);
        }
        subjectScoreHashMapId=subjectScoreHashMapIds;
    }

    //requests :

    public ArrayList<String> getRequestIds() {
        if(requestIds==null){
            requestIds=new ArrayList<>();
        }
        return requestIds;
    }

    public void setRequestIds(ArrayList<String> requestIds) {
        this.requestIds = requestIds;
    }

    public ArrayList<Request> getRequests() {
        return RequestDataBase.convertToRequestArraylist(requestIds);
    }

    public void addRequestId(String id){
        if(requestIds==null){
            requestIds=new ArrayList<>();
        }
        requestIds.add(id);
    }



    //todo subjects :


    public ArrayList<Subject> getSubjects(){
        ArrayList<Subject> subjects=new ArrayList<>();
        if(subjectScoreHashMapId==null){
            subjectScoreHashMapId=new HashMap<>();
        }
        for(String subjectId : subjectScoreHashMapId.keySet()){
            Subject subject=SubjectDataBase.searchByNumber(subjectId);
            subjects.add(subject);
        }
        return subjects;
    }

    @Override
    public ArrayList<Subject>  getCurrentTermSubjects(){
        return SubjectDataBase.subjectsThisTerm(this);
    }




    //subject scores :


    public HashMap<Subject,Score> getSubjectsWithTemporaryScore(){
        return StudentDataBase.temporaryScores(this);
    }

    public Score getScoreForSubject(Subject subject){
        String subjectId=subject.getSubjectsNumber();
        String scoreId=subjectScoreHashMapId.get(subjectId);
        return ScoreDataBase.searchByScoreId(scoreId);
    }



    public static Integer first=1;
    public static Integer generateStudentId(){
        return ++first;

    }
/*
    public Student(Collage collage){
        this.usersCollageId=collage.getCollageId();
        this.studentsNumber=generateStudentId()+"";
        StudentDataBase.students.add(this);
        this.username=studentsNumber;
        //this.password="1234";
        this.vasietSabtName=VasietSabtName.namoshakhas;
        this.vasiatTahsili=MojavesTahsili.Namoshkhas;
        this.satSabtNameString="-";
        this.supervisorId="-";


    }

 */
    public Student(String username,String password){
        this.username=username;
        this.password=password;
    }
/*
    @Override
    public ArrayList<String> getProfile() {
        ArrayList<String> data=super.getProfile();
        String studentNumber="student Number: "+this.getStudentsNumber();
        String GPA="GPA: "+this.getGPA()+"";
        data.add(studentNumber);
        data.add(GPA);
        Professor supervisorProfessor=this.getSupervisor();
        try {
            String supervisor="supervisor: "+supervisorProfessor.getFullName();
            data.add(supervisor);
        }catch (Exception e){
            log.warn("null supervisor");
            data.add("supervisor: null");
        }

        String yearOfEntrance ="yearOfEntrance: "+this.getYearOfEntrance()+"";
        String grade="grade: "+this.getGrade().name();
        String state="state: "+this.getState().name();
        data.add(yearOfEntrance);
        data.add(grade);
        data.add(state);
        return data;

    }

 */


    /*
    public void setSatSabtName(LocalDateTime satSabtName) {
        this.satSabtName = satSabtName;
    }

    public LocalDateTime getSatSabtName() {
        return satSabtName;
    }


 */




}
