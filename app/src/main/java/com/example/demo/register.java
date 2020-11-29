package com.example.demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Pattern;

public class register extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    private Button regbtn,regbtn2;
    private TextInputLayout regname,regmail,regpass,regphno;
    private ProgressDialog loadbar;
    private TextView reflink;
    private String currentyear,newrefkey;
    int maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regbtn=(Button)findViewById(R.id.signupbtn);
        regname=findViewById(R.id.regname);
        regmail=findViewById(R.id.regmail);
        regpass=findViewById(R.id.regpass);
        regphno=findViewById(R.id.regphno);
        loadbar=new ProgressDialog(this);





        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });


    }
    private  boolean validateEmail(){
        String emailinput=regmail.getEditText().getText().toString().trim();
        if(emailinput.isEmpty())
        {
            regmail.setError("Field can't be empty");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(emailinput).matches())
        {
            regmail.setError("Please Enter a Valid Email Address");
            return false;

        }
        else {
            regmail.setError(null);
            return true;
        }
    }

    private  boolean validateusername(){
        String username=regname.getEditText().getText().toString().trim();
        if(username.isEmpty())
        {
            regname.setError("Field can't be empty");
            return false;
        }
        else if(username.length() > 25)
        {
            regname.setError("UserName too long");
            return false;
        }
        else {
            regname.setError(null);
            return true;
        }
    }

    private  boolean validateusernumber(){
        String username=regphno.getEditText().getText().toString().trim();
        if(username.isEmpty())
        {
            regphno.setError("Field can't be empty");
            return false;
        }
        else if(username.length() > 10)
        {
            regphno.setError("InValid Phone Number");
            return false;
        }
        else {
            regphno.setError(null);
            return true;
        }
    }

    private  boolean validatepassword(){
        String userpass=regpass.getEditText().getText().toString().trim();
        if(userpass.isEmpty())
        {
            regpass.setError("Field can't be empty");
            return false;
        }
        else if(!PASSWORD_PATTERN.matcher(userpass).matches()){
            regpass.setError("Passwor is too Weak");
            return false;
        }

        else {
            regname.setError(null);
            return true;
        }
    }








    private void CreateAccount()
    {
        String name=regname.getEditText().getText().toString();
        String mail=regmail.getEditText().getText().toString();
        String pass=regpass.getEditText().getText().toString();
        String phno=regphno.getEditText().getText().toString();

         if(!validateEmail() | !validatepassword() | !validateusername() | !validateusernumber())
         {

             return;
         }





        else {
            loadbar.setTitle("create Account");
            loadbar.setMessage("please waait loading...");
            loadbar.setCanceledOnTouchOutside(false);
            loadbar.show();
            validphno(name,mail,pass,phno);
        }
    }

    private void validphno(final  String name, final String mail,final String pass,final String phno)
    {


        final DatabaseReference RootRef,ref;
        ref=FirebaseDatabase.getInstance().getReference().child("Users");
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
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if ((dataSnapshot.child("Users").child(phno).exists()))
                {
                    Toast.makeText(register.this,"This"+phno +"Already Exists..!",Toast.LENGTH_LONG).show();
                    loadbar.dismiss();

                    Intent intent=new Intent(register.this,MainActivity.class);
                    startActivity(intent);



                }



                else{


                    Calendar calendar=Calendar.getInstance();
                    SimpleDateFormat CurrentDate=new SimpleDateFormat("yyyy");
                    currentyear=CurrentDate.format(calendar.getTime());
                    newrefkey=currentyear+00+(maxid+1);
                    HashMap<String,Object> usermap=new HashMap<>();
                    usermap.put("phoneno",phno);
                    usermap.put("email",mail);
                    usermap.put("password",pass);
                    usermap.put("name",name);
                    usermap.put("refkey",newrefkey);
                    RootRef.child("Users").child(phno).updateChildren(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {

                                AddRefTable(newrefkey,phno);
                            }
                            else {
                                loadbar.dismiss();
                                Toast.makeText(register.this,"Error occur please try again latert!",Toast.LENGTH_LONG).show();
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

    private void AddRefTable(final String newrefkey, final String phno)
    {
        final DatabaseReference Addref;
        Addref= FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> usermap=new HashMap<>();

        usermap.put("ref_num",phno);
        Addref.child("reference").child(newrefkey).updateChildren(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful()){

                    UpdateBonus(newrefkey,phno);

                }

            }
        });

    }

    private void UpdateBonus(String newrefkey, final String phno)
    {
        int points=0;
        int total_ref=0;
        final DatabaseReference UpdateBonus;
        UpdateBonus= FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> usermap=new HashMap<>();
        usermap.put("ref_id",newrefkey);
        usermap.put("cust_id",phno);
        usermap.put("points",points);
        usermap.put("Total_ref",total_ref);
        UpdateBonus.child("BonusTable").child(phno).updateChildren(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {

                loadbar.dismiss();
                Intent intent=new Intent(register.this,ReferenceRegister.class);
                intent.putExtra("cust_phno",phno);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Register Success",Toast.LENGTH_LONG).show();
            }
        });
    }


}