package com.sp.areader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Administrator on 2017/2/21.
 */

public class BaseActivity extends AppCompatActivity{
    private static final String TAG = "BaseActivity";
    public static Stack<String> stacks=new Stack<>();
    private static long last_time=0;
    private long delta=0;
    @Override
    protected void onCreate(Bundle s){
        String str=this.getClass().getName();
        if (!stacks.contains(str)&&!str.equals("com.sp.areader.splashActivity")){
            Log.e(TAG,str );
            stacks.push(str);
            Log.e(TAG, "onCreate: "+stacks.size() );
        }
        super.onCreate(s);
    }
    @Override
    protected void onDestroy(){
        long time=System.currentTimeMillis();
        if (last_time==0){
            last_time=time;
        }else {
            delta=time-last_time;
            last_time=time;
        }
        if (delta<50&delta!=0){
            Log.e(TAG, "触发效果" );
        }
        String x=stacks.pop();
        Log.e(TAG, "onDestroy: "+x +" time:"+delta);
        super.onDestroy();
    }
    public static boolean test(){
        return true;
    }
}
