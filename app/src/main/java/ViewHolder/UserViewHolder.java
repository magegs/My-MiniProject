package ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;

import Interface.ItemClickListner;

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView number,custname,totalref,points,refid;

    public ItemClickListner itemClickListner;



    public UserViewHolder(@NonNull View itemView)
    {
        super(itemView);
        number=(TextView)itemView.findViewById(R.id.userreport_number);

        totalref=(TextView)itemView.findViewById(R.id.table_totalref);
        points=(TextView)itemView.findViewById(R.id.table_userpoints);
        refid=(TextView)itemView.findViewById(R.id.table_userrefnum);


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
