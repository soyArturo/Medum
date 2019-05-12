package e.arturo.nuevomedummasperron4kfullhd1link;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import e.arturo.nuevomedummasperron4kfullhd1link.Common.Common;
import e.arturo.nuevomedummasperron4kfullhd1link.Database.Database;
import e.arturo.nuevomedummasperron4kfullhd1link.Model.House;
import e.arturo.nuevomedummasperron4kfullhd1link.Model.Order;
import info.androidhive.fontawesome.FontDrawable;

public class HouseDetails extends AppCompatActivity {
    private TextView house_name,house_price,house_description,house_address,house_bed,house_bath,house_size,house_phone,house_email;
    private ImageView food_image;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton btnCart;

    String houseId="";
    StringBuilder sb = new StringBuilder();

    private FirebaseDatabase database;
    private DatabaseReference house;

    House currentHouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_details);

        database = FirebaseDatabase.getInstance();
        house = database.getReference("House");

        btnCart = findViewById(R.id.btnCart);

        FontDrawable drawable = new FontDrawable(this,R.string.fa_envelope_solid, true, false);
        drawable.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        btnCart.setImageDrawable(drawable);

        house_address = findViewById(R.id.housedtl_address);
        house_bed = findViewById(R.id.housedtl_bed);
        house_bath = findViewById(R.id.housedtl_bath);
        house_size = findViewById(R.id.housedtl_size);
        house_name = findViewById(R.id.housedtl_name);
        house_price = findViewById(R.id.housedtl_price);
        house_description = findViewById(R.id.housedtl_description);
        house_phone = findViewById(R.id.housedtl_phone);
        food_image = findViewById(R.id.img_food);
        house_email = findViewById(R.id.housedtl_email);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        if(getIntent()!=null){
            houseId= getIntent().getStringExtra("HouseID");
        }
        if(!houseId.isEmpty()){
            getDetailHouse(houseId);
        }

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailDialog();
               /*new Database(getBaseContext()).addCart(new Order(
                       houseId,
                       currentHouse.getTitle(),
                       currentHouse.getPhone(),
                       currentHouse.getAddress(),
                       currentHouse.getEmail()
               ));
                Toast.makeText(getBaseContext(),"Agregado al carrito",Toast.LENGTH_SHORT).show();*/
            }
        });

        house_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String currentAddress = currentHouse.getLatLng();
                    String[] separated = currentAddress.split(",");
                    String addressName = currentHouse.getAddress();
                    Uri gmmIntentUri = Uri.parse("geo:"+separated[0]+","+separated[1]+"?"+"z=20&q="+separated[0]+","+separated[1]+(addressName));
                    if(gmmIntentUri!= null) {
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                    else
                        Toast.makeText(HouseDetails.this, "No cuenta con ubicacion", Toast.LENGTH_SHORT).show();
                }catch (Exception e) {
                    Toast.makeText(HouseDetails.this, "No cuenta con ubicacion", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void emailDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Ya casi esta listo!");
        alertDialog.setMessage("Ingresa un comentario para el propietario de la casa: ");

        final EditText editComment = new EditText(this);
        editComment.setHintTextColor(getResources().getColor(R.color.colorPrimary));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        editComment.setLayoutParams(lp);
        alertDialog.setView(editComment);
        final FontDrawable houseAdd = new FontDrawable(this,R.string.fa_comment_alt, false, false);
        houseAdd.setTextColor(getResources().getColor(R.color.colorPrimary));
        alertDialog.setIcon(houseAdd);

        alertDialog.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent it = new Intent(Intent.ACTION_SEND);
                it.putExtra(Intent.EXTRA_EMAIL, new String[]{currentHouse.getEmail()});
                it.putExtra(Intent.EXTRA_SUBJECT, "Interes en propiedad " + currentHouse.getTitle());
                sb.append("Hola");
                sb.append('\n');
                sb.append("El usuario " + Common.currentuser.getName() + " esta interesado en la siguiente propiedad:");
                sb.append('\n');
                sb.append(currentHouse.getTitle());
                sb.append('\n');
                sb.append("Comentario: "+ editComment.getText().toString());
                sb.append('\n');
                sb.append("Direccion: " + currentHouse.getAddress());
                sb.append('\n');
                sb.append('\n');
                sb.append('\n');
                sb.append("Informacion del interesado:");
                sb.append('\n');
                sb.append(Common.currentuser.getPhone());
                sb.append('\n');
                sb.append(Common.currentuser.getEmail());
                it.putExtra(Intent.EXTRA_TEXT, sb.toString());
                it.setType("message/rfc822");
                startActivity(Intent.createChooser(it,"Choose Mail App"));
            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void getDetailHouse(String houseId) {
        house.child(houseId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentHouse = dataSnapshot.getValue(House.class);

                Picasso.get().load(currentHouse.getImage())
                        .into(food_image);

               /* collapsingToolbarLayout.setTitle(currentHouse.getTitle());*/
                house_address.setText(currentHouse.getAddress());
                house_bed.setText(currentHouse.getBedroom());
                house_bath.setText(currentHouse.getBathroom());
                house_size.setText(currentHouse.getSize());
                house_name.setText(currentHouse.getTitle());
                house_price.setText(currentHouse.getPrice());
                house_phone.setText(currentHouse.getPhone());
                house_email.setText(currentHouse.getEmail());
                house_description.setText(currentHouse.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
