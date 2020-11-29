package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Model.CartData;
import Model.CartOrderDetails;
import Model.Products;
import Privalent.Privalent;
import ViewHolder.CartViewHolder;

public class PlaceOrder extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    ArrayList<Products> arrayList;
    private TextView shipping_name,shipp_add1,shipp_add2,ship_state,ship_pincode,billingtotal,paymentmode,totalprice,proamount;
    private int countproduct;
    private Button ordercancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        recyclerView=findViewById(R.id.placedorderrecycle);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        proamount=(TextView)findViewById(R.id.pro_amount);
        shipping_name=(TextView)findViewById(R.id.shipping_name);
        shipp_add1=(TextView)findViewById(R.id.shipping_addr1);
        shipp_add2=(TextView)findViewById(R.id.shipping_addr2);
        ship_state=(TextView)findViewById(R.id.shipping_state);
        ship_pincode=(TextView)findViewById(R.id.shipp_pincode);
        billingtotal=(TextView)findViewById(R.id.billingtotal);
        paymentmode=(TextView)findViewById(R.id.ship_paymentmode);
        totalprice=(TextView)findViewById(R.id.ship_totalprice);
        ordercancel=(Button)findViewById(R.id.ordercancel);
        ordercancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Cancel();

            }
        });
    }

    private void Cancel()
    {
        final DatabaseReference cancelorder=FirebaseDatabase.getInstance().getReference().child("CartOrders").child(Privalent.currentuser.getPhoneno());
        cancelorder.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    String status="PENDING";
                    CartOrderDetails cartOrderDetails=dataSnapshot.getValue(CartOrderDetails.class);
                    if(cartOrderDetails.getStatus().equals(status))
                    {
                        FirebaseDatabase.getInstance().getReference().child("cart").child("AdminView").child(Privalent.currentuser.getPhoneno()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                FirebaseDatabase.getInstance().getReference().child("CartOrders").child(Privalent.currentuser.getPhoneno()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        Intent intent = new Intent(PlaceOrder.this, Home.class);
                                        startActivity(intent);
                                        Toast.makeText(PlaceOrder.this,"Order Has Been Canceled",Toast.LENGTH_LONG).show();

                                    }
                                });

                            }
                        });

                    }
                    else {
                        Toast.makeText(PlaceOrder.this,"Cancellation Unavailable Order has been Dispatch" ,Toast.LENGTH_LONG).show();
                        ordercancel.setText("Cancellation Unavailable ");
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference custorder= FirebaseDatabase.getInstance().getReference().child("cart")
                .child("AdminView").child(Privalent.currentuser.getPhoneno()).child("Products");
        FirebaseRecyclerOptions<CartData> options=new FirebaseRecyclerOptions.Builder<CartData>().setQuery(custorder,CartData.class).build();
        FirebaseRecyclerAdapter<CartData, CartViewHolder>adapter=new FirebaseRecyclerAdapter<CartData, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull CartData cartData)
            {
                cartViewHolder.proname.setText(cartData.getPname());
                cartViewHolder.proprice.setText(cartData.getPrice());
                cartViewHolder.proquantity.setText(cartData.getQuantity());
                Picasso.get().load(cartData.getPimagr()).into(cartViewHolder.proimage);
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.placeorderdata,parent,false);
                CartViewHolder holder=new CartViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        final DatabaseReference orderdata= FirebaseDatabase.getInstance().getReference().child("CartOrders");
        orderdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                CartOrderDetails orderdata=dataSnapshot.child(Privalent.currentuser.getPhoneno()).getValue(CartOrderDetails.class);
                String items=(orderdata.getTotal_Amount());
                int itemprice=Integer.parseInt(items);
                int itemtotal=itemprice+140;
                shipping_name.setText(orderdata.getCustomer_Name());
                shipp_add1.setText(orderdata.getAddressLine1());
                shipp_add2.setText(orderdata.getAddressLine2());
                ship_state.setText(orderdata.getState());
                ship_pincode.setText(orderdata.getPincode());
                paymentmode.setText(orderdata.getPaymentMode());
                proamount.setText("₹"+orderdata.getTotal_Amount()+"");
                billingtotal.setText("₹"+itemtotal+"");




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final DatabaseReference countpro= FirebaseDatabase.getInstance().getReference().child("cart").child("AdminView").child(Privalent.currentuser.getPhoneno()).child("Products");
        countpro.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    countproduct=(int) dataSnapshot.getChildrenCount();
                    totalprice.setText(Integer.toString(countproduct));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}