package com.mustafa.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Faculty.AddTeacher;
import Faculty.TeacherAdapter;
import Faculty.TeacherData;

public class UpdateFacultyAcivity extends AppCompatActivity {
FloatingActionButton Fab;

private RecyclerView Nursery,LKG,UKG,StdOne,StdTwo,StdThree,StdFour,StdFive,StdSix,StdSeven,StdEight,StdNine,StdTen;
private LinearLayout NurseryNoData,LKGNoData,UKGNoData,StdOneNoData,StdTwoNoData,StdThreeNoData,StdFourNoData,StdFiveNoData,StdSixNoData,StdSevenNoData,
        StdEightNoData,StdNineNoData,StdTenNoData;
private TeacherAdapter adapter;
private List<TeacherData> list1,list2,list3,list4,list5,list6,list7,list8,list9,list10,list11,list12,list13;
private DatabaseReference reference,dBRef;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);

        //DataFinding
        Nursery = findViewById(R.id.Nursery);
        NurseryNoData = findViewById(R.id.NurseryNoData);
        LKG = findViewById(R.id.LKG);
        LKGNoData = findViewById(R.id.LKGnoData);
        UKG = findViewById(R.id.UKG);
        UKGNoData = findViewById(R.id.UKGnoData);
        StdOne = findViewById(R.id.StdOneDepartment);
        StdOneNoData = findViewById(R.id.StdOneNoData);
        StdTwo = findViewById(R.id.StdTwoDepartment);
        StdTwoNoData = findViewById(R.id.StdTwoNoData);
        StdThree = findViewById(R.id.StdThreeDepartment);
        StdThreeNoData = findViewById(R.id.StdThreeNoData);
        StdFour = findViewById(R.id.StdFourDepartment);
        StdFourNoData = findViewById(R.id.StdFourNoData);
        StdFive = findViewById(R.id.StdFiveDepartment);
        StdFiveNoData = findViewById(R.id.StdFiveNoData);
        StdSix =findViewById(R.id.StdSixDepartment);
        StdSixNoData = findViewById(R.id.StdSixNoData);
        StdSeven = findViewById(R.id.StdSeven);
        StdSevenNoData = findViewById(R.id.StdSevenNoData);
        StdEight = findViewById(R.id.StdEightDepartment);
        StdEightNoData = findViewById(R.id.StdEightNoData);
        StdNine = findViewById(R.id.StdNineDepartment);
        StdNineNoData = findViewById(R.id.StdNineNoData);
        StdTen = findViewById(R.id.StdTenDepartment);
        StdTenNoData = findViewById(R.id.StdTenNoData);
        reference = FirebaseDatabase.getInstance().getReference().child("Faculty");

        // Calling Methos
        Nursery();
        LKG();
        UKG();
        StdOne();
        StdTwo();
        StdThree();
        StdFour();
        StdFive();
        StdSix(); StdSeven();StdEight();StdNine();StdTen();

        Fab=findViewById(R.id.fab);
        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateFacultyAcivity.this, AddTeacher.class));
            }
        });
    }

    private void Nursery() {
        dBRef = reference.child("Nursery");
        dBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
           list1 = new ArrayList<>();
           if(!snapshot.exists()){
               NurseryNoData.setVisibility(View.VISIBLE);
               Nursery.setVisibility(View.GONE);
           }else
               for(DataSnapshot snapshot1 : snapshot.getChildren()){
                   TeacherData data = snapshot1.getValue(TeacherData.class);
                   data.setUniqueKey(snapshot1.getKey());
                   list1.add(data);
               }
                if (list1!=null && snapshot.exists()) {
                    Nursery.setHasFixedSize(true);
                    Nursery.setLayoutManager(new LinearLayoutManager(UpdateFacultyAcivity.this));
                    adapter = new TeacherAdapter(list1, UpdateFacultyAcivity.this, "Nursery");
                    Nursery.setAdapter(adapter);
                    NurseryNoData.setVisibility(View.GONE);
                    Nursery.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyAcivity.this, "Error something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void LKG() {
        dBRef = reference.child("LKG");
        dBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<>();
                if(!snapshot.exists()){
                    LKGNoData.setVisibility(View.VISIBLE);
                    LKG.setVisibility(View.GONE);
                }else
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        //(snapshot1.getKey());
                        //("LKGDTATA", data.getUniqueKey());
                        list2.add(data);
                    }
                    if (list2!=null && snapshot.exists()) {
                        LKG.setHasFixedSize(true);
                        LKG.setLayoutManager(new LinearLayoutManager(UpdateFacultyAcivity.this));
                        adapter = new TeacherAdapter(list2, UpdateFacultyAcivity.this, "LKG");
                        LKG.setAdapter(adapter);
                        LKGNoData.setVisibility(View.GONE);
                        LKG.setVisibility(View.VISIBLE);
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyAcivity.this, "Error something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void UKG() {
        dBRef = reference.child("UKG");
        dBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3 = new ArrayList<>();
                if(!snapshot.exists()){
                    UKGNoData.setVisibility(View.VISIBLE);
                    UKG.setVisibility(View.GONE);
                }else
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        //(snapshot1.getKey());
                        list3.add(data);
                    }
                if (list3!=null && snapshot.exists()) {
                    UKG.setHasFixedSize(true);
                    UKG.setLayoutManager(new LinearLayoutManager(UpdateFacultyAcivity.this));
                    adapter = new TeacherAdapter(list3, UpdateFacultyAcivity.this, "UKG");
                    UKG.setAdapter(adapter);
                    UKGNoData.setVisibility(View.GONE);
                    UKG.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyAcivity.this, "Error something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void StdOne() {
        dBRef = reference.child("I");
        dBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list4 = new ArrayList<>();
                if(!snapshot.exists()){
                    StdOneNoData.setVisibility(View.VISIBLE);
                    StdOne.setVisibility(View.GONE);
                }else
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        //(snapshot1.getKey());
                        list4.add(data);
                    }
                if (list4!=null && snapshot.exists()) {
                    StdOne.setHasFixedSize(true);
                    StdOne.setLayoutManager(new LinearLayoutManager(UpdateFacultyAcivity.this));
                    adapter = new TeacherAdapter(list4, UpdateFacultyAcivity.this, "I");
                    StdOne.setAdapter(adapter);
                    StdOneNoData.setVisibility(View.GONE);
                    StdOne.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyAcivity.this, "Error something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void StdTwo() {
        dBRef = reference.child("II");
        dBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list5 = new ArrayList<>();
                if(!snapshot.exists()){
                    StdTwoNoData.setVisibility(View.VISIBLE);
                    StdTwo.setVisibility(View.GONE);
                }else
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        //(snapshot1.getKey());
                        list5.add(data);
                    }
                if (list5!=null && snapshot.exists()) {
                    StdTwo.setHasFixedSize(true);
                    StdTwo.setLayoutManager(new LinearLayoutManager(UpdateFacultyAcivity.this));
                    adapter = new TeacherAdapter(list5, UpdateFacultyAcivity.this, "II");
                    StdTwo.setAdapter(adapter);
                    StdTwoNoData.setVisibility(View.GONE);
                    StdTwo.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyAcivity.this, "Error something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void StdThree() {
        dBRef = reference.child("III");
        dBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list6 = new ArrayList<>();
                if(!snapshot.exists()){
                    StdThreeNoData.setVisibility(View.VISIBLE);
                    StdThree.setVisibility(View.GONE);
                }else
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        //(snapshot1.getKey());
                        list6.add(data);
                    }
                if (list6!=null && snapshot.exists()) {
                    StdThree.setHasFixedSize(true);
                    StdThree.setLayoutManager(new LinearLayoutManager(UpdateFacultyAcivity.this));
                    adapter = new TeacherAdapter(list6, UpdateFacultyAcivity.this, "III");
                    StdThree.setAdapter(adapter);
                    StdThreeNoData.setVisibility(View.GONE);
                    StdThree.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyAcivity.this, "Error something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void StdFour() {
        dBRef = reference.child("IV");
        dBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list7 = new ArrayList<>();
                if(!snapshot.exists()){
                    StdFourNoData.setVisibility(View.VISIBLE);
                    StdFour.setVisibility(View.GONE);
                }else
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        //(snapshot1.getKey());
                        list7.add(data);
                    }
                if (list7!=null && snapshot.exists()) {
                    StdFour.setHasFixedSize(true);
                    StdFour.setLayoutManager(new LinearLayoutManager(UpdateFacultyAcivity.this));
                    adapter = new TeacherAdapter(list7, UpdateFacultyAcivity.this, "IV");
                    StdFour.setAdapter(adapter);
                    StdFourNoData.setVisibility(View.GONE);
                    StdFour.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyAcivity.this, "Error something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void StdFive() {
        dBRef = reference.child("V");
        dBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list8 = new ArrayList<>();
                if(!snapshot.exists()){
                    StdFiveNoData.setVisibility(View.VISIBLE);
                    StdFive.setVisibility(View.GONE);
                }else
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        //(snapshot1.getKey());
                        list8.add(data);
                    }
                if (list8!=null && snapshot.exists()) {
                    StdFive.setHasFixedSize(true);
                    StdFive.setLayoutManager(new LinearLayoutManager(UpdateFacultyAcivity.this));
                    adapter = new TeacherAdapter(list8, UpdateFacultyAcivity.this, "V");
                    StdFive.setAdapter(adapter);
                    StdFiveNoData.setVisibility(View.GONE);
                    StdFive.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyAcivity.this, "Error something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void StdSix() {
        dBRef = reference.child("VI");
        dBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list9 = new ArrayList<>();
                if(!snapshot.exists()){
                    StdSixNoData.setVisibility(View.VISIBLE);
                    StdSix.setVisibility(View.GONE);
                }else
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        //(snapshot1.getKey());
                        list9.add(data);
                    }
                if (list9!=null && snapshot.exists()) {
                    StdSix.setHasFixedSize(true);
                    StdSix.setLayoutManager(new LinearLayoutManager(UpdateFacultyAcivity.this));
                    adapter = new TeacherAdapter(list9, UpdateFacultyAcivity.this, "VI");
                    StdSix.setAdapter(adapter);
                    StdSixNoData.setVisibility(View.GONE);
                    StdSix.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyAcivity.this, "Error something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void StdSeven() {
        dBRef = reference.child("VII");
        dBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list10 = new ArrayList<>();
                if(!snapshot.exists()){
                    StdSevenNoData.setVisibility(View.VISIBLE);
                    StdSeven.setVisibility(View.GONE);
                }else
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        //(snapshot1.getKey());
                        list10.add(data);
                    }
                if (list10!=null && snapshot.exists()) {
                    StdSeven.setHasFixedSize(true);
                    StdSeven.setLayoutManager(new LinearLayoutManager(UpdateFacultyAcivity.this));
                    adapter = new TeacherAdapter(list10, UpdateFacultyAcivity.this, "VII");
                    StdSeven.setAdapter(adapter);
                    StdSevenNoData.setVisibility(View.GONE);
                    Nursery.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyAcivity.this, "Error something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void StdEight() {
        dBRef = reference.child("VIII");
        dBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list11 = new ArrayList<>();
                if (!snapshot.exists()) {
                    StdEightNoData.setVisibility(View.VISIBLE);
                    StdEight.setVisibility(View.GONE);
                } else
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        //(snapshot1.getKey());
                        list11.add(data);
                    }
                if (list11!=null && snapshot.exists()) {
                    StdEight.setHasFixedSize(true);
                    StdEight.setLayoutManager(new LinearLayoutManager(UpdateFacultyAcivity.this));
                    adapter = new TeacherAdapter(list11, UpdateFacultyAcivity.this, "VIII");
                    StdEight.setAdapter(adapter);
                    StdEightNoData.setVisibility(View.GONE);
                    StdEight.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyAcivity.this, "Error something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void StdNine() {
        dBRef = reference.child("IX");
        dBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list12 = new ArrayList<>();
                if(!snapshot.exists()){
                    StdNineNoData.setVisibility(View.VISIBLE);
                    StdNine.setVisibility(View.GONE);
                }else
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        //(snapshot1.getKey());
                        list12.add(data);
                    }
                if (list12!=null && snapshot.exists()) {
                    StdNine.setHasFixedSize(true);
                    StdNine.setLayoutManager(new LinearLayoutManager(UpdateFacultyAcivity.this));
                    adapter = new TeacherAdapter(list12, UpdateFacultyAcivity.this, "IX");
                    StdNine.setAdapter(adapter);
                    StdNineNoData.setVisibility(View.GONE);
                    StdNine.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyAcivity.this, "Error something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void StdTen() {
        dBRef = reference.child("X");
        dBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list13 = new ArrayList<>();
                if(!snapshot.exists()){
                    StdTenNoData.setVisibility(View.VISIBLE);
                    StdTen.setVisibility(View.GONE);
                }else
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        //(snapshot1.getKey());
                        list13.add(data);
                    }
                if (list13!=null && snapshot.exists()) {
                    StdTen.setHasFixedSize(true);
                    StdTen.setLayoutManager(new LinearLayoutManager(UpdateFacultyAcivity.this));
                    adapter = new TeacherAdapter(list13, UpdateFacultyAcivity.this, "X");
                    StdTen.setAdapter(adapter);
                    StdTenNoData.setVisibility(View.GONE);
                    StdTen.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyAcivity.this, "Error something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

}