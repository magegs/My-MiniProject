package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.demo.R;

import Report.User_Report;

public class Manageuser extends AppCompatActivity {
    private ImageView viewuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageuser);
        viewuser=(ImageView)findViewById(R.id.viewusers);
        viewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Manageuser.this, User_Report.class);
                startActivity(intent);
            }
        });
    }
}