package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapter.ReferenceAdapter;
import Adapter.UserAdapter;
import Model.Bonus;
import ViewHolder.UserViewHolder;

public class ViewUserReference extends AppCompatActivity {
    private EditText search_custid;
    private DatabaseReference userdata;
    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<Bonus> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_reference);
        search_custid=(EditText)findViewById(R.id.search_custid);
        recyclerView =(RecyclerView) findViewById(R.id.viewreferencesrecycle);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>();
        search_custid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(!editable.toString().isEmpty()){
                    search(editable.toString());
                }
                else{
                    search("");
                }

            }
        });

        
    }

    private void search(String toString)
    {
        final DatabaseReference userdata= FirebaseDatabase.getInstance().getReference().child("BonusTable");
        Query query=userdata.orderByChild("cust_id").startAt(toString).endAt(toString+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.hasChildren())
                {
                    arrayList.clear();
                    for (DataSnapshot dss:dataSnapshot.getChildren())
                    {
                        final Bonus bonus=dss.getValue(Bonus.class);
                        arrayList.add(bonus);
                    }
                    ReferenceAdapter referenceAdapter=new ReferenceAdapter(getApplicationContext(),arrayList);
                    recyclerView.setAdapter(referenceAdapter);
                    referenceAdapter.notifyDataSetChanged();

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
        final DatabaseReference refdata= FirebaseDatabase.getInstance().getReference().child("BonusTable");
        FirebaseRecyclerOptions<Bonus> options=new FirebaseRecyclerOptions.Builder<Bonus>().setQuery(refdata,Bonus.class).build();
        FirebaseRecyclerAdapter<Bonus, UserViewHolder> adapter=new FirebaseRecyclerAdapter<Bonus, UserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i, @NonNull final Bonus bonus)
            {
                userViewHolder.number.setText(bonus.getCust_id());
                userViewHolder.refid.setText(bonus.getRef_id());
                userViewHolder.points.setText(bonus.getPoints().toString());
                userViewHolder.totalref.setText(bonus.getTotal_ref().toString());
                userViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(getApplicationContext(),ViewReferenceDetails.class);
                        intent.putExtra("uid",bonus.getCust_id());
                        intent.putExtra("refid",bonus.getRef_id().toString());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.userreport,parent,false);
                UserViewHolder holder=new UserViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}