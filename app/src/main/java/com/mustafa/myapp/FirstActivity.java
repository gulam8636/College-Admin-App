package com.mustafa.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.google.firebase.database.Transaction;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//       requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_first);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(FirstActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}