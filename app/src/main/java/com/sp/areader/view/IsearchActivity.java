package com.sp.areader.view;

import com.sp.areader.bean.book;

import java.util.ArrayList;

/**
 * Created by my on 2016/11/23.
 */
public interface IsearchActivity {
    void updateUI(ArrayList<book> mybooks);
    void toTextActivity(int position);
    void showP();
    void hideP();
}
