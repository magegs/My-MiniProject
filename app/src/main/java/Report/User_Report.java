package Report;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.demo.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import Model.Bonus;
import Model.Products;
import ViewHolder.UserViewHolder;

public class User_Report extends AppCompatActivity {
    private EditText userid;
    private DatabaseReference userdata;
    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<Bonus> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__report);
        userid=(EditText)findViewById(R.id.search_userid);


        recyclerView =(RecyclerView) findViewById(R.id.recycle_userreport);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        arrayList=new ArrayList<>();

    }


    @Override
    protected void onStart() {
        super.onStart();
        userdata = FirebaseDatabase.getInstance().getReference().child("BonusTable");
        FirebaseRecyclerOptions<Bonus> options=new FirebaseRecyclerOptions.Builder<Bonus>().setQuery(userdata, Bonus.class).build();
        FirebaseRecyclerAdapter<Bonus, UserViewHolder> adapter=new FirebaseRecyclerAdapter<Bonus, UserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i, @NonNull Bonus bonus)
            {

              userViewHolder.custname.setText(bonus.getCust_id());


            }

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.userreport,parent,false);
                UserViewHolder holder=new UserViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}