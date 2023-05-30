package Faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mustafa.myapp.R;

public class AddTeacher extends AppCompatActivity {
    String name,email,post;
    Bitmap bitmap;
    private DatabaseReference reference,dBref;
private ImageView addTeacherImage;
private EditText addTeacherName,addTeacherEmail,addTeacherPost;
Button addTeacherBtn;
    String item;
    StorageReference storageReference;
    private  String uniqueKey;
   //TeacherData
    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference storage;
Spinner addTeacherClass;
ProgressDialog progressDialog;
private Uri selectedImageUri;
public String getDownloadUrl="";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);
        addTeacherImage = findViewById(R.id.addTeacherImg);
        addTeacherName = findViewById(R.id.addTeacherName);
        addTeacherEmail = findViewById(R.id.addTeacherEmail);
        addTeacherPost = findViewById(R.id.addTeacherPost);
        addTeacherClass = findViewById(R.id.addTeacherClass);
        progressDialog = new ProgressDialog(this);
        addTeacherBtn = findViewById(R.id.AddTeacherBtn);

        storageReference=FirebaseStorage.getInstance().getReference();
        storage=FirebaseDatabase.getInstance().getReference();
        reference = storage.child("Faculty");

        name=addTeacherName.getText().toString();
        email = addTeacherEmail.getText().toString();
        post=addTeacherPost.getText().toString();

        uniqueKey=reference.push().getKey();

        //Defining Categories
        String[] Class ={"Select Class","Nursery","LKG","UKG","I","II","III","IV","V","VI","VII","VIII","IX","X"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddTeacher.this, android.R.layout.simple_spinner_item,Class);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       addTeacherClass.setAdapter(adapter);

       addTeacherClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
          item=adapterView.getItemAtPosition(i).toString();
               Toast.makeText(AddTeacher.this, item, Toast.LENGTH_SHORT).show();

           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });


        // below line is used to get reference for our database.


        addTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String name=addTeacherName.getText().toString();
                String email = addTeacherEmail.getText().toString();
               String post=addTeacherPost.getText().toString();
               item=addTeacherClass.getSelectedItem().toString();
            checkValidation();

            uploadImg();
            }
        });
        addTeacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImg();
            }
        });
    }

    private void uploadImg() {
        if (selectedImageUri != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child("Faculty").child(item).child(uniqueKey);

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

                                         String   t=task.getResult().toString();


                                            InsertData(name,email,post,t,uniqueKey,item);
                                        }
                                    });
                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast.makeText(AddTeacher.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();

                                     finish();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(AddTeacher.this,
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


    private void InsertData(String name,String email,String post,String image,String key,String item) {
        TeacherData teacherData=new TeacherData(name,email,post,image,key,item);
        dBref=reference.child(item).child(uniqueKey);
        dBref.setValue(teacherData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddTeacher.this, "data added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddTeacher.this, "Fail to add data " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//        dBref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                dBref.setValue(teacherData);
//                // after adding this data we are showing toast message.
//                Toast.makeText(AddTeacher.this, "data added", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // we are displaying a failure toast message.
//                Toast.makeText(AddTeacher.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
//
////            }
//        });

    }


    private void checkValidation() {
        name=addTeacherName.getText().toString();
        email = addTeacherEmail.getText().toString();
        post=addTeacherPost.getText().toString();
        if(name.isEmpty()){
            addTeacherName.setError("name should not be empty");
            addTeacherName.requestFocus();
        }
        if(email.isEmpty()){
            addTeacherEmail.setError("name should not be empty");
            addTeacherEmail.requestFocus();
        }
        if(post.isEmpty()){
            addTeacherPost.setError("name should not be empty");
            addTeacherPost.requestFocus();
        }

    }




    private void SelectImg() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==100 && data!=null && data.getData()!=null )
        {
            selectedImageUri = data.getData();
            addTeacherImage.setImageURI(selectedImageUri);
        }
    }



}