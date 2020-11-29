package ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;

import Interface.ItemClickListner;

public class CartOrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView items,customer_id,order_placedon,order_status,payment_mode;
    public ItemClickListner itemClickListner;

    public CartOrderHolder(@NonNull View itemView)
    {
        super(itemView);

        customer_id=(TextView)itemView.findViewById(R.id.customer_id);
        order_placedon=(TextView)itemView.findViewById(R.id.order_date);
        order_status=(TextView)itemView.findViewById(R.id.order_status);
        payment_mode=(TextView)itemView.findViewById(R.id.order_paymentmode);

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
