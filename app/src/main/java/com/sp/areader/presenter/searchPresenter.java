package com.sp.areader.presenter;

import com.sp.areader.bean.book;
import com.sp.areader.biz.bookbiz;
import com.sp.areader.view.IsearchActivity;

import java.util.ArrayList;

/**
 * Created by my on 2016/11/23.
 */
public class searchPresenter {
    private IsearchActivity isearchActivity;
    bookbiz mbookbz;
    public searchPresenter(IsearchActivity isearchActivity){
        this.isearchActivity=isearchActivity;
        this.mbookbz=new bookbiz(this);
    }
    public void getlist(String name){
        isearchActivity.showP();
        mbookbz.showbookslist(name);
    }

    public void updatelist(ArrayList<book> books){
        isearchActivity.updateUI(books);
        isearchActivity.hideP();
    }
    public void showcatalogue(int position){
        isearchActivity.toTextActivity(position);
    }
}
