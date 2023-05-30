package com.mustafa.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadImageActivity extends AppCompatActivity {
 ImageView SelectImg,ShowImg;
 TextInputEditText Title;
 Button UploadImg;
    Uri selectedImageUri;
    private int SELECT_PICTURE=100;
    ProgressDialog progressDialog;
    FirebaseStorage storage;
    StorageReference storageReference;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        SelectImg = findViewById(R.id.SelectImg3Activity);
        ShowImg = findViewById(R.id.showImage3Activity);
        UploadImg = findViewById(R.id.uploadImgbtn3Activity);
        Title = findViewById(R.id.ImageTitle3Activity);
        progressDialog = new ProgressDialog(this);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        SelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });
        UploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
            }
        });

    }
    private void UploadImage() {
        if(selectedImageUri!=null)
// Code for showing progressDialog while uploading
            progressDialog.setTitle("Uploading...");
        progressDialog.show();
        // Defining the child of storageReference
        StorageReference ref = storageReference.child("images/" + Title.getText().toString());
        ref.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(UploadImageActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Error, Image not uploaded
                progressDialog.dismiss();
                Toast.makeText(UploadImageActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress
                        = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Uploaded " + (int)progress + "%");
            }
        });
    }
    // Select image from gallery
    private void SelectImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK ) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE && data!=null && data.getData()!=null) {
                // Get the url of the image from data
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {

                    // update the preview image in the layout
                    ShowImg.setImageURI(selectedImageUri);

                }
            }
        }
    }



}


