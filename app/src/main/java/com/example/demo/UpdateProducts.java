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
import Adapter.SearchAdapter;
import Adapter.UpdateProAdapter;
import Model.CartOrderDetails;
import Model.Products;
import ViewHolder.CartOrderHolder;
import ViewHolder.ProductViewHolder;
import ViewHolder.UpdateProductHolder;

public class UpdateProducts extends AppCompatActivity {
    private RecyclerView recyclerView;
    ArrayList<Products> arrayList;
    private EditText searchpro_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_products);
        searchpro_id=(EditText)findViewById(R.id.search_product_id);
        recyclerView = (RecyclerView) findViewById(R.id.updateprosrecycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>();
        searchpro_id.addTextChangedListener(new TextWatcher() {
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
        final DatabaseReference prodata= FirebaseDatabase.getInstance().getReference().child("Products");
        Query query=prodata.orderByChild("product_id").startAt(toString).endAt(toString+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.hasChildren()){
                    arrayList.clear();
                    for (DataSnapshot dss:dataSnapshot.getChildren()){
                        final Products products=dss.getValue(Products.class);
                        arrayList.add(products);
                    }
                    UpdateProAdapter updateProAdapter=new UpdateProAdapter(getApplicationContext(),arrayList);
                    recyclerView.setAdapter(updateProAdapter);
                    updateProAdapter.notifyDataSetChanged();







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
        final DatabaseReference proref= FirebaseDatabase.getInstance().getReference().child("Products");
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
             if(dataSnapshot.exists())
             {
              proview();
             }
             else{
                 Toast.makeText(UpdateProducts.this,"No Order Has been Founded",Toast.LENGTH_LONG).show();
                 Intent intent = new Intent(UpdateProducts.this, adminpanel.class);
                 startActivity(intent);
             }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        proref.addListenerForSingleValueEvent(valueEventListener);
        proref.removeEventListener(valueEventListener);
    }

    private void proview()
    {
        final DatabaseReference proref= FirebaseDatabase.getInstance().getReference().child("Products");
        FirebaseRecyclerOptions<Products>options=new FirebaseRecyclerOptions.Builder<Products>().setQuery(proref,Products.class).build();
        FirebaseRecyclerAdapter<Products, UpdateProductHolder>adapter=new FirebaseRecyclerAdapter<Products, UpdateProductHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UpdateProductHolder updateProductHolder, int i, @NonNull final Products products)
            {
                updateProductHolder.pro_id.setText(products.getProduct_id());
                updateProductHolder.proname.setText(products.getProduct_Name());
                updateProductHolder.stock.setText(products.getStock());
                updateProductHolder.price.setText(products.getProduct_price());
                updateProductHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(UpdateProducts.this, UpdateProductDetails.class);
                        intent.putExtra("pro_id",products.getProduct_id());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public UpdateProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.updateproduct,parent,false);
                UpdateProductHolder holder=new UpdateProductHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}