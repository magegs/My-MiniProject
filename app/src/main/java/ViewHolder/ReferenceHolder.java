package ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;

import Interface.ItemClickListner;

public class ReferenceHolder  extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView custid;
    public ItemClickListner itemClickListner;

    public ReferenceHolder(@NonNull View itemView) {
        super(itemView);
        custid=(TextView)itemView.findViewById(R.id.jp_id);
    }
    public void setItemClickListner(ItemClickListner itemClickListner){
        this.itemClickListner=itemClickListner;
    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view,getAdapterPosition(),false);
    }
}
