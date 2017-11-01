package com.ali.question.structure;

/**
 * Created by Ali_Najafi on 3/5/2017.
 */
public class Soal_sever {

    String type,id;
    String qustion;
    String g1,g2,g3,g4;


    public Soal_sever(String type,String id ,String qustion, String g1 ,String g2 ,String g3 ,String g4){
        this.id=id;
        this.qustion = qustion;
        this.type    = type;
        this.g1      = g1;
        this.g2      = g2;
        this.g3      = g3;
        this.g4      = g4;

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

    public String getQustion(){
        return qustion;
    }
    public void setQustion(String qustion){
        this.qustion = qustion;
    }

    public  String getG1(){
        return g1;
    }
    public  void setG1(String g1){
        this.g1      = g1;
    }
    public  String getG2(){
        return g2;
    }
    public  void setG2(String g2){
        this.g2      = g2;
    }
    public  String getG3(){
        return g3;
    }
    public  void setG3(String g3){
        this.g3      = g3;
    }
    public  String getG4(){
        return g4;
    }
    public  void setG4(String g4){
        this.g4      = g4;
    }




}
