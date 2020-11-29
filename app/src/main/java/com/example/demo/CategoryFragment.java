package com.example.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

import java.util.ArrayList;

import Adapter.SearchAdapter;
import Model.Products;
import ViewHolder.ProductViewHolder;

public class CategoryFragment extends Fragment{
    Spinner spinner;
    String names[]={"Select Category","VEGETABLE SEED","FLOWER SEED","HYBRID VEGETABLES","LEAFY VEGETABLES","DESI SEEDS","ROOT VEGETABLES","CREEPER VEGETABLES"};
    ArrayAdapter<String> arrayAdapter;
    private EditText searchcategory;
    ArrayList<Products> arrayList;

    private RecyclerView recyclerView;
    private DatabaseReference productdata;
    private Context procontext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        procontext=context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_category,container,false);

        recyclerView = view.findViewById(R.id.recycle_category);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(procontext);
        recyclerView.setLayoutManager(new GridLayoutManager(procontext,2));

        arrayList=new ArrayList<>();

        spinner=(Spinner)view.findViewById(R.id.spinner_search);
        searchcategory=(EditText)view.findViewById(R.id.search_categoryname);
        arrayAdapter=new ArrayAdapter<String>(procontext,android.R.layout.simple_list_item_1,names);
        spinner.setAdapter(arrayAdapter);
//        searchcategory.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable)
//            {
//                if(!editable.toString().isEmpty()){
//                    search(editable.toString());
//                }
//                else {
//                    search("");
//                }
//            }
//        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                Toast.makeText(procontext,"You Selected"+names[i],Toast.LENGTH_SHORT).show();
                String procat=adapterView.getItemAtPosition(i).toString();
                search(procat);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;

    }


    private void search(String procat)
    {

        DatabaseReference productref= FirebaseDatabase.getInstance().getReference().child("Products");
        Query query=productref.orderByChild("Category").startAt(procat).endAt(procat+"\uf8ff");
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
        productdata = FirebaseDatabase.getInstance().getReference().child("Products");
        FirebaseRecyclerOptions<Products> options=new FirebaseRecyclerOptions.Builder<Products>().setQuery(productdata,Products.class).build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter=new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull final Products products)
            {
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
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.productdata,parent,false);
                ProductViewHolder holder=new ProductViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
