package com.sp.areader.view.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sp.areader.ListActivity;
import com.sp.areader.R;
import com.sp.areader.SearchActivity;
import com.sp.areader.adapter.DBHelper;
import com.sp.areader.adapter.MineAdapter;
import com.sp.areader.bean.book;
import com.sp.areader.presenter.BookshelfPresenter;
import com.sp.areader.view.IBookShelf;

import java.util.ArrayList;

/**
 * Created by my on 2016/12/10.
 */

public class BookShelf extends Fragment implements IBookShelf{
    private DBHelper dbHelper;
    private Cursor cursor;
    private MineAdapter adapter;
    private RecyclerView recyclerView;
    private HintPopupWindow hintPopupWindow;
    private int onDeletedposition;
    private book mybook;
    private BookshelfPresenter presenter=new BookshelfPresenter(this);
    //接受广播相关
    private LocalBroadcastManager localBroadcastManager;
    private LovedBookReceiver receiver;
    private IntentFilter intentFilter;
    private int tag;
    public static BookShelf newinstance(){
        BookShelf bookShelf=new BookShelf();
        return bookShelf;
    }
    @Override
    public void onCreate(Bundle s){
        super.onCreate(s);
        dbHelper=new DBHelper(getActivity());
        cursor=dbHelper.select();
        adapter=new MineAdapter(getActivity(),cursor,dbHelper);
        //接收广播
        localBroadcastManager=LocalBroadcastManager.getInstance(getActivity());
        receiver=new LovedBookReceiver();
        intentFilter=new IntentFilter();
        intentFilter.addAction("addToShelf");
        localBroadcastManager.registerReceiver(receiver,intentFilter);

        //下面的操作是初始化弹出数据
        ArrayList<String> strList = new ArrayList<>();
        strList.add("删除");
        ArrayList<View.OnClickListener> clickList = new ArrayList<>();
        View.OnClickListener clickListener0=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor.moveToPosition(onDeletedposition);
                dbHelper.delete(cursor.getInt(0));
                cursor.requery();
                handler.obtainMessage(0).sendToTarget();
                hintPopupWindow.gonePopupWindow();
            }
        };
        clickList.add(clickListener0);
        //初始化
        hintPopupWindow = new HintPopupWindow(getActivity(), strList, clickList);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstance){
        View view=inflater.inflate(R.layout.mine,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.mineshelf);
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnBookClickListener(new MineAdapter.onBookClickListener() {
            @Override
            public void onChoose(int position) {
                cursor.moveToPosition(position);
                String title=cursor.getString(1);
                String url=cursor.getString(5);
                Intent intent=new Intent(getActivity(),ListActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
        adapter.setOnBookDeletedListener(new MineAdapter.onBookDeletedListener() {
            @Override
            public void onDeleted(int position) {
                onDeletedposition=position;
                View view = linearLayoutManager.findViewByPosition(position);
                hintPopupWindow.showPopupWindow(view);
            }
        });
        return view;
    }
    @Override
    public void UpdateUI(book newbook){
        Log.e("0","受到update请求");
        int tag=0;
        Log.e("0","count="+cursor.getCount());
        for (int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            if (cursor.getString(1).equals(newbook.getBook_name())){
                tag=1;
            }
        }
        if (tag==1){
            Log.e("2333","该书已添加");
        }else {
            dbHelper.insert(newbook);
            cursor.requery();
            handler.obtainMessage(0).sendToTarget();
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message message){
            Log.e("notify","receive and boradcast and update");
            adapter.notifyDataSetChanged();
        }
    };
    class LovedBookReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent mintent){
            book lovedbook=new book();
            lovedbook.setBook_name(mintent.getStringExtra("lovedBook_name"));
            lovedbook.setBook_writter(mintent.getStringExtra("lovedBook_writer"));
            lovedbook.setBook_details(mintent.getStringExtra("lovedBook_detail"));
            lovedbook.setBook_cover(mintent.getStringExtra("lovedBook_imgurl"));
            lovedbook.setContenturl(mintent.getStringExtra("lovedBook_contenturl"));
            UpdateUI(lovedbook);
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(receiver);
    }
}
