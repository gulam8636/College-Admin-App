package com.mustafa.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import Notice.DeleteNoticeActivity;
import Notice.UploadNoticeActivity;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser UserId;
    private TextView nameShow;
    Button Logout;
    ImageView UploadEbook,AddImg,Uploadpdf;
    ImageView AddNotice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddNotice= findViewById(R.id.addNotice);
        AddImg = findViewById(R.id.addGallery);
        Uploadpdf = findViewById(R.id.addEbook);
        nameShow=findViewById(R.id.nameShow);

        auth=FirebaseAuth.getInstance();
        Logout=findViewById(R.id.logout);
        UserId = auth.getCurrentUser();
        nameShow.setText(UserId.getEmail());
        if(UserId==null){
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else
            UserId.getEmail();
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void NoticeActivity(View view){
        Toast.makeText(this, "opening gallary", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this, UploadNoticeActivity.class);
        startActivity(intent);
    }

    public void ImageActivity(View view){
        Toast.makeText(this, "opening gallary", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this, UploadImageActivity.class);
        startActivity(intent);
    }
    public void EbookActivity(View view){
        Toast.makeText(this, "opening Ebook", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,MainActivity4.class);
        startActivity(intent);
    }
    public void FacultyActivity(View view){
        Toast.makeText(this, "opening Faculty", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this, UpdateFacultyAcivity.class);
        startActivity(intent);
    }
    public void DeleteActivity(View view){
        Toast.makeText(this, "opening Delete", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(MainActivity.this, DeleteNoticeActivity.class);
        startActivity(intent);
    }


}

