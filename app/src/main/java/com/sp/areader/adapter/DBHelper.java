package com.sp.areader.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sp.areader.bean.book;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by my on 2016/10/23.
 */
public class DBHelper extends SQLiteOpenHelper {
    private final static String DB_NAME="my.db";
    private final static int DB_VERSION=1;
    private final  static String TABLE_NAME="info";
    private final static String ID="_id";
    SQLiteDatabase database=getWritableDatabase();
    public DBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL("create table " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,writer TEXT,detail TEXT,imgurl TEXT,contenturl TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public long insert(book mybook){
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",mybook.getBook_name());
        contentValues.put("writer",mybook.getBook_writter());
        contentValues.put("detail",mybook.getBook_details());
        contentValues.put("imgurl",mybook.getBook_cover());
        contentValues.put("contenturl",mybook.getContenturl());
        //获取系统时间
        SimpleDateFormat formatter=new SimpleDateFormat("yy-MM-dd HH:mm");
        Date curDate =  new Date(System.currentTimeMillis());
        String time=formatter.format(curDate);
        long row=database.insert(TABLE_NAME,null,contentValues);
        return row;
    }
    public void delete(int _id){
        database.delete(TABLE_NAME, ID + "=?", new String[]{Integer.toString(_id)});
    }
    public Cursor select(){
        Cursor cursor=database.query(TABLE_NAME,null,null,null,null,null,null);
        return cursor;
    }
}