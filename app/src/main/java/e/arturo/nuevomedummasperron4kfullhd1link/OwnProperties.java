package e.arturo.nuevomedummasperron4kfullhd1link;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Locale;

import e.arturo.nuevomedummasperron4kfullhd1link.Database.Database;
import e.arturo.nuevomedummasperron4kfullhd1link.Interface.ItemClickListener;
import e.arturo.nuevomedummasperron4kfullhd1link.Model.House;
import e.arturo.nuevomedummasperron4kfullhd1link.ViewHolder.HouseViewHolder;
import io.paperdb.Paper;

public class OwnProperties extends AppCompatActivity {

    private TextView toolBar_Title;

    FirebaseDatabase database;
    DatabaseReference house;

    RecyclerView recycler_house;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<House,HouseViewHolder> adapter;

    String userID="";

    Database localDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_properties);

        toolBar_Title = findViewById(R.id.toolbar_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolBar_Title.setText("Mis propiedades");
        toolBar_Title.setTextColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        database=FirebaseDatabase.getInstance();
        house=database.getReference("House");

        Paper.init(this);

        recycler_house = findViewById(R.id.cardrecycler);
        recycler_house.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_house.setLayoutManager(layoutManager);

        if (getIntent() != null) {
            userID = getIntent().getStringExtra("userID");
        }
        if (!userID.isEmpty() && userID != null) {
            loadHouse(userID);

        }


    }

    private void loadHouse(String userID) {
        adapter = new FirebaseRecyclerAdapter<House, HouseViewHolder>(House.class,
                R.layout.house_item,
                HouseViewHolder.class,
                house.orderByChild("userID").equalTo(userID)) {
            @Override
            protected void populateViewHolder(final HouseViewHolder viewHolder, final House model, final int position) {
                viewHolder.txtHouseName.setText(model.getTitle());
                viewHolder.txtHousePrice.setText("$"+ model.getPrice());
                viewHolder.txtHouseCurrency.setText(model.getCurrency()+"/mes");
                viewHolder.txtHouseAddress.setText(model.getAddress());
                viewHolder.txtHouseBedroom.setText(model.getBedroom()+ " cuartos");
                viewHolder.txtHouseBathroom.setText(model.getBathroom()+" baños");
                viewHolder.txtHouseSize.setText(model.getSize()+ " m²");
                viewHolder.txtHouseDate.setText(ConvertToDate(model.getDate()));
                Picasso.get().load(model.getImage())
                        .into(viewHolder.imageView);

                final House clickItem = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent restlist = new Intent(OwnProperties.this,HouseDetails.class);
                        restlist.putExtra("HouseID",adapter.getRef(position).getKey());
                        startActivity(restlist);
                    }
                });


            }
        };

        recycler_house.setAdapter(adapter);
    }

    private String ConvertToDate(String date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTimeInMillis(Integer.parseInt(date));
        date = DateFormat.format("yyyy-mm-dd",cal).toString();
        return date;
    }
}
