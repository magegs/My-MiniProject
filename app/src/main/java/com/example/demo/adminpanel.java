package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class adminpanel extends AppCompatActivity {
   private ImageView mpro,muser,morder,mstock,msr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminpanel);
       mpro=(ImageView) findViewById(R.id.managepro);
        muser=(ImageView) findViewById(R.id.manageusers);
        morder=(ImageView) findViewById(R.id.manageorder);
        msr=(ImageView)findViewById(R.id.salesreport);
        mstock=(ImageView)findViewById(R.id.managestock);
        mpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(adminpanel.this, Managepro.class);
                startActivity(intent1);
            }
        });
        muser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(adminpanel.this, Manageuser.class);
                startActivity(intent2);
            }
        });
        morder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(adminpanel.this, Manageorders.class);
                startActivity(intent3);
            }
        });
        mstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(adminpanel.this, Managestock.class);
                startActivity(intent4);
            }
        });
        msr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(adminpanel.this, Salesreport.class);
                startActivity(intent5);
            }
        });


    }
}