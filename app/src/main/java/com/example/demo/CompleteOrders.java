package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.baoyachi.stepview.VerticalStepView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Model.CartOrderDetails;
import Model.OrderComplete;

public class CompleteOrders extends AppCompatActivity {
    VerticalStepView step_view;
    private TextView shipping_name,shipp_add1,shipp_add2,ship_state,ship_pincode,billingtotal,paymentmode,totalitem;
    private int countproduct;
    private String cust_id="";
    private String date="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_orders);
        cust_id=getIntent().getStringExtra("cust_id");
        date=getIntent().getStringExtra("date");


        shipping_name=(TextView)findViewById(R.id.shipping_name);
        shipp_add1=(TextView)findViewById(R.id.shipping_addr1);
        shipp_add2=(TextView)findViewById(R.id.shipping_addr2);
        ship_state=(TextView)findViewById(R.id.shipping_state);
        ship_pincode=(TextView)findViewById(R.id.shipp_pincode);
        billingtotal=(TextView)findViewById(R.id.billingtotal);
        paymentmode=(TextView)findViewById(R.id.ship_paymentmode);
        totalitem=(TextView)findViewById(R.id.ship_totalprice);

        step_view=findViewById(R.id.stepview);
        setStepView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference orderdata= FirebaseDatabase.getInstance().getReference().child("CompleteOrders").child(cust_id).child("orders").child(date);
        orderdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    OrderComplete orderdata=dataSnapshot.getValue(OrderComplete.class);
                    String items=(orderdata.getTotal_Amount());
                    int itemprice=Integer.parseInt(items);
                    int itemtotal=itemprice+140;
                    shipping_name.setText(orderdata.getCustomer_Name());
                    shipp_add1.setText(orderdata.getAddressLine1());
                    shipp_add2.setText(orderdata.getAddressLine2());
                    ship_state.setText(orderdata.getState());
                    ship_pincode.setText(orderdata.getPincode());
                    paymentmode.setText(orderdata.getPaymentMode());
                    totalitem.setText(orderdata.getTotal_Items());
                    billingtotal.setText("₹"+itemtotal+"");

                  }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setStepView()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            step_view.setStepsViewIndicatorComplectingPosition(getList()
                    .size())
                    .reverseDraw(false)
                    .setStepViewTexts(getList())
                    .setLinePaddingProportion(0.85f)
                    .setStepsViewIndicatorCompletedLineColor(getColor(R.color.colorPrimaryDark))
                    .setStepViewComplectedTextColor(getColor(R.color.ordertext))
                    .setStepViewUnComplectedTextColor(getColor(R.color.colorPrimaryDark))
                    .setStepsViewIndicatorCompleteIcon(getDrawable(R.drawable.done2))
                    .setStepsViewIndicatorAttentionIcon(getDrawable(R.drawable.attention))
                    .setStepsViewIndicatorDefaultIcon(getDrawable(R.drawable.default_icon));
        }
    }

    private List<String> getList()
    {
        List<String> list=new ArrayList<>();
        list.add("Order Placed");
        list.add("Order Dispatch");
        list.add("Order En Route");
        list.add("Order Arrived");
        return list;
    }
}