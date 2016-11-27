package com.sp.areader.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sp.areader.R;
import com.sp.areader.bean.book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by my on 2016/11/14.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Myholder>{
    private ArrayList<String> mtitle=new ArrayList<String>(),mwritter=new ArrayList<String>(),mdetails=new ArrayList<String>();
    private ArrayList<String> mcover=new ArrayList<String>();
    private LayoutInflater inflater;
    private Context mcontext;
    private ArrayList<book> mybook=new ArrayList<book>();
    private onBookClickListener mbooklistener;
    private onBooklovedListener mbooklovedlistener;
    public SearchAdapter(Context context,ArrayList<book> mbook){
        mybook=mbook;
        mcontext=context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getItemCount(){
        return mybook.size();
    }
    class Myholder extends RecyclerView.ViewHolder{
        TextView t_title,t_writter,t_details;
        ImageView bookcover;
        public Myholder(View view){
            super(view);
            t_title=(TextView)view.findViewById(R.id.search_title);
            t_writter=(TextView)view.findViewById(R.id.search_writter);
            t_details=(TextView)view.findViewById(R.id.search_details);
            bookcover=(ImageView)view.findViewById(R.id.book_cover);
        }
    }
    @Override
    public Myholder onCreateViewHolder(ViewGroup parent,int type){
        View view=inflater.inflate(R.layout.bookview_in_search,parent,false);
        Myholder myholder=new Myholder(view);
        return myholder;
    }
    @Override
    public void onBindViewHolder(Myholder myholder,final int position){
        myholder.t_title.setText(mybook.get(position).getBook_name());
        myholder.t_writter.setText(mybook.get(position).getBook_writter());
        myholder.t_details.setText(mybook.get(position).getBook_details());
        Picasso.with(myholder.itemView.getContext())
                .load(mybook.get(position).getBook_cover())
                .centerInside()
                .fit()
                .into(myholder.bookcover);
        //if (mcover.size()==mtitle.size()){myholder.bookcover.setImageBitmap(mcover.get(position));}
        if( mbooklistener!= null){
            myholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mbooklistener.onChoose(position);
                }
            });
            myholder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mbooklovedlistener.onLoved(position);
                    return true;
                }
            });
        }
    }
    public interface onBookClickListener{
        public void onChoose(int position);
    }
    public void setOnBookClickListener(onBookClickListener bookClickListener){
        this.mbooklistener=bookClickListener;
    }
    public interface onBooklovedListener{
        public void onLoved(int position);
    }
    public void setOnBooklovedListener(onBooklovedListener booklovedListener){
        this.mbooklovedlistener=booklovedListener;
    }
}
