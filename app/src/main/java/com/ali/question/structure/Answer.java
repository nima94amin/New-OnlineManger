package com.ali.question.structure;

/**
 * Created by Ali_Najafi on 4/7/2017.
 */
public class Answer {

    String id;
    String answer;

    public Answer(String id , String answer){
        this.id = id;
        this.answer  = answer;
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id    = id;
    }

    public String getAnswer(){
        return answer;
    }
    public void setAnswer(String answer){
        this.answer    = answer;
    }

}