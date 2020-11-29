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
import android.widget.ArrayAdapter;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Model.CartOrderDetails;
import Model.OrderComplete;
import Privalent.Privalent;
import ViewHolder.CartOrderHolder;
import ViewHolder.OrderCompleteHolder;

public class UserViewReference extends AppCompatActivity {
    private RecyclerView recyclerView;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_reference);
        recyclerView = (RecyclerView) findViewById(R.id.ordercompleterecycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference orderdata= FirebaseDatabase.getInstance().getReference().child("CompleteOrders").child(Privalent.currentuser.getPhoneno()).child("orders");
        FirebaseRecyclerOptions<OrderComplete> options=new FirebaseRecyclerOptions.Builder<OrderComplete>().setQuery(orderdata,OrderComplete.class).build();
        FirebaseRecyclerAdapter<OrderComplete, OrderCompleteHolder>adapter=new FirebaseRecyclerAdapter<OrderComplete, OrderCompleteHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderCompleteHolder orderCompleteHolder, int i, @NonNull final OrderComplete orderComplete)
            {
                orderCompleteHolder.date.setText(orderComplete.getDate());
                orderCompleteHolder.totalamount.setText(orderComplete.getTotal_Amount());
                orderCompleteHolder.totalitem.setText(orderComplete.getTotal_Items());
                orderCompleteHolder.paymentmode.setText(orderComplete.getPaymentMode());
                orderCompleteHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        Intent intent=new Intent(UserViewReference.this,CompleteOrders.class);
                        intent.putExtra("cust_id",orderComplete.getCart_id());
                        intent.putExtra("date",orderComplete.getDate());
                        startActivity(intent);
                    }
                });


            }

            @NonNull
            @Override
            public OrderCompleteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ordercompletedata,parent,false);
                OrderCompleteHolder holder=new OrderCompleteHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}