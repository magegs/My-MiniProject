package ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;

import Interface.ItemClickListner;

public class OrderCompleteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView date,paymentmode,totalamount,totalitem;
    public ItemClickListner itemClickListner;
    public OrderCompleteHolder(@NonNull View itemView) {
        super(itemView);
        date=(TextView)itemView.findViewById(R.id.date);
        paymentmode=(TextView)itemView.findViewById(R.id.Payment_Mode);
        totalamount=(TextView)itemView.findViewById(R.id.Total_Amount);
        totalitem=(TextView)itemView.findViewById(R.id.Total_Items);
    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view,getAdapterPosition(),false);
    }
    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
