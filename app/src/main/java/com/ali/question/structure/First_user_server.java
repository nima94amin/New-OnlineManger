package com.ali.question.structure;

/**
 * Created by Ali_Najafi on 3/5/2017.
 */
public class First_user_server {

    String username,moblie;
    int register;


    public First_user_server(String username,String moblie ,int register){
        this.username=username;
        this.moblie = moblie;
        this.register    = register;

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

    public int getRegister(){
        return register;
    }
    public void setRegister(int register){
        this.register = register;
    }





}
