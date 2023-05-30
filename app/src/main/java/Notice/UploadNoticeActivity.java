package Notice;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mustafa.myapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UploadNoticeActivity extends AppCompatActivity {
    ImageView AddImage;
    ImageView ShowImg;
    Bitmap bitmap;
    String DownloadUrl="";
    private DatabaseReference reference;
    private StorageReference storageReference;
    private EditText NoticeTitle;
    private Button UploadNoticeBtn;
    String uniqueKey;
    private DatabaseReference storage;
    private ProgressDialog pd;
    ActivityResultLauncher<String> galleryLauncher;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        storage= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
        reference = storage.child("Notice");
        super.onCreate(savedInstanceState);
        pd =new ProgressDialog(this);
        setContentView(R.layout.activity_upload_notice);
        ShowImg = findViewById(R.id.showimg);
        NoticeTitle=findViewById(R.id.noticeTitle);
        AddImage = findViewById(R.id.addcamera);
        UploadNoticeBtn=findViewById(R.id.uploadntcbtn);
        uniqueKey=reference.push().getKey();
        UploadNoticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NoticeTitle.getText().toString().isEmpty())
                {
                    NoticeTitle.setError("Empty");
                    NoticeTitle.requestFocus();
                }
                else if(bitmap==null){
                    uploadData();

                }else{
                    uploadImage();
                }
            }

            private void uploadData() {

                Calendar calForDate=Calendar.getInstance();
                String title=NoticeTitle.getText().toString();
                SimpleDateFormat currentdate=new SimpleDateFormat("dd-MM-yy");
                String date=currentdate.format(calForDate.getTime());

                Calendar calForTime=Calendar.getInstance();
                SimpleDateFormat currentTime=new SimpleDateFormat("hh:mm a");

                String time=currentTime.format(calForTime.getTime());
                NoticeData noticeData=new NoticeData(title,DownloadUrl,date,time,uniqueKey);
                assert uniqueKey != null;
                reference.child(uniqueKey).setValue(noticeData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void eVoid) {
                        pd.dismiss();
                        Toast.makeText(UploadNoticeActivity.this, "Notice uploaded", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(UploadNoticeActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            private void uploadImage() {
                pd.setMessage("Uploading.....");
                pd.show();
                ByteArrayOutputStream baos= new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,50 ,baos);
                byte[] finalimg = baos.toByteArray();
                StorageReference filePath = storageReference.child("Notice").child(uniqueKey);
             final   UploadTask uploadtask=filePath.putBytes(finalimg);
       uploadtask.addOnCompleteListener(UploadNoticeActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
    @Override
    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
        if(task.isSuccessful()){
            uploadtask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DownloadUrl =String.valueOf(uri);
                            uploadData();
                        }
                    });
                }
            });
        }
        else {
            pd.dismiss();
            Toast.makeText(UploadNoticeActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
        }
    }
});

            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {

                try {
                     bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ShowImg.setImageBitmap(bitmap);
                UploadPicture();
            }

            private void UploadPicture() {

                // Create a reference to 'images/mountains.jpg'
                StorageReference mountainImagesRef = storageReference.child("image.jpg");

// While the file names are the same, the references point to different files
                mountainImagesRef.getName().equals(mountainImagesRef.getName());    // true
                mountainImagesRef.equals(mountainImagesRef.getPath());

            }
        });
        AddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryLauncher.launch("image/*");

            }
        });
    }


}