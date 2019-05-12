package e.arturo.nuevomedummasperron4kfullhd1link.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import e.arturo.nuevomedummasperron4kfullhd1link.Common.Common;
import e.arturo.nuevomedummasperron4kfullhd1link.Database.Database;
import e.arturo.nuevomedummasperron4kfullhd1link.HouseDetails;
import e.arturo.nuevomedummasperron4kfullhd1link.Interface.ItemClickListener;
import e.arturo.nuevomedummasperron4kfullhd1link.Model.Favorites;
import e.arturo.nuevomedummasperron4kfullhd1link.Model.House;
import e.arturo.nuevomedummasperron4kfullhd1link.R;
import e.arturo.nuevomedummasperron4kfullhd1link.ViewHolder.FavoritesAdapter;
import e.arturo.nuevomedummasperron4kfullhd1link.ViewHolder.FavoritesViewHolder;
import e.arturo.nuevomedummasperron4kfullhd1link.ViewHolder.HouseViewHolder;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {


    private TextView toolBar_Title,message;

    RecyclerView recycler_house;
    RecyclerView.LayoutManager layoutManager;

    FavoritesAdapter adapter;

    CoordinatorLayout rootLayout;

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        toolBar_Title = view.findViewById(R.id.toolbar_title);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolBar_Title.setText("Favoritos");
        toolBar_Title.setTextSize(20);
        toolBar_Title.setTextColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        recycler_house = view.findViewById(R.id.recycler_fav);
        recycler_house.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recycler_house.setLayoutManager(layoutManager);

        loadFavorites();

        return view;
    }

    private void loadFavorites() {
        adapter = new FavoritesAdapter(getContext(),new Database(getContext()).getFavorites(Common.currentuser.getPhone()));
        recycler_house.setAdapter(adapter);
    }

}
