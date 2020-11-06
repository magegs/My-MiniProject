package ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;

import Interface.ItemClickListner;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView dispproname,disprodespc,disproprice;
    public ImageView dispproimage;
    public ItemClickListner itemClickListner;

    public ProductViewHolder(@NonNull View itemView)
    {
        super(itemView);
        dispproimage=(ImageView)itemView.findViewById(R.id.disp_productimage);
        dispproname=(TextView)itemView.findViewById(R.id.disp_productname);
        disproprice=(TextView)itemView.findViewById(R.id.disp_productprice);

    }
    public void setItemClickListner(ItemClickListner itemClickListner){
        this.itemClickListner=itemClickListner;
    }

    @Override
    public void onClick(View view)
    {
     itemClickListner.onClick(view,getAdapterPosition(),false);

    }
}
