package e.arturo.nuevomedummasperron4kfullhd1link.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import e.arturo.nuevomedummasperron4kfullhd1link.Common.Common;
import e.arturo.nuevomedummasperron4kfullhd1link.HouseDetails;
import e.arturo.nuevomedummasperron4kfullhd1link.Interface.ItemClickListener;
import e.arturo.nuevomedummasperron4kfullhd1link.Model.House;
import e.arturo.nuevomedummasperron4kfullhd1link.OwnProperties;
import e.arturo.nuevomedummasperron4kfullhd1link.R;
import e.arturo.nuevomedummasperron4kfullhd1link.SignIn;
import e.arturo.nuevomedummasperron4kfullhd1link.ViewHolder.HouseViewHolder;
import info.androidhive.fontawesome.FontTextView;
import io.paperdb.Paper;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView username,userPhone,seemore;
    private FontTextView logout;

    FirebaseDatabase database;
    DatabaseReference house;
    private Query houseQuery;

    RecyclerView recycler_house;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<House,HouseViewHolder> adapter;

    private String userID;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        database=FirebaseDatabase.getInstance();
        house=database.getReference("House");

        Paper.init(getActivity());

        username = view.findViewById(R.id.usernameprofile);
        userPhone = view.findViewById(R.id.userphoneprofile);
        logout = view.findViewById(R.id.logout);
        seemore = view.findViewById(R.id.seemore);

        username.setText(Common.currentuser.getName());
        userPhone.setText(Common.currentuser.getPhone());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().destroy();
                Intent signIn = new Intent(getActivity(),SignIn.class);
                signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signIn);
            }
        });

        seemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restlist = new Intent(getActivity(), OwnProperties.class);
                restlist.putExtra("userID",Common.currentuser.getPhone());
                startActivity(restlist);
            }
        });

      /*  recycler_house = view.findViewById(R.id.cardrecycler);
        recycler_house.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recycler_house.setLayoutManager(layoutManager);
        loadMenu();*/
        return view;
    }

    private void loadMenu() {
        adapter = new FirebaseRecyclerAdapter<House, HouseViewHolder>(House.class,
                R.layout.house_item,
                HouseViewHolder.class,house) {
            @Override
            protected void populateViewHolder(HouseViewHolder viewHolder, House model, int position) {
                viewHolder.txtHouseName.setText(model.getTitle());
                viewHolder.txtHousePrice.setText("$ "+ model.getPrice()+ " MX");
                Picasso.get().load(model.getImage())
                        .into(viewHolder.imageView);
                final House clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent restlist = new Intent(getActivity(),HouseDetails.class);
                        restlist.putExtra("HouseID",adapter.getRef(position).getKey());
                        startActivity(restlist);
                    }
                });
            }
        };
        recycler_house.setAdapter(adapter);
    }

    private void query() {
        houseQuery = house.child("House")
                .orderByChild("title").equalTo(userID);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Common.DELETE))
            deleteHouse(adapter.getRef(item.getOrder()).getKey());
        return super.onContextItemSelected(item);
    }

    private void deleteHouse(String key) {
        house.child(key).removeValue();
    }

}
