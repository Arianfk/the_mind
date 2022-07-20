package Logic.Models;

import Logic.Database.ScoreDataBase;

public class Score {

    public String scoreId;
    protected String subjectId;
    protected String studentId;
    protected Double score;
    protected String eteraz;
    protected String javabie;


    //getters and setters:

    //subjectId:

    public String getSubjectId() {
        return subjectId;
    }

    //studentId:

    public String getStudentId() {
        return studentId;
    }


    //score id:

    public String getScoreId() {
        return scoreId;
    }

    public void setScoreId(String scoreId) {
        this.scoreId = scoreId;
    }

    //javabie :

    public String getJavabie() {
        return javabie;
    }

    public void setJavabie(String javabie) {
        this.javabie = javabie;
    }

    //eteraz:

    public String getEteraz() {
        return eteraz;
    }

    public void setEteraz(String eteraz) {
        this.eteraz = eteraz;
    }

    //Score:

    public Double getScore() {
        return score;
    }






}
