package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
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
import Privalent.Privalent;

public class OrderTrack extends AppCompatActivity {
    VerticalStepView step_view;
    private TextView shipping_name,shipp_add1,shipp_add2,ship_state,ship_pincode,billingtotal,paymentmode,totalprice;
   private int countproduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_track);
        shipping_name=(TextView)findViewById(R.id.shipping_name);
        shipp_add1=(TextView)findViewById(R.id.shipping_addr1);
        shipp_add2=(TextView)findViewById(R.id.shipping_addr2);
        ship_state=(TextView)findViewById(R.id.shipping_state);
        ship_pincode=(TextView)findViewById(R.id.shipp_pincode);
        billingtotal=(TextView)findViewById(R.id.billingtotal);
        paymentmode=(TextView)findViewById(R.id.ship_paymentmode);
        totalprice=(TextView)findViewById(R.id.ship_totalprice);


        step_view=findViewById(R.id.stepview);
        setStepView();
    }

    @Override
    protected void onStart() {
        super.onStart();
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
//                totalprice.setText("₹"+orderdata.getTotal_Amount()+"");
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

            final DatabaseReference orderref= FirebaseDatabase.getInstance().getReference().child("CartOrders");
            orderref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    CartOrderDetails orderdata=dataSnapshot.child(Privalent.currentuser.getPhoneno()).getValue(CartOrderDetails.class);
                    String dispatch="DISPATCH";
                    String enroute="ENROUTE";
                    String pending="PENDING";
                    if(orderdata.getStatus().equals(dispatch))
                    {

                        step_view.setStepsViewIndicatorComplectingPosition(2);
                        NotificationCompat.Builder builder=(NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.track)
                                .setContentTitle("Your Order")
                                .setContentText("Your Order has Been Dispatch");
                        NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                        notificationManager.notify(0,builder.build());
                    }
                    if(orderdata.getStatus().equals(enroute))
                    {

                        step_view.setStepsViewIndicatorComplectingPosition(3);
                    }
                    if(orderdata.getStatus().equals(pending))
                    {
                        step_view.setStepsViewIndicatorComplectingPosition(1);
                    }





                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
    private List<String>getList(){
        List<String> list=new ArrayList<>();
        list.add("Order Placed");
        list.add("Order Dispatch");
        list.add("Order En Route");
        list.add("Order Arrived");
        return list;
    }
}