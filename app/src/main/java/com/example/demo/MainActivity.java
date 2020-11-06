package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.Users;
import Privalent.Privalent;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private Button mregbtn,mloginbtn;
    private ProgressDialog loadbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mloginbtn=(Button)findViewById(R.id.loginbtn);
        loadbar=new ProgressDialog(this);

        Paper.init(this);
        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,login.class);
                startActivity(intent);
            }
        });
        mregbtn=(Button)findViewById(R.id.regbtn);
        mregbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,register.class);
                startActivity(intent);
            }
        });
        String userphno= Paper.book().read(Privalent.Userphnokey);
        String userpass= Paper.book().read(Privalent.Userpasskey);
        if(userphno !="" ){
            if(!TextUtils.isEmpty(userphno)){
                AllowAcess(userphno,userphno);
                loadbar.setTitle("Login Account");
                loadbar.setMessage("please waait loading...");
                loadbar.setCanceledOnTouchOutside(false);
                loadbar.show();


                
            }
        }



    }

    private void AllowAcess(final String userphno, final String userpass) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(userphno).exists()){
                    Users userdata=dataSnapshot.child("Users").child(userphno).getValue(Users.class);
                    if(userdata.getPhoneno().equals(userphno)){

                            Toast.makeText(MainActivity.this,"Please wait you are already login...!!",Toast.LENGTH_LONG).show();
                            loadbar.dismiss();
                            Intent intent=new Intent(MainActivity.this,Home.class);
                            Privalent.currentuser=userdata;
                            startActivity(intent);


                    }

                }
                else {
                    Toast.makeText(MainActivity.this,"user not register!",Toast.LENGTH_LONG).show();
                    loadbar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}