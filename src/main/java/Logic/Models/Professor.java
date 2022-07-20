package Logic.Models;


import Logic.Database.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Professor extends User {


    public static Logger log= LogManager.getLogger(Professor.class);

    protected Degree degree;
    protected boolean supervisor;
    protected Integer roomNumber;
    protected ProfessorRole role;
    protected String professorId;


    public static Integer first=1;
    public String generateProfessorId(){
        first++;
        Integer year= LocalDateTime.now().getYear();
        return year+this.usersCollageId+first;

    }
    public Professor(Collage collage) throws NoSuchAlgorithmException {
        this.usersCollageId=collage.getCollageId();
        this.professorId=generateProfessorId();
        ProfessorDataBase.professors.add(this);
        this.username=professorId;
        //this.password=UserDatabase.hash("1234");
        this.role=ProfessorRole.normal;
        this.degree=Degree.daneshyar;

    }
    //public ArrayList<String> currentStudentWhoSheIsTheirSupervisorIds;
    //public ArrayList<String> subjectsPresentedByThisProfessorIds;


    //getters and setters:


    //degree:

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    //supervisor:

    public boolean isSupervisor() {
        return supervisor;
    }

    public void setSupervisor(boolean supervisor) {
        this.supervisor = supervisor;
    }

    //room number:

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }


    //professorNumber

    public String getProfessorId() {
        return professorId;
    }


    //role

    public ProfessorRole getRole() {
        return role;
    }

    public boolean setRole(ProfessorRole role) {
        if(role.equals(ProfessorRole.DepartmentChair)){
            log.error("can't set the role to DepartmentChair");
            return false;
        }
        else if(role.equals(ProfessorRole.educationalViceChair)){
            Collage c = this.getCollage();
            if(c.getEducationViceChair()==null){
                this.role = role;
                log.info("educationalViceChair is changed to"+this.getFullName());
                return true;
            }
            log.error("the collage have already had a educationalViceChair");
            return false;
        }
        this.role=role;
        log.info(this.getFullName()+" is no longer educationalViceChair ");
        return true;
    }

    //students:



    public ArrayList<Student> getCurrentStudentWhoSheIsTheirSupervisor() {
        return StudentDataBase.studentsHeIsTheirSupervisor(this);
    }

    //subjects:

    public ArrayList<Subject> getSubjectsPresentedByThisProfessor() {
        return SubjectDataBase.subjectsByThisProfessor(this);
    }


    @Override
    public ArrayList<Subject> getCurrentTermSubjects(){
        ArrayList<Subject> out=new ArrayList<>();
        for(Subject x: SubjectDataBase.getSubjectsCurrentTerm()){
            if(x.getProfessor()!=null && x.getProfessor().equals(this)){
                out.add(x);
            }
        }
        return out;
    }

    @Override
    public ArrayList<String> getProfile() {
        ArrayList<String> data=super.getProfile();
        String professorNumber="professor number: "+this.getProfessorId();
        String roomNumber="room number: "+this.getRoomNumber()+"";
        String degree="degree: "+this.getDegree().name();
        data.add(professorNumber);
        data.add(roomNumber);
        data.add(degree);
        return data;


    }

    /*
    public Professor(String fullName,String codeMelli
            ,String phoneNumber, String email, String username
            , String password,Collage collage,String professorNumber){
        super(fullName, codeMelli, phoneNumber, email, username, password, collage);
        UserDatabase.users.add(this);
        ProfessorDataBase.professors.add(this);
        this.supervisor=false;
        this.degree=Degree.daneshyar;
        this.role= ProfessorRole.normal;
        this.professorNumber=professorNumber;
        currentStudentWhoSheIsTheirSupervisor=new ArrayList<>();
        subjectsPresentedByThisProfessor=new ArrayList<>();

    }

 */
    /*
    public Professor(Degree dexgree,boolean supervisor,
                     ArrayList<Student> currentStudentWhoSheIsTheirSupervisor,
                     ArrayList<Subject> subjectsPresentedByThisProfessor){
        this.degree=degree;
        this.supervisor=supervisor;
        this.currentStudentWhoSheIsTheirSupervisor=currentStudentWhoSheIsTheirSupervisor;
        this.subjectsPresentedByThisProfessor=subjectsPresentedByThisProfessor;

    }

     */
}
