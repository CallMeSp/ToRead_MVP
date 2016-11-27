package com.sp.areader.bean;

/**
 * Created by my on 2016/11/22.
 */
public class catalogue {
    String title,url;
    public String gettitle(){
        return this.title;
    }
    public void settitle(String s){
        this.title=s;
    }
    public String geturl(){
        return this.url;
    }
    public void seturl(String s){
        this.url=s;
    }
}
