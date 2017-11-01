package com.ali.question.structure;

/**
 * Created by Ali_Najafi on 3/5/2017.
 */
public class Frind_sever {

    String username,moblie;



    public Frind_sever(String username,String moblie){
        this.username=username;
        this.moblie = moblie;

    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username    = username;
    }

    public String getMoblie(){
        return moblie;
    }
    public void setMoblie(String moblie){
        this.moblie    = moblie;
    }
}
