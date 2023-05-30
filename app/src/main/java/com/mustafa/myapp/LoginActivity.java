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

public class LoginActivity extends AppCompatActivity  {
    TextView Email,Password;
    Button LoginBtn;
    TextView RegisterLink;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("name",currentUser.getDisplayName());
            startActivity(intent);
            finish();
        }
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//
        progressBar=findViewById(R.id.Progressbar);
        Email=findViewById(R.id.etemailoflogin);
        Password=findViewById(R.id.etpasswordoflogin);
        LoginBtn=findViewById(R.id.Btnoflogin);
        RegisterLink=findViewById(R.id.registerLink);
        mAuth=FirebaseAuth.getInstance();
        RegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
LoginBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String email,password;
        email=   String.valueOf(Email.getText());
        password=  String.valueOf(Password.getText());
        progressBar.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(email)){
            Toast.makeText(LoginActivity.this, "Email is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "Password is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "Login Sucessful", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(LoginActivity.this, "Please enter a valid email or password", Toast.LENGTH_SHORT).show();

                        }
                    }
                });



    }
});

    }

}