package com.example.hotelnfc;

public class URL_make {

    public String URL ="http://192.168.0.50/";
    public String param;

    public URL_make(String param){
        this.param = param;
    }

    public String makeURL(){
        return URL+param;
    }
}
