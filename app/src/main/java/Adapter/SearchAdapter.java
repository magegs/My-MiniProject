package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.ProductDetails;
import com.example.demo.Product_Items;
import com.example.demo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Interface.ItemClickListner;
import Model.CartOrderDetails;
import Model.Products;
import ViewHolder.ProductViewHolder;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyAdapterViewHolder> {
    public Context c;
    public ArrayList<Products>arrayList;

    public SearchAdapter(Context c, ArrayList<Products>arrayList){
        this.c=c;
        this.arrayList=arrayList;
    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.productdata,parent,false);

        return new MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position ) {
        final Products products=arrayList.get(position);
        holder.dispproname.setText(products.getProduct_Name());
        holder.disproprice.setText(products.getProduct_price());
        Picasso.get().load(products.getProduct_Image()).into(holder.dispproimage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(c, ProductDetails.class);
                intent.putExtra("pro_id",products.getProduct_id());
                intent.putExtra("imagepath",products.getProduct_Image().toString());
                c.startActivity(intent);
            }
        });


    }

    public class MyAdapterViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView dispproname,disprodespc,disproprice;
        public ImageView dispproimage;
        public ItemClickListner itemClickListner;

        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            dispproimage=(ImageView)itemView.findViewById(R.id.disp_productimage);
            dispproname=(TextView)itemView.findViewById(R.id.disp_productname);
            disproprice=(TextView)itemView.findViewById(R.id.disp_productprice);
        }


        @Override
        public void onClick(View view)
        {
            itemClickListner.onClick(view,getAdapterPosition(),false);
        }

        public void setItemClickListner(ItemClickListner itemClickListner) {
            this.itemClickListner = itemClickListner;
        }
    }
}
