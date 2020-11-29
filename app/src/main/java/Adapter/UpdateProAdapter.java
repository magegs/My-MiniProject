package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.ProductDetails;
import com.example.demo.R;
import com.example.demo.UpdateProductDetails;
import com.example.demo.UpdateTrack;

import java.util.ArrayList;

import Interface.ItemClickListner;
import Model.CartOrderDetails;
import Model.Products;

public class UpdateProAdapter extends RecyclerView.Adapter<UpdateProAdapter.MyAdapterViewHolder> {
    public Context c;
    public ArrayList<Products> arrayList;

    public UpdateProAdapter(Context c, ArrayList<Products> arrayList) {
        this.c = c;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view= LayoutInflater.from(parent.getContext()).inflate(R.layout.updateproduct,parent,false);

        return new UpdateProAdapter.MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateProAdapter.MyAdapterViewHolder holder, int position)
    {
        final Products products=arrayList.get(position);
        holder.pro_id.setText(products.getProduct_id());
        holder.proname.setText(products.getProduct_Name());
        holder.stock.setText(products.getStock());
        holder.price.setText(products.getProduct_price());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c, UpdateProductDetails.class);
                intent.putExtra("pro_id",products.getProduct_id());
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
        public TextView proname,pro_id,stock,price;
        public ItemClickListner itemClickListner;
        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            proname=(TextView)itemView.findViewById(R.id.proname);
            pro_id=(TextView)itemView.findViewById(R.id.pro_id);
            price=(TextView)itemView.findViewById(R.id.proprice);
            stock=(TextView)itemView.findViewById(R.id.prostock);
        }
    }
}
