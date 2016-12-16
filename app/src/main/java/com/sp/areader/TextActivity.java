package com.sp.areader;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sp.areader.bean.catalogue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by my on 2016/11/9.
 */
public class TextActivity extends AppCompatActivity {
    public TextView textView,title;
    public String url="",text="";
    public ArrayList<String> nexturl=new ArrayList<String>(),titles=new ArrayList<String>();
    public int position;
    public NestedScrollView scrollView;
    public float dx,wholex,last,next;
    public String mstate,ltitle,lurl;
    private ArrayList<catalogue> catalogues=new ArrayList<catalogue>();
    private NavigationView navigationView;
    private PopupWindow popupWindow;
    private View popupview;
    private Button button1,button2,button4;
    private ToggleButton button3;

    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle s){
        super.onCreate(s);
        setContentView(R.layout.story_content);
        localBroadcastManager=LocalBroadcastManager.getInstance(this);
        title=(TextView)findViewById(R.id.contenttitle);
        textView=(TextView)findViewById(R.id.story);scrollView=(NestedScrollView)findViewById(R.id.scrollView);
        Intent intent=getIntent();
        mstate=intent.getStringExtra("state");
        url=intent.getStringExtra("url");
        position= Integer.valueOf(intent.getStringExtra("position")).intValue();
        nexturl=intent.getStringArrayListExtra("urllist");
        titles=intent.getStringArrayListExtra("titlelist");
        getText(url);

        popupview=getLayoutInflater().inflate(R.layout.popup_menu, null);
        popupWindow=new PopupWindow(popupview, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,true);
        //实现点击外部收回popupwindow
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

        button1=(Button)popupview.findViewById(R.id.pop_cata);
        //button2=(Button)popupview.findViewById(R.id.pop_text);
        button3=(ToggleButton) popupview.findViewById(R.id.pop_light);
        //button4=(Button)popupview.findViewById(R.id.pop_download);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    textView.setBackgroundColor(Color.BLACK);
                    textView.setTextColor(Color.WHITE);
                    title.setBackgroundColor(Color.BLACK);
                    title.setTextColor(Color.WHITE);

                }else {
                    textView.setBackgroundColor(getResources().getColor(R.color.tan));
                    textView.setTextColor(Color.BLACK);
                    title.setBackgroundColor(getResources().getColor(R.color.tan));
                    title.setTextColor(Color.BLACK);
                }
            }
        });
        //根据触摸位置判断是下一章还是上一章
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dx = event.getX();
                        wholex=textView.getWidth();last=wholex/3;next=wholex*2/3;
                        Log.e("0","dx:" + dx+"   wholex:"+wholex);
                }
                return false;
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dx >next) {
                    if(mstate.equals("false")){
                        position++;
                        getText(nexturl.get(position));
                    }else {
                        position--;
                        getText(nexturl.get(position));
                    }
                }else if(dx<last){
                    if (mstate.equals("false")){
                        position--;
                        getText((nexturl.get(position)));
                    }else {
                        position++;
                        getText((nexturl.get(position)));
                    }
                }else{
                    popupWindow.showAtLocation(findViewById(R.id.scrollView), Gravity.BOTTOM,0,0);
                }
            }
        });

    }
    public void getText(String xxx){
        final String x=xxx;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(x).get();
                    Element item=doc.getElementById("content");
                    text=item.text();
                    Log.e("0", "=" + text);
                }catch (IOException e){
                    e.printStackTrace();
                }
                Message.obtain(handler, 0).sendToTarget();
            }
        }).start();
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Log.e("0", "hhhhhhhhhhhhhh");
                    scrollView.scrollTo(0, 0);
                    textView.setText(text);
                    title.setText(titles.get(position));
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Intent myintent=new Intent("showthePOSITION");
        myintent.putExtra("test",position+"");
        localBroadcastManager.sendBroadcast(myintent);
    }
}
