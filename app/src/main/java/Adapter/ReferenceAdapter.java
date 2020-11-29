package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.example.demo.ViewReferenceDetails;

import java.util.ArrayList;

import Interface.ItemClickListner;
import Model.Bonus;

public class ReferenceAdapter extends RecyclerView.Adapter<ReferenceAdapter.MyAdapterViewHolder>{
    public Context c;
    public ArrayList<Bonus> arrayList;

    public ReferenceAdapter(Context c, ArrayList<Bonus> arrayList) {
        this.c = c;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ReferenceAdapter.MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view= LayoutInflater.from(parent.getContext()).inflate(R.layout.userreport,parent,false);

        return new ReferenceAdapter.MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReferenceAdapter.MyAdapterViewHolder holder, int position)
    {
        final Bonus bonus=arrayList.get(position);
        holder.number.setText(bonus.getCust_id());
        holder.refid.setText(bonus.getRef_id());
        holder.points.setText(bonus.getPoints().toString());
        holder.totalref.setText(bonus.getTotal_ref().toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(c, ViewReferenceDetails.class);
                intent.putExtra("uid",bonus.getCust_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                c.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView number,custname,totalref,points,refid;
        public ItemClickListner itemClickListner;
        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            number=(TextView)itemView.findViewById(R.id.userreport_number);
            totalref=(TextView)itemView.findViewById(R.id.table_totalref);
            points=(TextView)itemView.findViewById(R.id.table_userpoints);
            refid=(TextView)itemView.findViewById(R.id.table_userrefnum);
        }
    }
}
