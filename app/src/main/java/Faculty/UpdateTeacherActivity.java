package Faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mustafa.myapp.R;
import com.mustafa.myapp.UpdateFacultyAcivity;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class UpdateTeacherActivity extends AppCompatActivity {
private ImageView updateTeacherImage;
private EditText updateTeacherName,updateTeacherEmail,updateTeacherPost;
private Button updateTeacherBtn,deleteTeacherBtn;
private StorageReference storageReference;
private DatabaseReference reference;
  private   DatabaseReference storage;
    String   t;
   private String category,uniqueKey;


 private   Uri selectedImageUri;
private String name,email,image,post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher);
        updateTeacherName = findViewById(R.id.updateTeacherName);
        updateTeacherEmail = findViewById(R.id.updateTeacherEmail);
        updateTeacherPost = findViewById(R.id.updateTeacherPost);
        updateTeacherBtn = findViewById(R.id.updateTeacherBtn);
        deleteTeacherBtn = findViewById(R.id.DeleteTeacherBtn);
        updateTeacherImage = findViewById(R.id.updateTeacherImage);
        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        image=getIntent().getStringExtra("image");
        post=getIntent().getStringExtra("post");
        category =getIntent().getStringExtra("category");
        uniqueKey=getIntent().getStringExtra("key");
        Log.d("UPDATEUNIQUE",uniqueKey);

        storageReference=FirebaseStorage.getInstance().getReference();
        storage=FirebaseDatabase.getInstance().getReference();
        reference = storage.child("Faculty");


        //Definig Class
        //Class

        try {
            Picasso.get().load(image).into(updateTeacherImage);
        } finally {

        }

        updateTeacherName.setText(name);
        updateTeacherEmail.setText(email);
        updateTeacherPost.setText(post);
        updateTeacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImg();
            }
        });

        updateTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name=updateTeacherName.getText().toString();
                email=updateTeacherEmail.getText().toString();
                post=updateTeacherPost.getText().toString();
                //checkValidation();
              if(selectedImageUri==null){

                  updateData("");
              }

                uploadImage();
            }
        });
        deleteTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });
    }

    private void deleteData() {
        reference.child(category).child(uniqueKey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(UpdateTeacherActivity.this, "Teacher deleted successfully", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(UpdateTeacherActivity.this, UpdateFacultyAcivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateTeacherActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void checkValidation() {
//        if(name.isEmpty()){
//            updateTeacherName.setError("Please Enter Name");
//            updateTeacherName.requestFocus();
//        }
//        else if(email.isEmpty()){
//            updateTeacherEmail.setError("Please Enter Name");
//            updateTeacherEmail.requestFocus();
//        }
//        else if(post.isEmpty()){
//            updateTeacherPost.setError("Please Enter Name");
//            updateTeacherPost.requestFocus();
//        }
//
//
//         else {
//            uploadImage();
//        }
//
//    }

    private void updateData(String s) {
        HashMap hp=new HashMap();
        hp.put("name",name);
        hp.put("email",email);
        hp.put("post",post);
        if (s != null && s != "") hp.put("image",s);
        hp.put("uniqueKey",uniqueKey);

        reference.child(category).child(uniqueKey).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(UpdateTeacherActivity.this, "Teacher updated successfully", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(UpdateTeacherActivity.this, UpdateFacultyAcivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateTeacherActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();}
        });
    }

    private void uploadImage() {
        if (selectedImageUri != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            TeacherData teacherData=new TeacherData();
            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child("Faculty").child(category).child(uniqueKey);

            // adding listeners on upload
            // or failure of image
            ref.putFile(selectedImageUri)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    Task<Uri> downloadUrl=taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {

                                            t=task.getResult().toString();
                                            updateData(t);
                                        }
                                    });
                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast.makeText(UpdateTeacherActivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();

                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(UpdateTeacherActivity.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }

    private void SelectImg() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==10 && data!=null && data.getData()!=null )
        {
            selectedImageUri = data.getData();
            updateTeacherImage.setImageURI(selectedImageUri);
        }
    }

}