package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapter.CartAdapter;
import Model.CartOrderDetails;
import ViewHolder.CartOrderHolder;

public class ViewOrder extends AppCompatActivity {
    private EditText searchcust_id;
    private RecyclerView recyclerView;

    ArrayList<CartOrderDetails> arrayList;

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        recyclerView = (RecyclerView) findViewById(R.id.viewordersrecycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>();

        searchcust_id=(EditText)findViewById(R.id.search_ordercart_id);
        searchcust_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(!editable.toString().isEmpty()){
                    search(editable.toString());
                }
                else{
                    search("");
                }
            }
        });
    }

    private void search(String toString)
    {
        final DatabaseReference orderdata= FirebaseDatabase.getInstance().getReference().child("CartOrders");
        Query query=orderdata.orderByChild("Cart_id").startAt(toString).endAt(toString+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.hasChildren())
                {
                    arrayList.clear();
                    for (DataSnapshot dss:dataSnapshot.getChildren())
                    {
                        final CartOrderDetails cartOrderDetails=dss.getValue(CartOrderDetails.class);
                        arrayList.add(cartOrderDetails);
                    }
                    CartAdapter cartAdapter=new CartAdapter(getApplicationContext(),arrayList);
                    recyclerView.setAdapter(cartAdapter);
                    cartAdapter.notifyDataSetChanged();

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
        final DatabaseReference orderdata= FirebaseDatabase.getInstance().getReference().child("CartOrders");
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    orderview();
                }
                else{
                    Toast.makeText(ViewOrder.this,"No Order Has been Founded",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ViewOrder.this, adminpanel.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        orderdata.addListenerForSingleValueEvent(valueEventListener);
        orderdata.removeEventListener(valueEventListener);
    }

    private void orderview()
    {
        final DatabaseReference orderdata= FirebaseDatabase.getInstance().getReference().child("CartOrders");
        FirebaseRecyclerOptions<CartOrderDetails> options=new FirebaseRecyclerOptions.Builder<CartOrderDetails>().setQuery(orderdata,CartOrderDetails.class).build();
        FirebaseRecyclerAdapter<CartOrderDetails, CartOrderHolder> adapter=new FirebaseRecyclerAdapter<CartOrderDetails, CartOrderHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartOrderHolder cartOrderHolder, int i, @NonNull final CartOrderDetails cartOrderDetails)
            {
                cartOrderHolder.customer_id.setText(cartOrderDetails.getContact());
                cartOrderHolder.order_status.setText(cartOrderDetails.getStatus());
                cartOrderHolder.order_placedon.setText(cartOrderDetails.getDate());
                cartOrderHolder.payment_mode.setText(cartOrderDetails.getPaymentMode());

            }

            @NonNull
            @Override
            public CartOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.updateorder,parent,false);
                CartOrderHolder holder=new CartOrderHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}