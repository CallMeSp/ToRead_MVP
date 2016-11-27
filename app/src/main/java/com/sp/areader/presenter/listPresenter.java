package com.sp.areader.presenter;

import com.sp.areader.bean.catalogue;
import com.sp.areader.biz.listbiz;
import com.sp.areader.view.IlistActivity;

import java.util.ArrayList;

/**
 * Created by my on 2016/11/27.
 */
public class listPresenter {
    IlistActivity listActivity;
    listbiz mlistbiz;
    public listPresenter(IlistActivity listActivity){
        this.listActivity=listActivity;
        this.mlistbiz=new listbiz(this);
    }
    public void getcatalogue(String url){
        listActivity.showP();
        mlistbiz.showCatalogue(url);
    }
    public void updateUI(ArrayList<String> namelist,ArrayList<String> urllist ){
        listActivity.updateUI(namelist,urllist);
        listActivity.hideP();
    }
}
