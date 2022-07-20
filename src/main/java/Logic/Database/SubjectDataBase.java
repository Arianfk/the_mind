package Logic.Database;

import Logic.Models.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class SubjectDataBase {

    //protected static HashMap<Term,ArrayList<Subject>> subjectsByTerm;

   public  static ArrayList<Subject> subjects=new ArrayList<>();

    public static ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public static void setSubjects(ArrayList<Subject> subjects) {
        SubjectDataBase.subjects = subjects;
    }


    public static Integer numberOfPassedStudents(Subject subject){
        Integer numberOfStudents=0;
        HashMap<Student,Score> studentScoreHashMap= subject.getStudentScoreHashMap();
        Set<Student> students=studentScoreHashMap.keySet();
        for(Student student : students){
            Score score=studentScoreHashMap.get(student);
            if(score.getScoreState().equals(ScoreState.permanent) && score.getVasiatNomre().equals(VasiatNomre.passed)){
                numberOfStudents++;
            }
        }
        return numberOfStudents;
    }

    public static Integer numberOfFailedStudents(Subject subject){
        Integer numberOfStudents=0;
        HashMap<Student,Score> studentScoreHashMap= subject.getStudentScoreHashMap();
        Set<Student> students=studentScoreHashMap.keySet();
        for(Student student : students){
            Score score=studentScoreHashMap.get(student);
            if(score.getScoreState().equals(ScoreState.permanent) && score.getVasiatNomre().equals(VasiatNomre.failed)){
                numberOfStudents++;
            }
        }
        return numberOfStudents;
    }


    public static Double calculateGPA(Subject subject){
        Double GPA=0.0;
        Integer numberOfStudents=0;
        HashMap<Student,Score> studentScoreHashMap= subject.getStudentScoreHashMap();
        Set<Student> students=studentScoreHashMap.keySet();
        for(Student student : students){
            Score score=studentScoreHashMap.get(student);
            if(score.getScoreState().equals(ScoreState.permanent)){
                GPA+=score.getScore();
                numberOfStudents++;
            }
        }
        return GPA/numberOfStudents;
    }


    public static Double calculateGPAForNotFailedStudents(Subject subject){
        Double GPA=0.0;
        Integer numberOfStudents=0;
        HashMap<Student,Score> studentScoreHashMap= subject.getStudentScoreHashMap();
        Set<Student> students=studentScoreHashMap.keySet();
        for(Student student : students){
            Score score=studentScoreHashMap.get(student);
            if(score.getScoreState().equals(ScoreState.permanent) && score.getVasiatNomre().equals(VasiatNomre.passed)){
                GPA+=score.getScore();
                numberOfStudents++;
            }
        }
        return GPA/numberOfStudents;
    }



    public static ArrayList<Subject> subjectsByThisProfessor(Professor professor){
        ArrayList<Subject> subjectArrayList=new ArrayList<>();
        for(Subject subject: subjects){
            Professor subjectsProfessor=subject.getProfessor();

            if(subjectsProfessor!=null && subjectsProfessor.equals(professor)){
                subjectArrayList.add(subject);
            }
        }
        return subjectArrayList;
    }


    public static ArrayList<Subject> getSubjectsCurrentTerm() {
        ArrayList<Subject> out=new ArrayList<>();
        for(Subject x:subjects){
            if(!x.isEnded()){
                out.add(x);
            }
        }
        return out;
    }





    public static ArrayList<Subject> filter(Grade grade,ArrayList<Subject> lastFilter){
        if(lastFilter==null){
            lastFilter=subjects;
        }
        ArrayList<Subject> filteringResult=new ArrayList<>();
        if(grade==null){
            return lastFilter;
        }
        for(Subject x: lastFilter){
            if(grade.equals(x.getGrade())){
                filteringResult.add(x);
            }
        }
        return filteringResult;
    }

    public static ArrayList<Subject> filter(Collage collage,ArrayList<Subject> lastFilter){
        if(lastFilter==null){
            lastFilter=subjects;
        }
        ArrayList<Subject> filteringResult=new ArrayList<>();
        if(collage==null){
            return lastFilter;
        }
        for(Subject x: lastFilter ){
            if(collage.equals(x.getCollage())){
                filteringResult.add(x);
            }
        }
        return filteringResult;
    }

    public static ArrayList<Subject> filter(String professorsName,ArrayList<Subject> lastFilter){
        if(lastFilter==null){
            lastFilter=subjects;
        }
        ArrayList<Subject> filteringResult=new ArrayList<>();
        if(professorsName==null){
            return lastFilter;
        }
        for(Subject x: lastFilter){
            if(x.getProfessor()!=null){
                String name=x.getProfessor().getFullName();
                if(name.equals(professorsName)){
                    filteringResult.add(x);
                }
            }
        }
        return filteringResult;
    }

    public static ArrayList<Subject> filter(Grade grade,String professorsName,Collage collage){
        ArrayList<Subject> gradeFilter=filter(grade,getSubjectsCurrentTerm());
        ArrayList<Subject> gradeAndProfessorFilter=filter(professorsName,gradeFilter);
        ArrayList<Subject> allFilter=filter(collage,gradeAndProfessorFilter);
        return allFilter;
    }

    public static ArrayList<Subject> filter(String grade,String professorsName,String collageId){
        Collage c=CollageDataBase.searchById(collageId);
        Grade g=null;
        if(grade.equals("master")){
            g=Grade.master;
        }
        else if(grade.equals("phd")){
            g=Grade.phd;
        }
        else if(grade.equals("bachelor")){
            g=Grade.bachelor;
        }
        if(professorsName.equals("")){
            professorsName=null;
        }
        //System.out.println(g==null);
        //System.out.println(c==null);
        //System.out.println(professorsName==null);
        return filter(g,professorsName,c);
    }


    public static ArrayList<Subject> subjectsThisTerm(Student student){
        ArrayList<Subject> out=new ArrayList<>();
        ArrayList<Subject> subjectsCurrentTerm=SubjectDataBase.getSubjectsCurrentTerm();
        for(Subject x: subjectsCurrentTerm){
            if(x.getStudents().contains(student)){
                out.add(x);
            }
        }
        return out;
    }

    public static ArrayList<Subject> convertToSubjectArraylistThisStringArraylistOfIds(ArrayList<String> subjectsIds){
        ArrayList<Subject> outputSubjects=new ArrayList<>();
        if(subjectsIds==null){
            return outputSubjects;
        }
        for(Subject subject: SubjectDataBase.subjects){
            String subjectId=subject.getSubjectsNumber();
            if(subjectsIds.contains(subjectId)){
                outputSubjects.add(subject);
            }
        }
        return outputSubjects;
    }



    public static Subject searchByNumber(String number){
        for (Subject x: subjects){
            if (x.getSubjectsNumber().equals(number)){
                return x;
            }
        }
        return null;
    }

    public static HashMap<Student,Score> studentScoreForASubject(Subject subject){
        HashMap<Student,Score> out=new HashMap<>();
        ArrayList<Student> students=StudentDataBase.getStudents();
        for (Student student:students){
            HashMap<Subject,Score> subjectScoreHashMap= student.getSubjectScoreHashMap();
            if(subjectScoreHashMap.containsKey(subject)){
                Score score=subjectScoreHashMap.get(subject);
                out.put(student,score);
            }
        }
        return out;
    }











}
