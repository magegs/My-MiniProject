package com.example.demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ReferenceRegister extends AppCompatActivity {
    private EditText refkey;
    private String cust_id = "";
    private Button skip, add;
    private ProgressDialog loadbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_register);
        loadbar = new ProgressDialog(this);
        refkey = (EditText) findViewById(R.id.referencekey);
        cust_id = getIntent().getStringExtra("cust_phno");
        skip = (Button) findViewById(R.id.skip);
        add = (Button) findViewById(R.id.addref);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidRefno();
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReferenceRegister.this, login.class);
                startActivity(intent);
            }
        });


    }

    private void ValidRefno() {
        String refno = refkey.getText().toString();
        if (TextUtils.isEmpty(refno)) {
            Toast.makeText(this, "Enter the Reference Key...", Toast.LENGTH_SHORT).show();
        } else {
            loadbar.setTitle("create Account");
            loadbar.setMessage("please waait loading...");
            loadbar.setCanceledOnTouchOutside(false);
            loadbar.show();
            AddRef(refno);
        }
    }

    private void AddRef(final String refno) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("reference").child(refno).exists()) {
                    UpdateAddRef(refno);
                } else {
                    Toast.makeText(ReferenceRegister.this, "This" + refno + "Invalid..!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ReferenceRegister.this, ReferenceRegister.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        RootRef.addListenerForSingleValueEvent(valueEventListener);
        RootRef.removeEventListener(valueEventListener);

    }

    private void UpdateAddRef(final String refno) {
        final DatabaseReference Updateref;
        Updateref = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> usermap = new HashMap<>();
        usermap.put("jp_id", cust_id);
        usermap.put("ref_id", refno);
        Updateref.child("reference").child(refno).child(cust_id).updateChildren(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    UpdatePoints(refno);

                }
            }
        });
    }

    private void UpdatePoints(String refno) {

        DatabaseReference bonustable = FirebaseDatabase.getInstance().getReference().child("reference").child(refno);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String refpernum = dataSnapshot.child("ref_num").getValue().toString();
                UpdateRefPoints(refpernum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        bonustable.addListenerForSingleValueEvent(valueEventListener);
        bonustable.removeEventListener(valueEventListener);


    }

    private void UpdateRefPoints(String refpernum) {
        final DatabaseReference PointTable = FirebaseDatabase.getInstance().getReference().child("BonusTable").child(refpernum);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String currentpoint = dataSnapshot.child("points").getValue().toString();
                int a = Integer.parseInt(currentpoint);
                int b = 50;
                final int pointtablevalue = a + b;


                String Bonuscust_id = dataSnapshot.child("cust_id").getValue().toString();
                UpdateFinal(Bonuscust_id, pointtablevalue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        PointTable.addListenerForSingleValueEvent(valueEventListener);
        PointTable.removeEventListener(valueEventListener);
    }

    private void UpdateFinal(String bonuscust_id, int pointtablevalue) {
        final DatabaseReference Updaterefpoint = FirebaseDatabase.getInstance().getReference();
        Updaterefpoint.child("BonusTable").child(bonuscust_id).child("points").setValue(pointtablevalue).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ReferenceRegister.this, "Register Successful!", Toast.LENGTH_LONG).show();
                    loadbar.dismiss();
                    Intent intent = new Intent(ReferenceRegister.this, login.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(ReferenceRegister.this, "error Successful!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
