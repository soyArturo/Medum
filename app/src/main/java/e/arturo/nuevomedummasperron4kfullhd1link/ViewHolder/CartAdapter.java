package e.arturo.nuevomedummasperron4kfullhd1link.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import e.arturo.nuevomedummasperron4kfullhd1link.Interface.ItemClickListener;
import e.arturo.nuevomedummasperron4kfullhd1link.Model.Order;
import e.arturo.nuevomedummasperron4kfullhd1link.R;

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txt_cart_name,txt_cart_phone,txt_cart_address;

    private ItemClickListener itemClickListener;

    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_cart_name= itemView.findViewById(R.id.house_item_name);
        txt_cart_phone = itemView.findViewById(R.id.house_item_phone);
        txt_cart_address = itemView.findViewById(R.id.house_item_address);
    }

    @Override
    public void onClick(View v) {

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> listData = new ArrayList<>();
    private android.content.Context context;

    public CartAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout,viewGroup,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int i) {
        holder.txt_cart_phone.setText(listData.get(i).getPhone());
        holder.txt_cart_name.setText(listData.get(i).getHouseName());
        holder.txt_cart_address.setText(listData.get(i).getHouseAddress());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
