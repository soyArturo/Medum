package e.arturo.nuevomedummasperron4kfullhd1link.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import e.arturo.nuevomedummasperron4kfullhd1link.Common.Common;
import e.arturo.nuevomedummasperron4kfullhd1link.Database.Database;
import e.arturo.nuevomedummasperron4kfullhd1link.Model.House;
import e.arturo.nuevomedummasperron4kfullhd1link.Model.Order;
import e.arturo.nuevomedummasperron4kfullhd1link.Model.Requests;
import e.arturo.nuevomedummasperron4kfullhd1link.Model.User;
import e.arturo.nuevomedummasperron4kfullhd1link.R;
import e.arturo.nuevomedummasperron4kfullhd1link.ViewHolder.CartAdapter;
import info.androidhive.fontawesome.FontDrawable;

public class CartFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView toolBar_Title;
    Button btnPlace;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    Order house = new Order();

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        toolBar_Title = view.findViewById(R.id.toolbar_title);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolBar_Title.setText("Interes");
        toolBar_Title.setTextSize(20);
        toolBar_Title.setTextColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

       recyclerView = view.findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        btnPlace = view.findViewById(R.id.placeOrder);
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
        loadFoodList();
        return view;
    }

    private void showAlertDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Ya casi esta listo!");
        alertDialog.setMessage("Ingresa un comentario para el propietario de la casa: ");

        final EditText editComment = new EditText(getContext());
        editComment.setHintTextColor(getResources().getColor(R.color.colorPrimary));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        editComment.setLayoutParams(lp);
        alertDialog.setView(editComment);
        final FontDrawable houseAdd = new FontDrawable(getActivity(),R.string.fa_comment_alt, false, false);
        houseAdd.setTextColor(getResources().getColor(R.color.colorPrimary));
        alertDialog.setIcon(houseAdd);

        alertDialog.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Requests request = new Requests(
                        Common.currentuser.getPhone(),
                        Common.currentuser.getName(),
                        Common.currentuser.getEmail(),
                        editComment.getText().toString(),
                        cart
                );

                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);

                new Database(getContext()).cleanCart();
                goHome();
                Toast.makeText(getContext(), "Gracias,tu mensaje se ha enviado", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void goHome() {
        HomeFragment home = new HomeFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container,home)
                .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                .addToBackStack(null).commit();
    }

    private void loadFoodList() {
        cart = new Database(getActivity()).getCarts();
        adapter = new CartAdapter(cart,getActivity());
        recyclerView.setAdapter(adapter);
    }

}
