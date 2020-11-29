package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import Model.Products;

public class UpdateProductDetails extends AppCompatActivity {
    private TextView proname,proprice,prostock;
    private EditText stock,price;
    private ImageView proimage;
    private String proid="";
    private Button block,delete,stock_btn,price_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product_details);
        proid=getIntent().getStringExtra("pro_id");
        stock=(EditText)findViewById(R.id.edit_stock);
        price=(EditText)findViewById(R.id.edit_price);
        proname=(TextView)findViewById(R.id.update_proname);
        proprice=(TextView)findViewById(R.id.update_price);
        prostock=(TextView)findViewById(R.id.update_stock);
        proimage=(ImageView)findViewById(R.id.update_productimage);
        block=(Button)findViewById(R.id.pro_block);
        delete=(Button)findViewById(R.id.pro_delete);
        stock_btn=(Button)findViewById(R.id.updatestock_btn);
        price_btn=(Button)findViewById(R.id.updateprice_btn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                DatabaseReference proref = FirebaseDatabase.getInstance().getReference().child("Products").child(proid);
                proref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        Toast.makeText(getApplicationContext(), "Product has been Deleted...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference proref= FirebaseDatabase.getInstance().getReference().child("Products").child(proid);
                proref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if(dataSnapshot.exists())
                        {

                            Products products=dataSnapshot.getValue(Products.class);

                            if(products.getStatus().equals("ACTIVE"))
                            {
                                proref.child("status").setValue("BLOCK").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        Toast.makeText(getApplicationContext(), "Product has been Blocked...", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            if(products.getStatus().equals("BLOCK"))
                            {
                                proref.child("status").setValue("ACTIVE").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        Toast.makeText(getApplicationContext(), "Product has been UNBlocked...", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        price_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updateprice = price.getText().toString();
                if (TextUtils.isEmpty(updateprice)) {
                    Toast.makeText(getApplicationContext(), "Enter the Values...", Toast.LENGTH_SHORT).show();
                }
                else{
                DatabaseReference proref = FirebaseDatabase.getInstance().getReference().child("Products").child(proid);

                proref.child("Product_price").setValue(updateprice).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                    }
                });
            }
            }
        });

        stock_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatestock = stock.getText().toString();
                if (TextUtils.isEmpty(updatestock)) {
                    Toast.makeText(getApplicationContext(), "Enter the Values...", Toast.LENGTH_SHORT).show();
                } else{
                    DatabaseReference proref = FirebaseDatabase.getInstance().getReference().child("Products").child(proid);
                proref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            final String currentstock = dataSnapshot.child("Stock").getValue().toString();
                            int a = Integer.parseInt(currentstock);
                            String addstock = stock.getText().toString();
                            int b = Integer.parseInt(addstock);
                            final int c = a + b;
                            String updatestock = String.valueOf(c);
                            DatabaseReference proref = FirebaseDatabase.getInstance().getReference().child("Products").child(proid);
                            proref.child("Stock").setValue(updatestock).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            }
        });

        getProductDetails(proid);
    }

    private void getProductDetails(String proid)
    {
        DatabaseReference proref= FirebaseDatabase.getInstance().getReference().child("Products");
        proref.child(proid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {

                    Products products=dataSnapshot.getValue(Products.class);
                    proname.setText(products.getProduct_Name());
                    proprice.setText(products.getProduct_price());
                    prostock.setText(products.getStock());
                    Picasso.get().load(products.getProduct_Image()).into(proimage);
                    if(products.getStatus().equals("ACTIVE"))
                    {
                        block.setText("BLOCK");
                    }
                    if(products.getStatus().equals("BLOCK"))
                    {
                        block.setText("UNBLOCK");
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}