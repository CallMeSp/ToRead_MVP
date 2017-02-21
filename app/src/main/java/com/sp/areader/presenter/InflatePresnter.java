package com.sp.areader.presenter;

import android.util.Log;

import com.sp.areader.bean.book;
import com.sp.areader.biz.InflateBookType;
import com.sp.areader.view.IBookTypeFragment;

import java.util.ArrayList;

/**
 * Created by my on 2016/12/11.
 */

public class InflatePresnter {
    IBookTypeFragment iBookTypeFragment;
    InflateBookType inflateBookType;
    public InflatePresnter(IBookTypeFragment iBookTypeFragment){
        this.iBookTypeFragment=iBookTypeFragment;
        inflateBookType=new InflateBookType(this);
    }
    public void getlist(String name){
        //isearchActivity.showP();
        //Log.e("presenter",name);
        inflateBookType.showbookslist(name);
    }
    public void postNUM(String num){
        iBookTypeFragment.getNUM(num);
    }
    public void updatelist(ArrayList<book> books){
        iBookTypeFragment.updateUI(books);
        //isearchActivity.hideP();
    }
}
