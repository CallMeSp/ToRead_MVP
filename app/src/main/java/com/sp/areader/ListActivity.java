package com.sp.areader;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sp.areader.adapter.ListViewAdapter;
import com.sp.areader.bean.catalogue;
import com.sp.areader.presenter.listPresenter;
import com.sp.areader.view.IlistActivity;
import com.sp.areader.view.fragment.HintPopupWindow;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by sp on 2016/11/12.
 */
public class ListActivity extends Activity implements IlistActivity{
    private ListView listView;
    private TextView textView;
    private ToggleButton toggleButton;
    private ListViewAdapter adapter;
    private ArrayList<String> catalogitems=new ArrayList<String>(),nextchapter_url_list=new ArrayList<String>();
    private IntentFilter intentFilter;
    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;
    private ArrayList<catalogue> catalogues=new ArrayList<catalogue>();

    private String url,mstitle;
    private boolean state;
    //private DBHelper dbHelper;
    private Cursor cursor;
    private int _id=0,pos_from_text=0;
    private HintPopupWindow hintPopupWindow;
    private ProgressBar progressBar;
    listPresenter presenter=new listPresenter(this);

    @Override
    protected void onCreate(Bundle s){
        super.onCreate(s);
        setContentView(R.layout.listactivity_layout);
        state=false;
        localBroadcastManager=LocalBroadcastManager.getInstance(this);
        intentFilter=new IntentFilter();
        localReceiver=new LocalReceiver();
        intentFilter.addAction("showthePOSITION");
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);


        listView=(ListView)findViewById(R.id.catalague_list);

        textView=(TextView)findViewById(R.id.catalog_title);
        progressBar=(ProgressBar)findViewById(R.id.list_progressbar);
        toggleButton=(ToggleButton)findViewById(R.id.change_sequence);
        Intent intent=getIntent();
        mstitle=intent.getStringExtra("title");
        url=intent.getStringExtra("url");
        textView.setText(mstitle);
        try {
            pos_from_text=Integer.valueOf(intent.getStringExtra("fromtext")).intValue();
            String bb=intent.getStringExtra("state");
            if (bb.equals("true")){
                state=true;
            }
        }catch (NumberFormatException e){
            Log.e("0","error");
        }
        adapter=new ListViewAdapter(this,R.layout.list_item,catalogitems,state);
        listView.setAdapter(adapter);
        presenter.getcatalogue(url);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*if (state) {
                    position = catalogitems.size() - position - 1;
                }*/
                Intent intent = new Intent(ListActivity.this, TextActivity.class);
                intent.putExtra("titlelist", catalogitems);
                intent.putExtra("urllist", nextchapter_url_list);
                String a = nextchapter_url_list.get(position);
                intent.putExtra("url", a);
                intent.putExtra("position", "" + position);
                intent.putExtra("state",""+state);
                Log.e("0", "position=" + position);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {//写个popupwindow事件。弹出添加书签功能。
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                hintPopupWindow.showPopupWindow(view);
                return true;
            }
        });
        toggleButton.setChecked(false);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changestate();
                Collections.reverse(catalogitems);
                Collections.reverse(nextchapter_url_list);
                Message.obtain(mhandeler, 0).sendToTarget();
            }
        });
        ArrayList<String> strList = new ArrayList<>();
        strList.add("添加书签");
        strList.add("收藏本章");
        strList.add("选项3");

        ArrayList<View.OnClickListener> clickList = new ArrayList<>();
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListActivity.this, "点击事件触发", Toast.LENGTH_SHORT).show();
            }
        };

        clickList.add(clickListener);
        clickList.add(clickListener);
        clickList.add(clickListener);
        //初始化
        hintPopupWindow = new HintPopupWindow(this, strList, clickList);
    }
    Handler mhandeler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.e("0", "hhhhhhhhhhhhhh+" + state);
            switch (msg.what){
                case 0:
                    adapter.notifyDataSetChanged();
                    listView.setSelection(pos_from_text);
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
    public void changestate(){
        if (state){
            state=false;
        }else {
            state=true;
        }
    }
    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context,Intent mintent){
            int pos=Integer.valueOf(mintent.getStringExtra("test")).intValue();
            listView.setSelection(pos);
        }
    }
    @Override
    public void updateUI(ArrayList<String> namelist,ArrayList<String> urllist ){
        catalogitems.clear();nextchapter_url_list.clear();
        catalogitems.addAll(namelist);nextchapter_url_list.addAll(urllist);
        if (state==true){
            Collections.reverse(catalogitems);
            Collections.reverse(nextchapter_url_list);
        }
        Message.obtain(mhandeler).sendToTarget();
    }
    @Override
    public void showP(){
        Message.obtain(mhandeler,1).sendToTarget();
    }
    @Override
    public void hideP(){
        Message.obtain(mhandeler,2).sendToTarget();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }
}
