package com.ali.question.structure;

/**
 * Created by Ali_Najafi on 3/5/2017.
 */
public class Users_sever {

    String name,family;
    String username;
    String fathername,code,ostan,city,address;
    String mobile;

    public Users_sever(String username,String name ,String family, String fathername ,String code ,String ostan ,String city,String address,String mobile){
        this.username=username;
        this.name = name;
        this.family    = family;
        this.fathername      = fathername;
        this.code      = code;
        this.ostan      = ostan;
        this.city      = city;
        this.address  = address;
        this.mobile   =mobile;
    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username    = username;
    }


    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name    = name;
    }

    public String getFamily(){
        return family;
    }
    public void setFamily(String family){
        this.family = family;
    }

    public  String getFathername(){
        return fathername;
    }
    public  void setFathername(String fathername){
        this.fathername      = fathername;
    }
    public  String getCode(){
        return code;
    }
    public  void setCode(String code){
        this.code      = code;
    }
    public  String getOstan(){
        return ostan;
    }
    public  void setOstan(String ostan){
        this.ostan      = ostan;
    }
    public  String getCity(){
        return city;
    }
    public  void setCity(String city){
        this.city      = city;
    }

    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }

    public String getMobile(){
        return mobile;
    }
    public void setMobile(String mobile){
        this.mobile = mobile;
    }


}
