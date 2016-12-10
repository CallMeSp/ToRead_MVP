package com.sp.areader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sp.areader.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by my on 2016/11/7.
 */
public class TabAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private ArrayList<String> title=new ArrayList<String>();

    public TabAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
        title.add("我的");
        title.add("在线");
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    @Override
    public int getCount() {
        return fragments.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);

    }
}
