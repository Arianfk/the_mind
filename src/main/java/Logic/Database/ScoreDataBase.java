package Logic.Database;

import Logic.Models.Score;
import Logic.Models.ScoreState;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class ScoreDataBase {

    public static ArrayList<Score> scores=new ArrayList<>();


    public static ArrayList<Score> getScores() {
        if (scores==null){
            scores=new ArrayList<>();
        }
        return scores;
    }

    public static Logger log= LogManager.getLogger(ScoreDataBase.class);


    public static Score searchByScoreId(String scoreId){
        for(Score score: scores){
            String id=score.getScoreId();
            if(id.equals(scoreId)){
                return score;
            }
        }
        Score score=new Score();
        score.setScoreState(ScoreState.no);
        score.setScoreId(scoreId);
        return score;
        //return null;
    }


    public static Double validateScoreSubject(Double score){
        log.info("validate score is running");

        double trueScore=Math.floor(score);
        if(score>20 || score<0 ){
            log.error("score is more than 20 0r less than 0 ");
            return null;
        }
        double a=(score-trueScore)*100;
        double min=25/2.0;
        if(a<min){

            return trueScore;
        }
        else if(a<min+25){
            return trueScore+0.25;
        }
        else if(a<min+50){
            return trueScore+0.50;
        }
        else if(a<min+75){
            return trueScore+0.75;
        }
        else{
            return trueScore+1;
        }

    }





}
