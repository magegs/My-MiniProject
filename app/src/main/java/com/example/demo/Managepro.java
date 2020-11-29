package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Managepro extends AppCompatActivity {
    private ImageView addpro,updatepro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managepro);
        addpro=(ImageView)findViewById(R.id.addpro);
        updatepro=(ImageView)findViewById(R.id.updatepro);
        updatepro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Managepro.this, UpdateProducts.class);
                startActivity(intent);
            }
        });
        addpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Managepro.this, Add_Product.class);
                startActivity(intent);
            }
        });
    }
}