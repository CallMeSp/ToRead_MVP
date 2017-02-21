package com.sp.areader.presenter;

import android.util.Log;

import com.sp.areader.biz.bookbiz;
import com.sp.areader.view.ImainActivity;

/**
 * Created by my on 2016/11/22.
 */
public class mainPresenter {
    private ImainActivity imainActivity;
    public mainPresenter(ImainActivity imainActivity){
        this.imainActivity=imainActivity;
    }
    public void show_search(){
        //Log.e("0","+++"+imainActivity.getsearchbookname());
        imainActivity.toSearchActivity();
    }
}
