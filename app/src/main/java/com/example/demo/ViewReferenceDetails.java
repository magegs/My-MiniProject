package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.Bonus;
import Model.Products;
import Model.Reference;
import ViewHolder.ReferenceHolder;
import ViewHolder.UserViewHolder;

public class ViewReferenceDetails extends AppCompatActivity {
    private TextView number,custname,totalref,points,refid;
    private String uid="";
    private String getrefid="";
    String childrefid;
    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uid=getIntent().getStringExtra("uid");
        getrefid=getIntent().getStringExtra("refid");
        childrefid=String.valueOf(getrefid);
        setContentView(R.layout.activity_view_reference_details);
        number=(TextView)findViewById(R.id.userreport_number);
        totalref=(TextView)findViewById(R.id.table_totalref);
        points=(TextView)findViewById(R.id.table_userpoints);
        refid=(TextView)findViewById(R.id.table_userrefnum);
        recyclerView=(RecyclerView)findViewById(R.id.referencerecycle);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference refdata= FirebaseDatabase.getInstance().getReference().child("reference").child("202001");
        FirebaseRecyclerOptions<Reference> options=new FirebaseRecyclerOptions.Builder<Reference>().setQuery(refdata,Reference.class).build();
        FirebaseRecyclerAdapter<Reference, ReferenceHolder>adapter=new FirebaseRecyclerAdapter<Reference, ReferenceHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ReferenceHolder referenceHolder, int i, @NonNull Reference reference)
            {
                referenceHolder.custid.setText(reference.getRef_num());
            }

            @NonNull
            @Override
            public ReferenceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.jp_id,parent,false);
                ReferenceHolder holder=new ReferenceHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();


        final DatabaseReference refview= FirebaseDatabase.getInstance().getReference().child("BonusTable").child(uid);
        refview.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
               if(dataSnapshot.exists())
               {
                   Bonus bonus=dataSnapshot.getValue(Bonus.class);
                   number.setText(bonus.getCust_id());
                   refid.setText(bonus.getRef_id());
                   points.setText(bonus.getPoints().toString());
                   totalref.setText(bonus.getTotal_ref().toString());
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {


            }
        });
    }
}