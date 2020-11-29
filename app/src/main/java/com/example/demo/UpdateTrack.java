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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import Model.CartData;
import Model.CartOrderDetails;
import Model.Products;
import Privalent.Privalent;
import ViewHolder.CartViewHolder;

public class UpdateTrack extends AppCompatActivity {
    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    private String totalitem="";
    ArrayList<Products> arrayList;
    private TextView shipping_name,shipp_add1,shipp_add2,ship_state,ship_pincode,billingtotal,paymentmode,totalprice,proamount;
    private int countproduct;
    private Button ordercancel,dispatch_btn;
    private String cust_id="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_track);
        recyclerView=findViewById(R.id.placedorderrecycle);
        cust_id=getIntent().getStringExtra("cust_id");
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
        dispatch_btn=(Button)findViewById(R.id.orderdispatch);
        dispatch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference orderdispatch=FirebaseDatabase.getInstance().getReference().child("CartOrders").child(cust_id);
                ValueEventListener valueEventListener=new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                         CartOrderDetails cartOrderDetails=dataSnapshot.getValue(CartOrderDetails.class);
                        if(dataSnapshot.exists())
                        {
                            String status="DISPATCH";
                            if(cartOrderDetails.getStatus().equals(status))
                            {

                                orderdispatch.child("Status").setValue("COMPLETED").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        if(task.isSuccessful())
                                        {
                                            final DatabaseReference countpro= FirebaseDatabase.getInstance().getReference().child("cart").child("AdminView").child(cust_id).child("Products");
                                            countpro.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                {
                                                    if(dataSnapshot.exists())
                                                    {
                                                        countproduct=(int) dataSnapshot.getChildrenCount();
                                                       String totalitem=Integer.toString(countproduct);
                                                        copydata(totalitem);
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });


                                        }
                                    }
                                });

                            }
                            if(cartOrderDetails.getStatus().equals("PENDING"))
                            {
                                orderdispatch.child("Status").setValue("DISPATCH").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(UpdateTrack.this,"Ordere has Successfully Dispatch",Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(UpdateTrack.this, UpdateOrders.class);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                orderdispatch.addListenerForSingleValueEvent(valueEventListener);
                orderdispatch.removeEventListener(valueEventListener);


            }
        });
    }

    private void copydata(final String totalitem)
    {


        DatabaseReference oldnode=FirebaseDatabase.getInstance().getReference().child("CartOrders");
        DatabaseReference newnode=FirebaseDatabase.getInstance().getReference().child("CompleteOrders");
        oldnode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot dss:dataSnapshot.getChildren())
                {
                    final CartOrderDetails cartOrderDetails=dss.getValue(CartOrderDetails.class);

                    String time,date,newchild;
                    Calendar calendar=Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM dd,yyyy");
                    date=simpleDateFormat.format(calendar.getTime());

                    SimpleDateFormat currenttime=new SimpleDateFormat("HH:mm:ss a");
                    time=currenttime.format(calendar.getTime());
                    newchild=date+time;
                    final DatabaseReference orderref= FirebaseDatabase.getInstance().getReference().child("CompleteOrders").child(cust_id).child("orders").child(newchild);
                    HashMap<String,Object> ordermap=new HashMap<>();
                    ordermap.put("Cart_id",cust_id);
                    ordermap.put("Total_Items",totalitem);
                    ordermap.put("Total_Amount",cartOrderDetails.getTotal_Amount());
                    ordermap.put("Customer_Name",cartOrderDetails.getCustomer_Name());
                    ordermap.put("Contact",cartOrderDetails.getContact());
                    ordermap.put("AddressLine1",cartOrderDetails.getAddressLine1());
                    ordermap.put("AddressLine2",cartOrderDetails.getAddressLine2());
                    ordermap.put("AddressLine3",cartOrderDetails.getAddressLine3());
                    ordermap.put("pincode",cartOrderDetails.getPincode());
                    ordermap.put("State",cartOrderDetails.getState());
                    ordermap.put("date",date);
                    ordermap.put("time",time);
                    ordermap.put("PaymentMode",cartOrderDetails.getPaymentMode() );
                    ordermap.put("Status",cartOrderDetails.getStatus());
                    orderref.updateChildren(ordermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                FirebaseDatabase.getInstance().getReference().child("CartOrders").child(cust_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        if(task.isSuccessful())
                                        {
                                            FirebaseDatabase.getInstance().getReference().child("cart").child("AdminView").child(cust_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task)
                                                {
                                                    if(task.isSuccessful())
                                                    {
                                                        Toast.makeText(UpdateTrack.this,"Ordere has Successfully Completed",Toast.LENGTH_LONG).show();
                                                        Intent intent = new Intent(UpdateTrack.this, adminpanel.class);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });

                                        }

                                    }
                                });
                            }

                        }
                    });
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
        final DatabaseReference orderdispatch=FirebaseDatabase.getInstance().getReference().child("CartOrders").child(cust_id);
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                CartOrderDetails cartOrderDetails=dataSnapshot.getValue(CartOrderDetails.class);
                if(dataSnapshot.exists())
                {
                    String status="DISPATCH";
                    if(cartOrderDetails.getStatus().equals(status))
                    {
                       dispatch_btn.setText("Complete the Order");

                    }
                    if(cartOrderDetails.getStatus().equals("PENDING"))
                    {
                        dispatch_btn.setText("Dispatch");
                    }
                    if(cartOrderDetails.getStatus().equals("COMPLETED"))
                    {
                        dispatch_btn.setText("Order has been Completed");
                        ordercancel.setVisibility(View.INVISIBLE);

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        };
        orderdispatch.addListenerForSingleValueEvent(valueEventListener);
        orderdispatch.removeEventListener(valueEventListener);
        final DatabaseReference custorder= FirebaseDatabase.getInstance().getReference().child("cart")
                .child("AdminView").child(cust_id).child("Products");
        FirebaseRecyclerOptions<CartData> options=new FirebaseRecyclerOptions.Builder<CartData>().setQuery(custorder,CartData.class).build();
        FirebaseRecyclerAdapter<CartData, CartViewHolder> adapter=new FirebaseRecyclerAdapter<CartData, CartViewHolder>(options) {
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
                CartOrderDetails orderdata=dataSnapshot.child(cust_id).getValue(CartOrderDetails.class);
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
        final DatabaseReference countpro= FirebaseDatabase.getInstance().getReference().child("cart").child("AdminView").child(cust_id).child("Products");
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