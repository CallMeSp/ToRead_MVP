package com.sp.areader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by my on 2016/11/20.
 */
public class splashActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        try {
            Thread.sleep(1000);
        }catch (InterruptedException s){
            s.printStackTrace();
        }
        startActivity(intent);
        finish();
    }
}