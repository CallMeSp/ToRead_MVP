package com.sp.areader.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sp.areader.R;
import com.squareup.picasso.Picasso;

/**
 * Created by my on 2016/12/10.
 */

public class MineAdapter extends RecyclerView.Adapter<MineAdapter.MyViewholder>{
    private Cursor cursor;
    private DBHelper dbHelper;
    private LayoutInflater inflater;
    private Context context;
    private onBookClickListener clickListener;
    private onBookDeletedListener deletedListener;
    public MineAdapter(Context context,Cursor cursor,DBHelper dbHelper){
        this.cursor=cursor;
        this.dbHelper=dbHelper;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getItemCount(){
        return cursor.getCount();
    }
    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent,int viewType){
        View view=inflater.inflate(R.layout.bookview_in_search,parent,false);
        MyViewholder viewholder=new MyViewholder(view);
        return viewholder;
    }
    @Override
    public void onBindViewHolder(MyViewholder myViewholder,final int position){
        cursor.moveToPosition(position);
        myViewholder.t_title.setText(cursor.getString(1));
        myViewholder.t_writter.setText(cursor.getString(2));
        myViewholder.t_details.setText(cursor.getString(3));
        Picasso.with(myViewholder.itemView.getContext())
                .load(cursor.getString(4))
                .centerInside()
                .fit()
                .into(myViewholder.bookcover);
        if( clickListener!= null&deletedListener!=null){
            myViewholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onChoose(position);
                }
            });
            myViewholder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    deletedListener.onDeleted(position);
                    return true;
                }
            });
        }
    }
    class MyViewholder extends RecyclerView.ViewHolder{
        TextView t_title,t_writter,t_details;
        ImageView bookcover;
        public MyViewholder(View view){
            super(view);
            t_title=(TextView)view.findViewById(R.id.search_title);
            t_writter=(TextView)view.findViewById(R.id.search_writter);
            t_details=(TextView)view.findViewById(R.id.search_details);
            bookcover=(ImageView)view.findViewById(R.id.book_cover);
        }
    }
    public interface onBookClickListener{
        public void onChoose(int position);
    }
    public void setOnBookClickListener(onBookClickListener bookClickListener){
        this.clickListener=bookClickListener;
    }
    public interface onBookDeletedListener{
        public void onDeleted(int position);
    }
    public void setOnBookDeletedListener(onBookDeletedListener booklovedListener){
        this.deletedListener=booklovedListener;
    }
}
