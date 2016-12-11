package com.sp.areader.view.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sp.areader.R;
import com.sp.areader.adapter.OnlineAdapter;

import java.util.ArrayList;

/**
 * Created by my on 2016/12/10.
 */

public class OnlineShelf extends Fragment{
    TabLayout tabLayout;
    ViewPager viewPager;
    OnlineAdapter adapter;
    ArrayList<Fragment> fragments=new ArrayList<Fragment>();
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
        tabLayout=(TabLayout)view.findViewById(R.id.online_Tab);
        viewPager=(ViewPager)view.findViewById(R.id.online_pager);
        fragments.add(BookTypeFragment.newinstance("1/"));
        fragments.add(BookTypeFragment.newinstance("2/"));
        fragments.add(BookTypeFragment.newinstance("3/"));
        fragments.add(BookTypeFragment.newinstance("4/"));
        fragments.add(BookTypeFragment.newinstance("5/"));
        fragments.add(BookTypeFragment.newinstance("6/"));
        adapter=new OnlineAdapter(getFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        //将TabLayout和ViewPager关联起来。
        tabLayout.setupWithViewPager(viewPager);
        //设置可以滑动
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        return view;
    }
}
