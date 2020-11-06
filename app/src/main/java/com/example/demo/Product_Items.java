package com.example.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Adapter.SearchAdapter;
import Model.Products;
import ViewHolder.ProductViewHolder;

public class Product_Items extends Fragment {
    private DatabaseReference productref;
    private RecyclerView recyclerView;
    private Context procontext;
    EditText searchproname;
    ArrayList<Products>arrayList;
    String savecurrentdate;




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        procontext=context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.productitem, container, false);

        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat CurrentDate=new SimpleDateFormat("MMM dd,yyyy");
        savecurrentdate=CurrentDate.format(calendar.getTime());

        productref = FirebaseDatabase.getInstance().getReference().child("Products");
        recyclerView = view.findViewById(R.id.recycler_menu);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(procontext);
        recyclerView.setLayoutManager(new GridLayoutManager(procontext,2));

        arrayList=new ArrayList<>();
        searchproname=(EditText)view.findViewById(R.id.search_productname);

        searchproname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    search(editable.toString());
                }
                else {
                    search("");
                }
            }
        });
        return view;
    }

    private void search (String s)
    {
        Query query=productref.orderByChild("Product_Name").startAt(s).endAt(s+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    arrayList.clear();
                    for (DataSnapshot dss:dataSnapshot.getChildren()){
                        final Products products=dss.getValue(Products.class);
                        arrayList.add(products);

                    }
                    SearchAdapter searchAdapter=new SearchAdapter(procontext,arrayList);
                    recyclerView.setAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> options=new FirebaseRecyclerOptions.Builder<Products>().setQuery(productref,Products.class).build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder>adapter=new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull final Products products) {
                productViewHolder.dispproname.setText(products.getProduct_Name());

                productViewHolder.disproprice.setText("₹"+products.getProduct_price()+"");

                Picasso.get().load(products.getProduct_Image()).into(productViewHolder.dispproimage);

                productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(procontext,ProductDetails.class);
                        intent.putExtra("pro_id",products.getProduct_id());
                        intent.putExtra("imagepath",products.getProduct_Image().toString());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.productdata,parent,false);
                ProductViewHolder holder=new ProductViewHolder(view);


                return holder;

            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}
