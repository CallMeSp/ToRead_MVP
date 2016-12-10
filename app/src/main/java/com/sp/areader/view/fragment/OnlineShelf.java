package com.sp.areader.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sp.areader.R;

/**
 * Created by my on 2016/12/10.
 */

public class OnlineShelf extends Fragment {
    public static OnlineShelf newinstance(){
        OnlineShelf onlineShelf=new OnlineShelf();
        return onlineShelf;
    }
    @Override
    public void onCreate(Bundle s){
        super.onCreate(s);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View view=inflater.inflate(R.layout.online,container,false);
        return view;
    }
}
