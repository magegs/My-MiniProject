package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.demo.R;

public class Manageorders extends AppCompatActivity {
    private ImageView vieworder,updateorder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageorders);
        vieworder=(ImageView)findViewById(R.id.vieworder);
        updateorder=(ImageView)findViewById(R.id.updateorders);
        updateorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Manageorders.this, UpdateOrders.class);
                startActivity(intent);
            }
        });
        vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Manageorders.this, UpdateOrders.class);
                startActivity(intent);

            }
        });
    }
}