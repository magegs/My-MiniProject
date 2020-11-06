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

import org.w3c.dom.Text;

import Model.CartData;
import Model.Products;
import Privalent.Privalent;
import ViewHolder.CartViewHolder;
import ViewHolder.ProductViewHolder;

public class Cart extends AppCompatActivity {
    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    private Button next_btn;
    private TextView totalprice;
    private  int overtotal=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView=findViewById(R.id.cart_recycle);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        next_btn=(Button)findViewById(R.id.cart_next);
        totalprice=(TextView)findViewById(R.id.cart_totalprice);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        final DatabaseReference cartref = FirebaseDatabase.getInstance().getReference().child("cart").child("UserView")
            .child(Privalent.currentuser.getPhoneno()).child("Products");
        FirebaseRecyclerOptions<CartData> options=new FirebaseRecyclerOptions.Builder<CartData>().setQuery(cartref,CartData.class).build();
        FirebaseRecyclerAdapter<CartData, CartViewHolder>adapter=new FirebaseRecyclerAdapter<CartData, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull final CartData cartData) {
                cartViewHolder.proname.setText(cartData.getPname());
                cartViewHolder.proprice.setText(cartData.getPrice());
                cartViewHolder.proquantity.setText(cartData.getQuantity());
                Picasso.get().load(cartData.getPimagr()).into(cartViewHolder.proimage);
                final int onetypeproprice=((Integer.valueOf(cartData.getPrice()))) *Integer.valueOf(cartData.getQuantity());
                overtotal=overtotal+onetypeproprice;
                totalprice.setText(String.valueOf(overtotal));
                cartViewHolder.delete_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cartref.child("UserView").child(Privalent.currentuser.getPhoneno()).child("Products").child(cartData.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(Cart.this,"Item Removed",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(Cart.this,Cart.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                });
                cartViewHolder.edti_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(Cart.this,ProductDetails.class);
                        intent.putExtra("pro_id",cartData.getPid());
                        intent.putExtra("imagepath",cartData.getPimagr().toString());
                        startActivity(intent);
                    }
                });
                next_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(Cart.this,Shipping_Address.class);
                        intent.putExtra("Total_Price",totalprice.getText().toString());
                        startActivity(intent);
                    }
                });


            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_data,parent,false);
                CartViewHolder holder=new CartViewHolder(view);
                return holder;

            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
    }
