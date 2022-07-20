package Logic.Database;

import Logic.Models.*;
import javafx.scene.Scene;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class StudentDataBase extends UserDatabase{

    public  static ArrayList<Student> students=new ArrayList<>();

    public static void setStudents(ArrayList<Student> students) {
        StudentDataBase.students = students;
    }

    public static ArrayList<Student> getStudents() {
        if (students==null){
            students=new ArrayList<>();
        }
        return students;
    }

    public static Student searchByName(String name){
        for(Student x: students){
            if(x.getFullName().equals(name)){
                return x;
            }
        }
        return null;
    }

    public static Student searchByStudentNumber(String studentNumber){
        for(Student x: students){
            if(x.getStudentsNumber().equals(studentNumber)){
                return x;
            }
        }
        return null;
    }



    private static void removeStudent(Student student){

    }

    public static ArrayList<Student> convertToStudentArraylistThisStringArraylistOfIds(ArrayList<String> studentsId){

        ArrayList<Student> outputStudents=new ArrayList<>();
        if(studentsId==null){
            return outputStudents;
        }
        for(Student student: students){
            String studentId=student.getStudentsNumber();
            if( studentsId.contains(studentId)){
                outputStudents.add(student);
            }
        }
        return outputStudents;
    }

    public static ArrayList<Student> studentsHeIsTheirSupervisor(Professor professor){
        ArrayList<Student> studentArrayList=new ArrayList<>();
        for(Student student : students){
            Professor studentsSupervisor=student.getSupervisor();
            if(studentsSupervisor.equals(professor)){
                studentArrayList.add(student);
            }
        }
        return studentArrayList;
    }

    public static ArrayList<Student> haveThisSubject(Subject subject){//todo 0
        ArrayList<Student> studentArrayList=new ArrayList<>();
        for(Student student: students){
            //System.out.println(student.getFullName());//todo
            ArrayList<Subject> studentSubjects=student.getSubjects();
            if(studentSubjects!=null && studentSubjects.contains(subject)){
                studentArrayList.add(student);
            }
        }
        return studentArrayList;
    }





    public static HashMap<Subject,Score> temporaryScores(Student student){
        HashMap<Subject,Score> subjectScoreHashMap= student.getSubjectScoreHashMap();
        Collection<Score> scores=subjectScoreHashMap.values();
        Set<Subject> subjects=subjectScoreHashMap.keySet();
        HashMap<Subject,Score> subjectsWithTemporaryScore=new HashMap<>();
        for(Subject subject: subjects){
            Score score=subjectScoreHashMap.get(subject);
            System.out.println(score.getScoreId());
            if(score.getScoreState().equals(ScoreState.sabtTemprorary)){
                subjectsWithTemporaryScore.put(subject,score);
            }
        }
        return subjectsWithTemporaryScore;
    }

    public static Integer passedVaheds(Student student){
        Integer passedVahed=0;
        HashMap<Subject,Score> subjectScoreHashMap=student.getSubjectScoreHashMap();
        Set<Subject> subjects=subjectScoreHashMap.keySet();
        for(Subject subject:subjects){
            Score score=subjectScoreHashMap.get(subject);
            if(score.getVasiatNomre().equals(VasiatNomre.passed) && score.getScoreState().equals(ScoreState.permanent)){
                passedVahed+=subject.getVahed();
            }
        }
        return passedVahed;
    }


    public static Double calculateGPA(Student student){
        Double GPA=0.0;
        Integer numberOfVaheds=0;
        HashMap<Subject,Score> subjectScoreHashMap= student.getSubjectScoreHashMap();
        Set<Subject> subjects=subjectScoreHashMap.keySet();
        for(Subject subject:subjects){
            Score score=subjectScoreHashMap.get(subject);
            if(score.getScoreState().equals(ScoreState.permanent)){
                GPA+=score.getScore();
                numberOfVaheds++;
            }
        }
        if(numberOfVaheds==0){
            return 0.0;
        }
        return GPA/numberOfVaheds;
    }














}
