package Notice;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mustafa.myapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewAdapter> {
    private Context context;
    private ArrayList<NoticeData> list;

    public NoticeAdapter(Context context, ArrayList<NoticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.newsfeed_item_layout,parent,false);
        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter holder, @SuppressLint("RecyclerView") int position) {
         NoticeData currentItems=list.get(position);
         holder.Desc.setText(currentItems.getTitle());

             try {
                 if(currentItems.getImage()!=null) {
                     Picasso.get().load(currentItems.getImage()).into(holder.imageView);
                 }
             } catch (Exception e) {
                 throw new RuntimeException(e);
             }
holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage("Are you sure want to delete this notice?");
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Notice");
                        reference.child(currentItems.getUniqueKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Notice Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Notice Deletion failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                        notifyItemRemoved(position);

                    }
                }

        );
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               dialogInterface.cancel();
            }


        });
        AlertDialog dialog=null;
        try {
            dialog = builder.create();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(dialog!=null) dialog.show();

    }
});
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class  NoticeViewAdapter extends RecyclerView.ViewHolder {
private  TextView notice,Desc;
private Button deleteBtn;
private ImageView imageView;
CircleImageView image;
        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);
         notice=  itemView.findViewById(R.id.noticeTv);
         image=itemView.findViewById(R.id.CircleImg);
         deleteBtn=itemView.findViewById(R.id.deleteBtn);
         Desc=itemView.findViewById(R.id.deleteNoticeTitle);
         imageView=itemView.findViewById(R.id.deleteNoticeImage);

        }
    }

}
