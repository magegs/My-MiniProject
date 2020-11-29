package ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;

import Interface.ItemClickListner;

public class UpdateProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView proname,pro_id,stock,price;
    public ItemClickListner itemClickListner;
    public UpdateProductHolder(@NonNull View itemView)
    {
        super(itemView);
        proname=(TextView)itemView.findViewById(R.id.proname);
        pro_id=(TextView)itemView.findViewById(R.id.pro_id);
        price=(TextView)itemView.findViewById(R.id.proprice);
        stock=(TextView)itemView.findViewById(R.id.prostock);
    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view,getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
