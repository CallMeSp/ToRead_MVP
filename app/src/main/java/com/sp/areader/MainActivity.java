package com.sp.areader;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.SearchView;

import com.sp.areader.adapter.TabAdapter;
import com.sp.areader.bean.book;
import com.sp.areader.presenter.mainPresenter;
import com.sp.areader.view.ImainActivity;
import com.sp.areader.view.IsearchActivity;
import com.sp.areader.view.fragment.BookShelf;
import com.sp.areader.view.fragment.OnlineShelf;
import com.sp.areader.view.fragment.WaveView3;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ImainActivity{

    SearchView searchView;
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    WaveView3 waveView3;
    private ArrayList<Fragment> Fragments=new ArrayList<Fragment>();
    mainPresenter mainPresenter=new mainPresenter(this);
    private TabAdapter adapter;
    private String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }
    private void initViews(){
        Fragments.add(BookShelf.newinstance());
        Fragments.add(OnlineShelf.newinstance());
        adapter=new TabAdapter(getSupportFragmentManager(),Fragments);

        searchView=(SearchView)findViewById(R.id.tosearch);
        tabLayout=(TabLayout)findViewById(R.id.title_tab);
        viewPager=(ViewPager)findViewById(R.id.content_pager);
        viewPager.setAdapter(adapter);
        //将TabLayout和ViewPager关联起来。
        tabLayout.setupWithViewPager(viewPager);
        //设置可以滑动
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        waveView3=(WaveView3)findViewById(R.id.wave_view);
        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-2,-2);
        lp.gravity = Gravity.BOTTOM| Gravity.CENTER;
        waveView3.setOnWaveAnimationListener(new WaveView3.OnWaveAnimationListener() {
            @Override
            public void OnWaveAnimation(float y) {
                lp.setMargins(0,0,0,(int)y+2);
                tabLayout.setLayoutParams(lp);
            }
        });
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                s = query;
                mainPresenter.show_search();
                searchView.setIconifiedByDefault(false);
                searchView.clearFocus();
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
