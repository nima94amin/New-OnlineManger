package com.ali.question.structure;

/**
 * Created by Ali_Najafi on 3/5/2017.
 */
public class Type_server {

    String type;
    int count;



    public Type_server(String type, int count){
        this.type=type;
        this.count = count;

    }
    public int getCount(){
        return count;
    }
    public void setCount(int count){
        this.count    = count;
    }

    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type    = type;
    }


}
