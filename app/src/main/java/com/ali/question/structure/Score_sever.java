package com.ali.question.structure;

/**
 * Created by Ali_Najafi on 3/5/2017.
 */
public class Score_sever {

    String username,type;
    Double score;


    public Score_sever(String username, String type , Double score){
        this.username=username;
        this.type = type;
        this.score    = score;

    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username    = username;
    }

    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type    = type;
    }

    public  Double getScore(){
        return score;
    }
    public  void setScore(Double score){
        this.score      = score;
    }




}
