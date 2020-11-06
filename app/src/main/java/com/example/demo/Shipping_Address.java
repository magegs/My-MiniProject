package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import Privalent.Privalent;

public class Shipping_Address extends AppCompatActivity
{
    private EditText name,phno,addr1,addr2,addr3,pincode;
    private Button placeorder;
    private TextView total_price;
    Spinner spinner;
    String state,status,PaymentMode;
    String price;
    RadioGroup radioGroup;
    String names[]={"Select State","Andhra Pradesh","Goa","Ladakh","Tamil Nadu"};
    ArrayAdapter<String>arrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping__address);
        price= getIntent().getStringExtra("Total_Price");
        spinner=(Spinner)findViewById(R.id.shipping_state);
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
        spinner.setAdapter(arrayAdapter);
        radioGroup=(RadioGroup)findViewById(R.id.radiogroup);
        name=(EditText)findViewById(R.id.shipping_name);
        phno=(EditText)findViewById(R.id.shipping_number);
        addr1=(EditText)findViewById(R.id.shipping_addressline1);
        addr2=(EditText)findViewById(R.id.shipping_addressline2);
        addr3=(EditText)findViewById(R.id.shipping_addressline3);
        pincode=(EditText)findViewById(R.id.shipping_pincode);
        placeorder=(Button)findViewById(R.id.btn_PlaceOrder);
        total_price=(TextView)findViewById(R.id.total_price);
        status="PENDING";
        total_price.setText("Amount=₹"+String.valueOf(price)+"");
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radiobutton1:
                        PaymentMode="Online Payment";
                        break;
                    case R.id.radiobutton2:
                        PaymentMode="Cash On Delivery";
                }
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidInput();

            }
        });




    }

    private void ValidInput()
    {
        if(TextUtils.isEmpty(name.getText().toString()))
        {
            Toast.makeText(this,"Enter Customer Name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phno.getText().toString()))
        {
            Toast.makeText(this,"Enter Customer Number",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addr1.getText().toString()))
        {
            Toast.makeText(this,"Please Enter the Details",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addr2.getText().toString()))
        {
            Toast.makeText(this,"Please Enter the Details",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addr3.getText().toString()))
        {
            Toast.makeText(this,"Please Enter the Details",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pincode.getText().toString()))
        {
            Toast.makeText(this,"Please Enter the Details",Toast.LENGTH_SHORT).show();
        }

        else {
            ConfirmOrder();
        }
    }

    private void ConfirmOrder()
    {
        String time,date;
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM dd,yyyy");
        date=simpleDateFormat.format(calendar.getTime());

        SimpleDateFormat currenttime=new SimpleDateFormat("HH:mm:ss a");
        time=currenttime.format(calendar.getTime());

        final DatabaseReference orderref= FirebaseDatabase.getInstance().getReference().child("CartOrders").child(Privalent.currentuser.getPhoneno());
        HashMap<String,Object>ordermap=new HashMap<>();
        ordermap.put("Total_Amount",price);
        ordermap.put("Customer_Name",name.getText().toString());
        ordermap.put("Contact",phno.getText().toString());
        ordermap.put("AddressLine1",addr1.getText().toString());
        ordermap.put("AddressLine2",addr2.getText().toString());
        ordermap.put("AddressLine3",addr3.getText().toString());
        ordermap.put("pincode",pincode.getText().toString());
        ordermap.put("State",state);
        ordermap.put("date",date);
        ordermap.put("time",time);
        ordermap.put("PaymentMode",PaymentMode );
        ordermap.put("Status",status);
        orderref.updateChildren(ordermap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference().child("cart").child("UserView").child(Privalent.currentuser.getPhoneno()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful()){
                                Toast.makeText(Shipping_Address.this,"Cart Order Was Succesfully Placed",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(Shipping_Address.this,Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }

                        }
                    });

                }
            }
        });



    }

}