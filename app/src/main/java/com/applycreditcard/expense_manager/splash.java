package com.applycreditcard.expense_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class splash extends AppCompatActivity {

public  static  int SPLASH_TIME_OUT=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        Uri uri=getIntent().getData();
        if (uri!=null)
        {
            String path=uri.toString();
            Toast.makeText(splash.this,"path"+path,Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(splash.this,registeration.class));
                finish();

            }
        },SPLASH_TIME_OUT);
    }
}