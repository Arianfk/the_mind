package Logic.Database;

import Logic.Models.*;

import java.util.ArrayList;
import java.util.HashMap;

public class  ProfessorDataBase {

   public static ArrayList<Professor> professors=new ArrayList<>();

    //getters&setters

    public static void setProfessors(ArrayList<Professor> professors) {
        ProfessorDataBase.professors = professors;
    }

    public static ArrayList<Professor> getProfessors() {
        if (professors==null){
            professors=new ArrayList<>();
        }
        return professors;
    }

    //filters


    public static ArrayList<Professor> filter(Collage collage,ArrayList<Professor> lastFilter){
        if(lastFilter==null){
            lastFilter=professors;
        }
        ArrayList<Professor> filteringResult=new ArrayList<>();
        if(collage==null){
            return lastFilter;
        }
        for(Professor x: lastFilter ){
            Collage collage1=x.getCollage();
            if(collage.equals(collage1)){
                filteringResult.add(x);
            }
        }
        return filteringResult;
    }

    public static ArrayList<Professor> filter(Degree degree, ArrayList<Professor> lastFilter){
        if(lastFilter==null){
            lastFilter=professors;
        }
        ArrayList<Professor> filteringResult=new ArrayList<>();
        if(degree==null){
            return lastFilter;
        }
        for(Professor x: lastFilter){
            Degree degree1=x.getDegree();
            if(degree1.equals(degree)){
                filteringResult.add(x);
            }
        }
        return filteringResult;
    }

    public static ArrayList<Professor> filter(String professorsName,ArrayList<Professor> lastFilter){
        if(lastFilter==null){
            lastFilter=professors;
        }
        ArrayList<Professor> filteringResult=new ArrayList<>();
        if(professorsName==null){
            return lastFilter;
        }
        for(Professor x: lastFilter){
            String name=x.getFullName();
            if(professorsName.equals(name)){
                filteringResult.add(x);
            }
        }
        return filteringResult;
    }

    public static ArrayList<Professor> filter(Collage collage,String professorsName,Degree degree){
        ArrayList<Professor> degreeFilter=filter(degree,professors);
        ArrayList<Professor> gradeAndProfessorFilter=filter(professorsName,degreeFilter);
        ArrayList<Professor> allFilter=filter(collage,gradeAndProfessorFilter);
        return allFilter;
    }

    public static ArrayList<Professor> filter(String collage,String professorsName,String degree){
        Collage c= CollageDataBase.searchByName(collage);
        Degree d=null;
        /*
        ostadyar,daneshyar,fullprofessor
         */
        if(degree.equals("daneshyar")){
            d=Degree.daneshyar;
        }
        else if (degree.equals("fullprofessor")){
            d=Degree.fullprofessor;
        }
        else if( degree.equals("ostadyar")){
            d=Degree.ostadyar;
        }
        if(professorsName.equals("")){
            professorsName=null;
        }

        return filter(c,professorsName,d);

    }





    public static Professor searchById(String id){

        for(Professor professor: getProfessors()){
            String professorsId=professor.getProfessorNumber();
            if(id.equals(professorsId)){
                return professor;
            }
        }
        return null;
    }








     public boolean changeProfessorInfo(Professor professor,String degree,String roomNumber,String role,String supervisor){
        if(degree!=null && !degree.equals("")){
            professor.setDegree(Degree.valueOf(degree));
        }
         if(roomNumber!=null && !roomNumber.equals("")){
             professor.setRoomNumber(Integer.valueOf(roomNumber));
         }
         if(role!=null && !role.equals(ProfessorRole.DepartmentChair.name())){
             if(role.equals(ProfessorRole.educationalViceChair)){
                 if(professor.getCollage().getEducationViceChair()==null){
                     professor.setRole(ProfessorRole.educationalViceChair);
                 }
             }
             else if(role.equals(ProfessorRole.normal)){

             }
         }
         return true;

     }

     //change info by themselves
     private void changeProfessorsInfo (Professor professor,String email,String phoneNumber){
         professor.setEmail(email);
     }
    private void changeProfessorsInfo (Professor professor,String phoneNumber){

    }

    public void removeProfessor(Professor professor){
        professors.remove(professor);

    }



    public static ArrayList<Professor> convertToProfessorArraylistThisStringArraylistOfIds(ArrayList<String> professorIds){
        ArrayList<Professor> outputProfessors=new ArrayList<>();
        for(Professor professor: professors){
            String professorId=professor.getProfessorNumber();
            if(professorIds.contains(professorId)){
                outputProfessors.add(professor);
            }
        }
        return outputProfessors;
    }




    /*
    private boolean removeProfessor(Professor professor,DepartmentChair departmentChair){
        Collage departmentChairCollage=departmentChair.getCollage();
        if(departmentChairCollage.equals(professor.getCollage())){
            professors.remove(professor);
            ArrayList<Professor> professorsFromThisCollage=professorsBasedOnCollage.get(departmentChairCollage);
            professorsFromThisCollage.remove(professor);
            professorsBasedOnCollage.replace(departmentChairCollage,professorsFromThisCollage);
            return true;
        }
        return false;
    }

    private boolean addProfessor(Professor professor,DepartmentChair departmentChair){
        Collage departmentChairCollage=departmentChair.getCollage();
        if(departmentChairCollage.equals(professor.getCollage())){
            professors.add(professor);
            ArrayList<Professor> professorsFromThisCollage=professorsBasedOnCollage.get(departmentChairCollage);
            professorsFromThisCollage.add(professor);
            professorsBasedOnCollage.replace(departmentChairCollage,professorsFromThisCollage);
            return true;
        }
        return false;
    }

 */
/*
    private Professor ViceChair(Collage collage){
        for(Professor p: professorsBasedOnCollage.get(collage)){
            if(p instanceof EducationViceChair){
                return p;
            }
        }
        return null;
    }


    private boolean removeViceChair(DepartmentChair departmentChair){
        Collage collage=departmentChair.getCollage();
        Professor professor=ViceChair(collage);
        if(professor==null){
            return false;
        }
        professor.setRole(ProfessorRole.normal);//todo
        return true;
    }

 */
    /*
    public static void setProfessorsBasedOnCollage(HashMap<Collage, ArrayList<Professor>> professorsBasedOnCollage) {
        ProfessorDataBase.professorsBasedOnCollage = professorsBasedOnCollage;
    }

    public static HashMap<Collage, ArrayList<Professor>> getProfessorsBasedOnCollage() {
        return professorsBasedOnCollage;
    }

 */

    //protected static HashMap<Collage,ArrayList<Professor>> professorsBasedOnCollage;









}
