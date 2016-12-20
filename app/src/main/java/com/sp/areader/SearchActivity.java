package com.sp.areader;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.sp.areader.adapter.SearchAdapter;
import com.sp.areader.bean.book;
import com.sp.areader.view.IsearchActivity;
import com.sp.areader.view.fragment.HintPopupWindow;
import com.sp.areader.presenter.searchPresenter;

import org.apache.http.params.CoreConnectionPNames;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by my on 2016/11/23.
 */
public class SearchActivity extends Activity implements IsearchActivity{

    private SearchAdapter adapter;
    private String searchname="";
    private RecyclerView recyclerView;
    private SearchView searchView;
    private HintPopupWindow hintPopupWindow;
    private int longClickposition;
    private LocalBroadcastManager addtoBookShelf;
    ArrayList<book> my_book=new ArrayList<book>();
    searchPresenter presenter=new searchPresenter(this);
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstace){
        super.onCreate(savedInstace);
        setContentView(R.layout.search_layout);
        Intent intent=getIntent();
        searchname=intent.getStringExtra("searchname");
        addtoBookShelf= LocalBroadcastManager.getInstance(this);
        searchView=(SearchView)findViewById(R.id.search_again);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_search);
        progressBar=(ProgressBar)findViewById(R.id.search_progressbar);
        adapter=new SearchAdapter(this,my_book);
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.getlist(query);
                searchView.setIconifiedByDefault(false);
                searchView.clearFocus();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        adapter.setOnBookClickListener(new SearchAdapter.onBookClickListener() {
            @Override
            public void onChoose(int position) {
                presenter.showcatalogue(position);
            }
        });
        adapter.setOnBooklovedListener(new SearchAdapter.onBooklovedListener() {
            @Override
            public void onLoved(int position) {//改成弹出popupwindow->收藏等功能
                View view = linearLayoutManager.findViewByPosition(position);
                longClickposition=position;
                hintPopupWindow.showPopupWindow(view);
            }
        });
        //下面的操作是初始化弹出数据
        ArrayList<String> strList = new ArrayList<>();
        strList.add("加入书架");
        strList.add("取消");
        ArrayList<View.OnClickListener> clickList = new ArrayList<>();
        View.OnClickListener clickListener0=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent("addToShelf");
                myintent.putExtra("lovedBook_name",my_book.get(longClickposition).getBook_name());
                myintent.putExtra("lovedBook_writer",my_book.get(longClickposition).getBook_writter());
                myintent.putExtra("lovedBook_detail",my_book.get(longClickposition).getBook_details());
                myintent.putExtra("lovedBook_imgurl",my_book.get(longClickposition).getBook_cover());
                myintent.putExtra("lovedBook_contenturl",my_book.get(longClickposition).getContenturl());
                addtoBookShelf.sendBroadcast(myintent);
                Log.e("执行","broadcast");
                hintPopupWindow.gonePopupWindow();
            }
        };
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchActivity.this, longClickposition+"点击事件触发"+my_book.get(longClickposition).getBook_name(), Toast.LENGTH_SHORT).show();
                hintPopupWindow.gonePopupWindow();
            }
        };
        clickList.add(clickListener0);
        clickList.add(clickListener);
        //初始化
        hintPopupWindow = new HintPopupWindow(this, strList, clickList);
        presenter.getlist(searchname);
    }
    Handler mhandeler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    progressBar.setVisibility(View.GONE);
                    break;
            }
        }
    };
    @Override
    public void updateUI(ArrayList<book> mybooks){
        my_book.clear();
        my_book.addAll(mybooks);
        Message.obtain(mhandeler,0).sendToTarget();
    }
    @Override
    public void toTextActivity(int position){
        Intent intent=new Intent(SearchActivity.this,ListActivity.class);
        intent.putExtra("title",my_book.get(position).getBook_name());
        intent.putExtra("url",my_book.get(position).getContenturl());
        startActivity(intent);
    }
    @Override
    public void showP(){
        Message.obtain(mhandeler,1).sendToTarget();
    }
    @Override
    public void hideP(){
        Message.obtain(mhandeler,2).sendToTarget();
    }
}
