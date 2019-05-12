package e.arturo.nuevomedummasperron4kfullhd1link.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import e.arturo.nuevomedummasperron4kfullhd1link.HouseDetails;
import e.arturo.nuevomedummasperron4kfullhd1link.Interface.ItemClickListener;
import e.arturo.nuevomedummasperron4kfullhd1link.Model.Favorites;
import e.arturo.nuevomedummasperron4kfullhd1link.Model.House;
import e.arturo.nuevomedummasperron4kfullhd1link.R;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesViewHolder> {

    private Context context;
    private List<Favorites> listData = new ArrayList<>();

    public FavoritesAdapter(Context context, List<Favorites> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View itemView = LayoutInflater.from(context)
               .inflate(R.layout.favorites_item,viewGroup,false);
       return new FavoritesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder viewHolder, int i) {
        viewHolder.txtHouseName.setText(listData.get(i).getTitle());
        viewHolder.txtHousePrice.setText("$"+ listData.get(i).getPrice());
        viewHolder.txtHouseCurrency.setText(listData.get(i).getCurrency()+"/mes");
        viewHolder.txtHouseAddress.setText(listData.get(i).getAddress());
        viewHolder.txtHouseBedroom.setText(listData.get(i).getBedroom()+ " cuartos");
        viewHolder.txtHouseBathroom.setText(listData.get(i).getBathroom()+" baños");
        viewHolder.txtHouseSize.setText(listData.get(i).getSize()+ " m²");
        viewHolder.txtHouseDate.setText(ConvertToDate(listData.get(i).getDate()));
        Picasso.get().load(listData.get(i).getImage())
                .into(viewHolder.imageView);

        final Favorites clickItem = listData.get(i);
        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent restlist = new Intent(context, HouseDetails.class);
                restlist.putExtra("HouseID",listData.get(position).getHouseId());
                context.startActivity(restlist);
            }
        });
    }

    private String ConvertToDate(String date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTimeInMillis(Integer.parseInt(date));
        date = DateFormat.format("yyyy-mm-dd",cal).toString();
        return date;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public Favorites getItem(int position){
        return listData.get(position);
    }
}
