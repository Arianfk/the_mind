package Logic.filter.scoreFilterring;

import Logic.Database.ScoreDataBase;
import Logic.Database.SubjectDataBase;
import Logic.Models.Professor;
import Logic.Models.Score;
import Logic.Models.Subject;
import Logic.filter.Filter;

import java.util.ArrayList;

public class FilterByProfessor implements Filter {

    @Override
    public ArrayList<String> filter(Object T) {
        if (T instanceof Professor){
            String professorId=((Professor) T).getProfessorId();
            return filter(professorId);
        }
        return null;
    }

    @Override
    public ArrayList<String> filter(String id) {
        ArrayList<String> out=new ArrayList<>();
        for(Score x: ScoreDataBase.scores){
            String subjectId=x.getSubjectId();
            Subject subject = SubjectDataBase.searchByNumber(subjectId);
            assert subject != null;
            String professorId=subject.getProfessorId();
            boolean fromThisProfessor = professorId.equals(id);
            if (fromThisProfessor){
                out.add(x.getScoreId());
            }
        }
        return out;
    }
}
