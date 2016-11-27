package com.sp.areader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sp.areader.R;

import java.util.List;

/**
 * Created by my on 2016/11/9.
 */
public class ListViewAdapter extends ArrayAdapter<String> {
    private List<String> mDatas;
    private Context mContext;
    private boolean state;
    public ListViewAdapter(Context context, int layoutid, List<String> datas, boolean ss){
        super(context,layoutid,datas);
        mDatas=datas;
        state=ss;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        if (state==false){//顺序情况
            String item=getItem(position);
            View view;
            ViewHolder viewHolder;
            if (convertView==null){
                view= LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
                viewHolder=new ViewHolder();
                viewHolder.textView=(TextView)view.findViewById(R.id.item_title);
                view.setTag(viewHolder);
            }else {
                view=convertView;
                viewHolder=(ViewHolder)view.getTag();
            }
            viewHolder.textView.setText(item);
            return view;
        }else {//倒序情况
            int count=mDatas.size();
            String item=getItem(position);
            View view;
            ViewHolder viewHolder;
            if (convertView==null){
                view= LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
                viewHolder=new ViewHolder();
                viewHolder.textView=(TextView)view.findViewById(R.id.item_title);
                view.setTag(viewHolder);
            }else {
                view=convertView;
                viewHolder=(ViewHolder)view.getTag();
            }
            viewHolder.textView.setText(item);
            return view;
        }
    }
    class ViewHolder{
        TextView textView;
    }

}
