package Logic.Requests;

import Logic.Database.ProfessorDataBase;
import Logic.Models.Professor;
import Logic.Models.Score;
import Logic.Models.Student;
import Logic.Models.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Recommendation extends Request {


    //protected Professor professor;
    //protected Student student;
    final public static int minRequiredScore=17;
    public String recom;



    public Recommendation(String professorId,Student student){
        super(student);
        this.professorId=professorId;
        this.requestSC=RequestSC.recommendation;
        this.stateOfRequest=StateOfRequest.registered;
        BuildTosiehName(student);
        log.info("recommendation request");
    }

    public void BuildTosiehName(Student student){
        Professor professor= ProfessorDataBase.searchById(professorId);
        if(professor!=null){
            String professorName=professor.getFullName();
            String studentName=student.getFullName();
            String studentNumber=student.getStudentsNumber();
            String subjects=Arrays.toString(darsHa(professor,student).toArray());
            String scores=Arrays.toString(nomrat(professor,student).toArray());
            String subjectsAsTa=Arrays.toString(subjectsAsTA(professor,student).toArray());
            String out="injaneb "+professorName+" govahi midaham daneshjo: "+studentName
                    +" be shomare daneshjoee "+studentNumber+" darshaye "+
                    subjects+" banomrat "+scores+
                    " gozarande va dar dars haye "+
                    subjectsAsTa+" be onvan ta faliat dashte";

            recom=out;
        }

    }

    public String getRecom() {
        return recom;
    }

    private static ArrayList<Subject> subjectsWithThisProfessorAsStudent(Professor professor , Student student){
        ArrayList<Subject> out=new ArrayList<>();
        ArrayList<Subject> studentsSubjects=student.getSubjects();
        HashMap<Subject, Score> scors =student.getSubjectScoreHashMap();
        for(Subject subject : studentsSubjects){
            if(subject.getProfessor().equals(professor)){
                Double d=scors.get(subject).getScore();
                if(d!=null && d>minRequiredScore){
                    out.add(subject);
                }
            }
        }
        return out;
    }

    private static ArrayList<String> darsHa(Professor professor ,Student student){
        ArrayList<Subject> subjects=subjectsWithThisProfessorAsStudent(professor, student);
        ArrayList<String> out=new ArrayList<>();
        for (Subject s: subjects){
            out.add(s.getSubjectsName());
        }
        return out;
    }

    private static ArrayList<String> nomrat(Professor professor, Student student){
        ArrayList<Subject> subjects=subjectsWithThisProfessorAsStudent(professor,student);
        ArrayList<String> out=new ArrayList<>();
        HashMap<Subject,Score> subjectScoreHashMap= student.getSubjectScoreHashMap();
        for (Subject s: subjects){
            out.add(subjectScoreHashMap.get(s)+"");
        }
        return out;

    }


    private static ArrayList<Subject> subjectsAsTAWithThisProfessor(Professor professor, Student student){
        ArrayList<Subject> out=new ArrayList<>();
        ArrayList<Subject> professorSubjects=professor.getSubjectsPresentedByThisProfessor();
        for(Subject x: professorSubjects){
            if(x.getTAs().contains(student)){
                out.add(x);
            }
        }
        return out;
    }

    private static ArrayList<String> subjectsAsTA(Professor professor, Student student){
        ArrayList<Subject> subjects=subjectsAsTAWithThisProfessor(professor,student);
        ArrayList<String> out=new ArrayList<>();
        for (Subject s: subjects){
            out.add(s.getSubjectsName());
        }
        return out;
    }

}
