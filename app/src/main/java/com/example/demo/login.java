package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Privalent.Privalent;
import io.paperdb.Paper;

public class login extends AppCompatActivity {
    private EditText numb,pass;
    private Button login;
    private ProgressDialog loadbar;
    private String parentDbName = "Users";
    private String ref_key,phoneno;
    private CheckBox checkrem;
    private TextView Adminlink,NotAdminlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        numb=(EditText)findViewById(R.id.loginmail);
        pass=(EditText)findViewById(R.id.loginpass);

        login=(Button)findViewById(R.id.login1btn);
        loadbar=new ProgressDialog(this);
        Adminlink=(TextView) findViewById(R.id.adminlink);
        NotAdminlink=(TextView) findViewById(R.id.notadminlink);

        checkrem=(CheckBox)findViewById(R.id.remember);
        Paper.init(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
        Adminlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setText("Admin Login");
                Adminlink.setVisibility(View.INVISIBLE);
                NotAdminlink.setVisibility(View.VISIBLE);
                checkrem.setVisibility(View.INVISIBLE );

                parentDbName="Admins";

            }
        });
        NotAdminlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setText("Login");
                Adminlink.setVisibility(View.VISIBLE);
                NotAdminlink.setVisibility(View.INVISIBLE);
                checkrem.setVisibility(View.VISIBLE );

                parentDbName="Users";
            }
        });
    }

    private void LoginUser() {

        phoneno=numb.getText().toString();
        String password=pass.getText().toString();


        if(TextUtils.isEmpty(phoneno)){
            Toast.makeText(this,"Please Enter the Phone number...",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please Enter the password...",Toast.LENGTH_SHORT).show();
        }
        else{
            loadbar.setTitle("Login Account");
            loadbar.setMessage("please waait loading...");
            loadbar.setCanceledOnTouchOutside(false);
            loadbar.show();
            AllowAccessToAccount(phoneno,password);
        }
    }



    private void AllowAccessToAccount(final String phoneno, final String password) {
        if(checkrem.isChecked()){
            Paper.book().write(Privalent.Userphnokey,phoneno);
            Paper.book().write(Privalent.Userpasskey,password);
        }
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDbName).child(phoneno).exists()){
                    Users userdata=dataSnapshot.child(parentDbName).child(phoneno).getValue(Users.class);
                    if(userdata.getPhoneno().equals(phoneno)){
                        if(userdata.getPassword().equals(password))
                        {
                               if(parentDbName.equals("Admins")){
                            Toast.makeText(login.this, " Admin Login Succesfully!", Toast.LENGTH_LONG).show();
                            loadbar.dismiss();
                            Intent intent = new Intent(login.this, adminpanel.class);
                            startActivity(intent);
                        }
                               else if(parentDbName.equals("Users")){
                                   Toast.makeText(login.this,"Login Succesfully!",Toast.LENGTH_LONG).show();
                                   loadbar.dismiss();
                                   Intent intent=new Intent(login.this,Home.class);
                                  Privalent.currentuser=userdata;
                                   startActivity(intent);
                               }


                        }
                        else {
                            Toast.makeText(login.this,"Password not Match!",Toast.LENGTH_LONG).show();
                            loadbar.dismiss();
                        }
                    }

                }
                else {
                    Toast.makeText(login.this,"user not register!",Toast.LENGTH_LONG).show();
                    loadbar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}