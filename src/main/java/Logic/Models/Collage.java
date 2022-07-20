package Logic.Models;

import Logic.Database.CollageDataBase;
import Logic.Database.ProfessorDataBase;

import java.util.ArrayList;


public class Collage {


    private String collageId;
    public String name;
    public ArrayList<String> professorsId;
    public String departmentChairId;
    public String educationViceChairId;
    public ArrayList<String> subjectsId=new ArrayList<>();
    public ArrayList<String> studentsId=new ArrayList<>();



    //getters&setters:



    //name:

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //professors

    public ArrayList<String> getProfessorsId() {
        return professorsId;
    }

    public void setProfessorsId(ArrayList<String> professorsId) {
        this.professorsId = professorsId;
    }

    public ArrayList<Professor> getProfessors() {
        return CollageDataBase.professors(this);
    }

    //get important professors:


    public Professor getDepartmentChair() {
        return ProfessorDataBase.searchById(departmentChairId);
    }

    public Professor getEducationViceChair() {
        return ProfessorDataBase.searchById(educationViceChairId);
    }

    public String getDepartmentChairId() {
        return departmentChairId;
    }

    public String getEducationViceChairId() {
        return educationViceChairId;
    }

    //get subjects:


    public void setSubjectsId(ArrayList<String> subjectsId) {
        this.subjectsId = subjectsId;
    }
    public ArrayList<String> getSubjectsId() {
        return subjectsId;
    }

    public ArrayList<Subject> getSubjects() {
        return CollageDataBase.subjects(this);
    }

    //get students:

    public void setStudentsId(ArrayList<String> studentsId) {
        this.studentsId = studentsId;
    }

    public ArrayList<String> getStudentsId() {
        return studentsId;
    }

    public ArrayList<Student> getStudents() {
        return CollageDataBase.students(this);
    }

    //collageId

    public String getCollageId() {
        return collageId;
    }

    public void setCollageId(String collageId) {
        this.collageId = collageId;
    }
}

