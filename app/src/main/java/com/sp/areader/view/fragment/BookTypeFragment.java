package com.sp.areader.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
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
import com.sp.areader.adapter.SearchAdapter;
import com.sp.areader.bean.book;
import com.sp.areader.presenter.InflatePresnter;
import com.sp.areader.view.IBookTypeFragment;

import java.util.ArrayList;

/**
 * Created by my on 2016/12/11.
 */

public class BookTypeFragment extends Fragment implements IBookTypeFragment{
    private String baseurl="http://www.37zw.com/xiaoshuo";
    private String nexturl="";
    private RecyclerView recyclerView;
    SearchAdapter adapter;
    InflatePresnter presnter;
    int longClickposition;
    private HintPopupWindow hintPopupWindow;
    private LocalBroadcastManager addtoBookShelf;
    private ArrayList<book> mybooks=new ArrayList<book>();
    public static BookTypeFragment newinstance(String url){
        BookTypeFragment onlineShelf=new BookTypeFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("contenturl",url);
        onlineShelf.setArguments(bundle);
        return onlineShelf;
    }
    @Override
    public void onCreate(Bundle s){
        super.onCreate(s);
        if (getArguments()!=null){
            nexturl=getArguments().getSerializable("contenturl").toString();
        }
        addtoBookShelf=LocalBroadcastManager.getInstance(getActivity());
        Log.e("onCreate",baseurl+nexturl);
        presnter=new InflatePresnter(this);
        adapter=new SearchAdapter(getActivity(),mybooks);
        presnter.getlist(baseurl+nexturl);
        //下面的操作是初始化弹出数据
        ArrayList<String> strList = new ArrayList<>();
        strList.add("加入书架");
        strList.add("取消");
        ArrayList<View.OnClickListener> clickList = new ArrayList<>();
        View.OnClickListener clickListener0=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent("addToShelf");
                myintent.putExtra("lovedBook_name",mybooks.get(longClickposition).getBook_name());
                myintent.putExtra("lovedBook_writer",mybooks.get(longClickposition).getBook_writter());
                myintent.putExtra("lovedBook_detail",mybooks.get(longClickposition).getBook_details());
                myintent.putExtra("lovedBook_imgurl",mybooks.get(longClickposition).getBook_cover());
                myintent.putExtra("lovedBook_contenturl",mybooks.get(longClickposition).getContenturl());
                addtoBookShelf.sendBroadcast(myintent);
                Log.e("执行","broadcast");
                hintPopupWindow.gonePopupWindow();
            }
        };
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), longClickposition+"点击事件触发"+mybooks.get(longClickposition).getBook_name(), Toast.LENGTH_SHORT).show();
                hintPopupWindow.gonePopupWindow();
            }
        };
        clickList.add(clickListener0);
        clickList.add(clickListener);
        //初始化
        hintPopupWindow = new HintPopupWindow(getActivity(), strList, clickList);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View view=inflater.inflate(R.layout.online_fragment,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.online_recyclerView);
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnBookClickListener(new SearchAdapter.onBookClickListener() {
            @Override
            public void onChoose(int position) {
                Intent intent=new Intent(getActivity(), ListActivity.class);
                intent.putExtra("title",mybooks.get(position).getBook_name());
                intent.putExtra("url",mybooks.get(position).getContenturl());
                startActivity(intent);
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
        return view;
    }
    @Override
    public void updateUI(ArrayList<book> books){
        mybooks.clear();
        mybooks.addAll(books);
        handler.obtainMessage(0).sendToTarget();
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message message){
            Log.e("notify","receive and boradcast and update");
            adapter.notifyDataSetChanged();
        }
    };

}
