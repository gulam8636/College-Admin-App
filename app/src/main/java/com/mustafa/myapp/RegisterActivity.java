package com.mustafa.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
TextView Email,Password;
Button RegisterBtn;
TextView Loginlink;
FirebaseAuth mAuth;
    ProgressBar progressBar;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Email=findViewById(R.id.etemailofRegister);
        Password=findViewById(R.id.etpasswordofregister);
        RegisterBtn=findViewById(R.id.BtnofRegister);
        Loginlink=findViewById(R.id.Loginlink);
        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressbar);
        Loginlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password;
                progressBar.setVisibility(View.VISIBLE);
             email=   String.valueOf(Email.getText());
             password=  String.valueOf(Password.getText());
             if(TextUtils.isEmpty(email)){
                 Toast.makeText(RegisterActivity.this, "Email is Empty", Toast.LENGTH_SHORT).show();
                 return;
             }
             if(TextUtils.isEmpty(password)){
                 Toast.makeText(RegisterActivity.this, "Password is Empty", Toast.LENGTH_SHORT).show();
                 return;
             }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user.
                                if (task.isSuccessful()) {

                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(RegisterActivity.this, "Authentication is successfull", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(RegisterActivity.this, "Authentication is failed", Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });
    }
}