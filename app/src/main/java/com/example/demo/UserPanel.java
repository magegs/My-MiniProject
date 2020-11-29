package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.Users;
import Privalent.Privalent;

public class UserPanel extends AppCompatActivity
{
    private TextView uname,uphno,upoints,urefperson,refid;
    private Button edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);
        uname=(TextView)findViewById(R.id.userpanel_username);
        uphno=(TextView)findViewById(R.id.userpanel_phno);
        upoints=(TextView)findViewById(R.id.userpanelpoints);
        urefperson=(TextView)findViewById(R.id.total_ref_no);
        refid=(TextView)findViewById(R.id.refercode);
        edit=(Button)findViewById(R.id.editprofile_btn);
        refid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(UserPanel.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copytext",refid.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(UserPanel.this,"COPT TO Clipboard",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference userpanel= FirebaseDatabase.getInstance().getReference().child("Users");
        userpanel.child(Privalent.currentuser.getPhoneno()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    Users users=dataSnapshot.getValue(Users.class);
                    uname.setText(users.getName());
                    uphno.setText(users.getPhoneno());
                    BonusData();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void BonusData()
    {
        DatabaseReference bonuspanel= FirebaseDatabase.getInstance().getReference().child("BonusTable");
        bonuspanel.child(Privalent.currentuser.getPhoneno()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    upoints.setText(dataSnapshot.child("points").getValue().toString());
                    urefperson.setText(dataSnapshot.child("Total_ref").getValue().toString());
                    refid.setText(dataSnapshot.child("ref_id").getValue().toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}