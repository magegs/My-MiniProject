package ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;

import Interface.ItemClickListner;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView  proname,proprice,proquantity;
    public ImageView proimage;
    public Button edti_btn,delete_btn;
    public ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView)
    {
        super(itemView);
        proname=itemView.findViewById(R.id.cart_proname);
        proprice=itemView.findViewById(R.id.cart_price);
        proimage=itemView.findViewById(R.id.cart_productimage);
        proquantity=itemView.findViewById(R.id.cart_quantity);

        edti_btn=itemView.findViewById(R.id.cart_editpro);
        delete_btn=itemView.findViewById(R.id.cart_deletepro);

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
