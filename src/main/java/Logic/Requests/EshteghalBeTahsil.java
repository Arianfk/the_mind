package Logic.Requests;

import Logic.Models.Student;

import java.time.LocalDateTime;

public class EshteghalBeTahsil extends Request{

    String studdingString;



    public EshteghalBeTahsil(Student student) {
        super(student);
        this.requestSC=RequestSC.studding;
        build(student);
        log.info("اشتغال به تحصیل request");
    }

    public void build(Student student){
        String studentName=student.getFullName();
        String studentNumber=student.getStudentsNumber();
        String collage=student.getCollage().getName();
        Integer year=student.getYearOfEntrance();
        String tarikh= LocalDateTime.now().plusYears(2)+"";
        if(year!=null){
            tarikh=year+4+"";
        }

        String out="govahi mishavad "+studentName+" \n ba shomare shaneshjooee  "+studentNumber+
                "\n mashgolbeTahsil dar reshteye "+collage+"\n dar daneshgah sharif ast "+"\n tarikh etebar govahi "+tarikh;

        studdingString=out;
    }

    public String getStuddingString() {
        return studdingString;
    }
}
