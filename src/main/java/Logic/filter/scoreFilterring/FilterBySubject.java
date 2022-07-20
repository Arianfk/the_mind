package Logic.filter.scoreFilterring;

import Logic.Database.ScoreDataBase;
import Logic.Models.Score;
import Logic.Models.Subject;
import Logic.filter.Filter;

import java.util.ArrayList;

public class FilterBySubject implements Filter {

    @Override
    public ArrayList<String> filter(Object T) {
        if (T instanceof Subject){
            String subjectId=((Subject) T).getSubjectId();
            return filter(subjectId);
        }
        return null;
    }

    @Override
    public ArrayList<String> filter(String id) {
        ArrayList<String> out=new ArrayList<>();
        for(Score x: ScoreDataBase.scores){
            boolean fromThisSubject = x.getSubjectId().equals(id);
            if (fromThisSubject){
                out.add(x.getScoreId());
            }
        }
        return out;
    }
}
