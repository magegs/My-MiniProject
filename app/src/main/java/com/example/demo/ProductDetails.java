package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import Model.Products;
import Privalent.Privalent;

public class ProductDetails extends AppCompatActivity {
private TextView proname,productdesc,prodctdetail1,prodctdetail2,prodctdetail3,prodctdetail4,productprice;
private ImageView proimg;
private DataSnapshot dataSnapshot;
private ElegantNumberButton elegantNumberButton;
private Button addtocartbtn,buynowbtn;
private String proid="";
private String cartimage="";
private String totalprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        proid=getIntent().getStringExtra("pro_id");
        cartimage=getIntent().getStringExtra("imagepath");
        proname=(TextView)findViewById(R.id.details_productname);
        productprice=(TextView)findViewById(R.id.details_productprice);
        productdesc=(TextView)findViewById(R.id.details_productdesc);
        prodctdetail1=(TextView)findViewById(R.id.table_prodctdetail1);
        prodctdetail2=(TextView)findViewById(R.id.table_prodctdetail2);
        prodctdetail3=(TextView)findViewById(R.id.table_prodctdetail3);
        prodctdetail4=(TextView)findViewById(R.id.table_prodctdetail4);
        proimg=(ImageView)findViewById(R.id.details_productimage);
        elegantNumberButton=(ElegantNumberButton)findViewById(R.id.elegantbutton);
        addtocartbtn=(Button)findViewById(R.id.addtocartbtn);
        buynowbtn=(Button)findViewById(R.id.buynowbtn);





        getProductDetails(proid);
        addtocartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) 
            {
                final DatabaseReference cartref = FirebaseDatabase.getInstance().getReference();
                ValueEventListener valueEventListener=new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.child("CartOrders").child(Privalent.currentuser.getPhoneno()).exists())
                        {
                            Toast.makeText(ProductDetails.this, "Unable to  Add prodcut to Cart Cart Order is under processing !", Toast.LENGTH_LONG).show();
                        }
                        else {
                            addingToCartList();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                cartref.addListenerForSingleValueEvent(valueEventListener);
                cartref.removeEventListener(valueEventListener);



            }
        });

    }

    private void addingToCartList()
    {
       String time,date;
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM dd,yyyy");
        date=simpleDateFormat.format(calendar.getTime());

        SimpleDateFormat currenttime=new SimpleDateFormat("HH:mm:ss a");
        time=currenttime.format(calendar.getTime());



        final DatabaseReference cartlistref=FirebaseDatabase.getInstance().getReference().child("cart");
        final HashMap<String,Object>cartmap=new HashMap<>();

        cartmap.put("pid",proid);
        cartmap.put("pimagr",cartimage);
        cartmap.put("pname",proname.getText().toString());
        cartmap.put("price",productprice.getText().toString());
        cartmap.put("quantity",elegantNumberButton.getNumber());
        cartmap.put("Total",totalprice);
        cartmap.put("date",date);
        cartmap.put("time",time);
        cartlistref.child("UserView").child(Privalent.currentuser.getPhoneno()).child("Products").child(proid).updateChildren(cartmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful()){
                    cartlistref.child("AdminView").child(Privalent.currentuser.getPhoneno())
                            .child("Products").child(proid).updateChildren(cartmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful()){
                                Toast.makeText(ProductDetails.this,"Added to cart List",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(ProductDetails.this,Home.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });


    }

    private void getProductDetails(String proid)
    {
        DatabaseReference proref= FirebaseDatabase.getInstance().getReference().child("Products");
        proref.child(proid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Products products=dataSnapshot.getValue(Products.class);
                    proname.setText(products.getProduct_Name());
                    productprice.setText(products.getProduct_price());
                    productdesc.setText(products.getProduct_Descrpition());
                    prodctdetail1.setText(products.getProduct_Specs1());
                    prodctdetail2.setText(products.getProduct_Specs2());
                    prodctdetail3.setText(products.getProduct_Specs3());
                    prodctdetail4.setText(products.getProduct_Specs4());
                    Picasso.get().load(products.getProduct_Image()).into(proimg);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}