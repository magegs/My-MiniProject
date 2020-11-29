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
import com.example.demo.UpdateOrders;
import com.example.demo.UpdateTrack;

import java.util.ArrayList;

import Interface.ItemClickListner;
import Model.CartOrderDetails;
import Model.Products;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyAdapterViewHolder> {
    public Context c;
    public ArrayList<CartOrderDetails> arrayList;

    public CartAdapter(Context c, ArrayList<CartOrderDetails> arrayList) {
        this.c = c;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view= LayoutInflater.from(parent.getContext()).inflate(R.layout.updateorder,parent,false);

        return new CartAdapter.MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position)
    {
        final CartOrderDetails cartOrderDetails=arrayList.get(position);
        holder.customer_id.setText(cartOrderDetails.getCart_id());
        holder.order_placedon.setText(cartOrderDetails.getDate());
        holder.order_status.setText(cartOrderDetails.getStatus());
        holder.payment_mode.setText(cartOrderDetails.getPaymentMode());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(c, UpdateTrack.class);
                intent.putExtra("cust_id",cartOrderDetails.getCart_id());
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
        public TextView items,customer_id,order_placedon,order_status,payment_mode;
        public ItemClickListner itemClickListner;
        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            customer_id=(TextView)itemView.findViewById(R.id.customer_id);
            order_placedon=(TextView)itemView.findViewById(R.id.order_date);
            order_status=(TextView)itemView.findViewById(R.id.order_status);
            payment_mode=(TextView)itemView.findViewById(R.id.order_paymentmode);
        }
    }
}
