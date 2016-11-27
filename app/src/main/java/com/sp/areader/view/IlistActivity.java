package com.sp.areader.view;

import com.sp.areader.bean.catalogue;

import java.util.ArrayList;

/**
 * Created by my on 2016/11/27.
 */
public interface IlistActivity {
    void changestate();
    void updateUI(ArrayList<String> namelist,ArrayList<String> urllist );
    void showP();
    void hideP();
}
