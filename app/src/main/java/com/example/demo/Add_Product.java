package com.example.demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import Model.Products;

public class Add_Product extends AppCompatActivity {
    private  String procat,prodesc,proname,proprice,savecurrentdate,savecurrenttime,prospec1,prospec2,prospec3,prospec4,prostock,status;
    Spinner spinner;
    String names[]={"Select Category","VEGETABLE SEED","FLOWER SEED","HYBRID VEGETABLES","LEAFY VEGETABLES","DESI SEEDS","ROOT VEGETABLES","CREEPER VEGETABLES"};
    ArrayAdapter<String>arrayAdapter;
    private Button btn_addpro;
    private EditText pro_name,pro_price,pro_desc,spec1,spec2,spec3,spec4,stock;
    private ImageView pro_image;
    private static final int GalleryPick=1;
    private Uri imageuri;
    private  String productkey,downloadImageUrl;
    private StorageReference proimageref;
    private DatabaseReference productref;
    private ProgressDialog loadbar;
    private String currentyear,newrefkey;
    int maxid=0;
    Products products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__product);
        loadbar=new ProgressDialog(this);
        proimageref= FirebaseStorage.getInstance().getReference().child("ProductImages");
        btn_addpro=(Button) findViewById(R.id.btn_addpro);
        pro_name=(EditText)findViewById(R.id.edtitext_product_name);
        pro_desc=(EditText)findViewById(R.id.edtitext_product_description);
        pro_price=(EditText)findViewById(R.id.edtitext_product_price);
        spec1=(EditText)findViewById(R.id.edtitext_product_spec1);
        spec2=(EditText)findViewById(R.id.edtitext_product_spec2);
        spec3=(EditText)findViewById(R.id.edtitext_product_spec3);
        spec4=(EditText)findViewById(R.id.edtitext_product_spec4);
        stock=(EditText)findViewById(R.id.edtitext_stock);
        pro_image=(ImageView)findViewById(R.id.addproimage);
        productref= FirebaseDatabase.getInstance().getReference().child("Products");

        pro_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });


        btn_addpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });


        spinner=(Spinner)findViewById(R.id.spinner_dropdown);
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"You Selected"+names[i],Toast.LENGTH_SHORT).show();
                procat=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void ValidateProductData() {

        proname=pro_name.getText().toString();
        proprice=pro_price.getText().toString();
        prodesc=pro_desc.getText().toString();
        prospec1=spec1.getText().toString();
        prospec2=spec2.getText().toString();
        prospec3=spec3.getText().toString();
        prospec4=spec4.getText().toString();
        prostock=stock.getText().toString();
        status="ACTIVE";

        if(imageuri==null){
            Toast.makeText(this,"Products Image is Mandatory..!",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(prodesc)){
            Toast.makeText(this,"Products Descrpition  is Mandatory..!",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(proname)){
            Toast.makeText(this,"Products Nameis Mandatory..!",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(proprice)){
            Toast.makeText(this,"Products Price is Mandatory..!",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(prospec1)){
            Toast.makeText(this,"please fill all the Mandatory..!",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(prospec2)){
            Toast.makeText(this,"please fill all the Mandatory..!",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(prospec3)){
            Toast.makeText(this,"please fill all the Mandatory..!",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(prospec4)){
            Toast.makeText(this,"please fill all the Mandatory..!",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(prostock)){
            Toast.makeText(this,"please fill all the Mandatory..!",Toast.LENGTH_SHORT).show();
        }

        else {
            StoreProductInformation();
        }



    }

    private void StoreProductInformation() {
        loadbar.setTitle("Adding...");
        loadbar.setMessage("please waait loading...");
        loadbar.setCanceledOnTouchOutside(false);
        loadbar.show();
        final DatabaseReference RootRef,ref;
        ref=FirebaseDatabase.getInstance().getReference().child("Products");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxid=(int)dataSnapshot.getChildrenCount();
                }
                else{
                    maxid=0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat Currentyear=new SimpleDateFormat("yyyy");
        currentyear=Currentyear.format(calendar.getTime());
        String before="PRO";
        productkey=before+currentyear+00+(maxid+1);


        SimpleDateFormat CurrentDate=new SimpleDateFormat("MMM dd,yyyy");
        savecurrentdate=CurrentDate.format(calendar.getTime());

        SimpleDateFormat CurrentTime=new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime=CurrentTime.format(calendar.getTime());




        final StorageReference filepath=proimageref.child(imageuri.getLastPathSegment() +productkey+".jpg");
        final UploadTask uploadTask=filepath.putFile(imageuri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message =e.toString();
                Toast.makeText(Add_Product.this,"Error"+message,Toast.LENGTH_SHORT).show();
                loadbar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Add_Product.this,"Image upload Succesfully",Toast.LENGTH_SHORT).show();

                Task<Uri> urltask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if(!task.isSuccessful()){
                            throw task.getException();

                        }
                        downloadImageUrl=filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                     if(task.isSuccessful()){
                         downloadImageUrl=task.getResult().toString();
                         Toast.makeText(Add_Product.this,"Image addes",Toast.LENGTH_SHORT).show();
                         InsertProduct();
                     }
                    }
                });
            }
        });
    }

    private void InsertProduct() {
        HashMap<String,Object> productMap=new HashMap<>();
        productMap.put("product_Image",downloadImageUrl);
        productMap.put("product_id",productkey);
        productMap.put("date",savecurrentdate);
        productMap.put("time",savecurrenttime);
        productMap.put("Category",procat);
        productMap.put("Product_Name",proname);
        productMap.put("Product_price",proprice);
        productMap.put("Product_Descrpition",prodesc);
        productMap.put("Product_Specs1",prospec1);
        productMap.put("Product_Specs2",prospec2);
        productMap.put("Product_Specs3",prospec3);
        productMap.put("Product_Specs4",prospec4);
        productMap.put("Stock",prostock);
        productMap.put("status",status);

        productref.child(productkey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
             if(task.isSuccessful()){
                 loadbar.dismiss();
                 Toast.makeText(Add_Product.this,"Products Added Successfully",Toast.LENGTH_SHORT).show();
             }
             else{
                 loadbar.dismiss();
                 String message=task.getException().toString();
                 Toast.makeText(Add_Product.this,"Error"+message,Toast.LENGTH_SHORT).show();
             }
            }
        });


    }


    private void OpenGallery() {
        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GalleryPick && resultCode==RESULT_OK && data!=null){
            imageuri=data.getData();
            pro_image.setImageURI(imageuri);
        }
    }
}