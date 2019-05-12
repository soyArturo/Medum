package e.arturo.nuevomedummasperron4kfullhd1link.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import e.arturo.nuevomedummasperron4kfullhd1link.Common.Common;
import e.arturo.nuevomedummasperron4kfullhd1link.Interface.ItemClickListener;
import e.arturo.nuevomedummasperron4kfullhd1link.R;

public class HouseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

    public TextView txtHouseName,txtHousePrice,txtHouseAddress,txtHouseBedroom,txtHouseBathroom,txtHouseSize,txtHouseDate,txtHouseCurrency,txtHouseEmail;
    public ImageView imageView,fav_image;

    private ItemClickListener itemClickListener;

    public HouseViewHolder(@NonNull View itemView) {
        super(itemView);

        txtHouseName = itemView.findViewById(R.id.house_name);
        txtHousePrice = itemView.findViewById(R.id.house_price);
        txtHouseCurrency = itemView.findViewById(R.id.house_currency);
        imageView = itemView.findViewById(R.id.house_image);
        fav_image = itemView.findViewById(R.id.house_favorite);
        txtHouseAddress = itemView.findViewById(R.id.house_address);
        txtHouseBedroom = itemView.findViewById(R.id.house_bedroom);
        txtHouseBathroom = itemView.findViewById(R.id.house_bathroom);
        txtHouseSize=itemView.findViewById(R.id.house_size);
        txtHouseDate = itemView.findViewById(R.id.house_date);


        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Selecciona una accion");

        menu.add(0,0,getAdapterPosition(),Common.DELETE);
    }
}
