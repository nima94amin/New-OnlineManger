package com.ali.question.structure;

/**
 * Created by Ali_Najafi on 3/5/2017.
 */
public class Javab_sever {

    String username,id,type;

    int answer;


    public Javab_sever(String username,String type,String id,int answer){
        this.username=username;
        this.id=id;
        this.type    = type;
        this.answer =answer;

    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username    = username;
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id    = id;
    }


    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type    = type;
    }

    public int getAnswer(){
        return answer;
    }
    public void setAnswer(int answer){
        this.answer    = answer;
    }




}
