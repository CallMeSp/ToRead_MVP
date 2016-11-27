package com.sp.areader.bean;

/**
 * Created by my on 2016/11/22.
 */
public class book {
    public String book_name,book_writter,book_details,book_cover,contenturl;
    public String getBook_name(){
        return book_name;
    }
    public String getBook_writter(){
        return book_writter;
    }
    public String getBook_details(){
        return book_details;
    }
    public String getBook_cover(){
        return book_cover;
    }
    public String getContenturl(){return contenturl;}
    public void setBook_name(String s){
        this.book_name=s;
    }
    public void setBook_writter(String s){
        this.book_writter=s;
    }
    public void setBook_details(String s){
        this.book_details=s;
    }
    public void setBook_cover(String s){
        this.book_cover=s;
    }
    public void setContenturl(String s){this.contenturl=s;}
}
