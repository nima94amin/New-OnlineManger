package com.ali.question.structure;

/**
 * Created by Ali_Najafi on 3/5/2017.
 */
public class Algol_sever {

    String type,id;

    Double w1,w2,w3,w4;


    public Algol_sever(String type,String id , Double w1 ,Double w2 ,Double w3 ,Double w4){
        this.id=id;

        this.type    = type;
        this.w1      = w1;
        this.w2      = w2;
        this.w3      = w3;
        this.w4      = w4;

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


    public  Double getW1(){
        return w1;
    }
    public  void setW1(Double w1){
        this.w1      = w1;
    }
    public  Double getW2(){
        return w2;
    }
    public  void setW2(Double w2){
        this.w2      = w2;
    }
    public  Double getW3(){
        return w3;
    }
    public  void setW3(Double w3){
        this.w3      = w3;
    }
    public  Double getW4(){
        return w4;
    }
    public  void setW4(Double w4){
        this.w4      = w4;
    }



}
