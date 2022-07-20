package Logic.filter.scoreFilterring;

import Logic.Database.ScoreDataBase;
import Logic.Models.Score;
import Logic.Models.Student;
import Logic.Models.Subject;
import Logic.filter.Filter;

import java.util.ArrayList;

public class FilterByStudent implements Filter {

    @Override
    public ArrayList<String> filter(Object T) {
        if (T instanceof Student){
            String studentId=((Student) T).getStudentId();
            return filter(studentId);
        }
        return null;
    }

    @Override
    public ArrayList<String> filter(String id) {
        ArrayList<String> out=new ArrayList<>();
        for(Score x: ScoreDataBase.scores){
            boolean forThisStudent = x.getStudentId().equals(id);
            if (forThisStudent){
                out.add(x.getScoreId());
            }
        }
        return out;
    }
}
