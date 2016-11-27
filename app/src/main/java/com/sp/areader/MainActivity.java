package com.sp.areader;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.SearchView;

import com.sp.areader.adapter.TabAdapter;
import com.sp.areader.bean.book;
import com.sp.areader.presenter.mainPresenter;
import com.sp.areader.view.ImainActivity;
import com.sp.areader.view.IsearchActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ImainActivity{
    SearchView searchView;
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    mainPresenter mainPresenter=new mainPresenter(this);
    private TabAdapter adapter;
    private String s;
    public static final String[] tabTitle = new String[]{"追书","推荐"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }
    private void initViews(){
        searchView=(SearchView)findViewById(R.id.tosearch);
        tabLayout=(TabLayout)findViewById(R.id.titletab);
        viewPager=(ViewPager)findViewById(R.id.contentpager);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                s = query;
                mainPresenter.show_search();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    @Override
    public String getsearchbookname(){
        return s;
    }
    @Override
    public void toSearchActivity(){
        Intent intent=new Intent(this,SearchActivity.class);
        intent.putExtra("searchname",s);
        startActivity(intent);
    }
}
