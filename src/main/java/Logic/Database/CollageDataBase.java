package Logic.Database;

import Logic.Models.Collage;
import Logic.Models.Professor;
import Logic.Models.Student;
import Logic.Models.Subject;

import java.util.ArrayList;

public class CollageDataBase {

    public static ArrayList<Collage> collages=new ArrayList<>();


    public static Collage searchByName(String name){
        if(collages==null){
            collages=new ArrayList<>();
            return null;
        }
        for(Collage x:collages){
            if(name.equals(x.getName())){
                return x;
            }
        }
        return null;
    }

    public static Collage searchById(String collageId){
        for(Collage collage: collages){
            String thisCollageId=collage.getCollageId();
            if(collageId.equals(thisCollageId)){
                return collage;
            }
        }
        return null;
    }

    public static ArrayList<Professor> professors(Collage collage){
        ArrayList<String> professorsId= collage.professorsId;
        return ProfessorDataBase.convertToProfessorArraylistThisStringArraylistOfIds(professorsId);
    }

    public static ArrayList<Student> students(Collage collage){
        ArrayList<String> studentsId=collage.studentsId;
        return StudentDataBase.convertToStudentArraylistThisStringArraylistOfIds(studentsId);
    }

    public static ArrayList<Subject> subjects(Collage collage){
        ArrayList<String> subjectsId=collage.subjectsId;
        return SubjectDataBase.convertToSubjectArraylistThisStringArraylistOfIds(subjectsId);
    }









}
