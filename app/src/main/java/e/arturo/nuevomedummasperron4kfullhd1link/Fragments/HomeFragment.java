package e.arturo.nuevomedummasperron4kfullhd1link.Fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import e.arturo.nuevomedummasperron4kfullhd1link.AddHouseActivity;
import e.arturo.nuevomedummasperron4kfullhd1link.Common.Common;
import e.arturo.nuevomedummasperron4kfullhd1link.Database.Database;
import e.arturo.nuevomedummasperron4kfullhd1link.HouseDetails;
import e.arturo.nuevomedummasperron4kfullhd1link.Interface.ItemClickListener;
import e.arturo.nuevomedummasperron4kfullhd1link.Model.Favorites;
import e.arturo.nuevomedummasperron4kfullhd1link.Model.House;
import e.arturo.nuevomedummasperron4kfullhd1link.Model.User;
import e.arturo.nuevomedummasperron4kfullhd1link.R;
import e.arturo.nuevomedummasperron4kfullhd1link.ViewHolder.HouseViewHolder;
import info.androidhive.fontawesome.FontDrawable;
import info.androidhive.fontawesome.FontTextView;
import io.paperdb.Paper;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private TextView toolBar_Title;

    //Variables de Firebase
    FirebaseDatabase database;
    DatabaseReference house;
    FirebaseStorage storage;
    StorageReference storageReference;

    //RecyclerView de las casas
    RecyclerView recycler_house;
    RecyclerView.LayoutManager layoutManager;

    //Adaptador
    FirebaseRecyclerAdapter<House,HouseViewHolder> adapter;

    MaterialEditText edtName,edtPrice,edtDescription,edtPhone,edtBed,edtBath,edtSize;
    AutocompleteSupportFragment edtAddress;
    String houseAddress;
    Spinner edtCurrency;
    Button btnUpload,btnSelect;
    FontTextView userProfile;

    House newHouse;
    Uri saveUri;
    User user;
    Place address;
    private final int PICK_IMAGE_REQUEST=71;
    BottomNavigationView bottomNavigationView;

    Database localDB;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        toolBar_Title = view.findViewById(R.id.toolbar_title);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolBar_Title.setText(getResources().getString(R.string.app_name));
        toolBar_Title.setTextSize(28);
        toolBar_Title.setTextColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        //Instancia para firebase
        database=FirebaseDatabase.getInstance();
        house=database.getReference("House");
        storage=FirebaseStorage.getInstance();
        storageReference =storage.getReference();

        // Initialize Places.
        Places.initialize(getActivity(), "AIzaSyBbjEl9A0av9cpDEa7fNYiniH655SNa-qc");
        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(getActivity());

        localDB = new Database(getActivity());

        //https://github.com/pilgr/Paper, se usa para recordar la sesion del usuario
        Paper.init(getActivity());

        FloatingActionButton fab =  view.findViewById(R.id.fab);

        FontDrawable drawable = new FontDrawable(getActivity(),R.string.fa_plus_solid, true, false);
        drawable.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
        fab.setImageDrawable(drawable);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialogo();
                Intent addHouse = new Intent(getActivity(), AddHouseActivity.class);
                startActivity(addHouse);
            }
        });

        //Se inicializa el recyclerview para visualizar las publicaciones
        recycler_house = view.findViewById(R.id.recycler_house);
        recycler_house.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recycler_house.setLayoutManager(layoutManager);
        //Metodo para poder mostrar las casas
        loadMenu();
        return view;
    }

    //Metodo para agregar las casas
   /* private void showDialogo() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Agregar nueva casa");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_menu = inflater.inflate(R.layout.add_house_layout,null);

        edtName = add_menu.findViewById(R.id.edtName);
        edtPrice = add_menu.findViewById(R.id.edtPrice);
        edtCurrency = add_menu.findViewById(R.id.edtCurrency);
        edtDescription = add_menu.findViewById(R.id.edtDescription);
        edtAddress = (AutocompleteSupportFragment)
                getActivity().getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragments);

        // Specify the types of place data to return.
        edtAddress.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG,Place.Field.NAME));
        edtAddress.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(com.google.android.libraries.places.api.model.Place place) {
                // TODO: Get info about the selected place.
                address = place;
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.e("Error",status.getStatusMessage());
            }
        });
        edtPhone = add_menu.findViewById(R.id.edtPhone);
        edtBed = add_menu.findViewById(R.id.edtBedroom);
        edtBath = add_menu.findViewById(R.id.edtBathroom);
        edtSize = add_menu.findViewById(R.id.edtSize);
        btnSelect = add_menu.findViewById(R.id.select);
        btnUpload = add_menu.findViewById(R.id.upload);

        List<String> currency = new ArrayList<String>();
        currency.add("mx");
        currency.add("usd");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,currency);
        dataAdapter.setDropDownViewResource(R.layout.item_spinner);
        edtCurrency.setAdapter(dataAdapter);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Metodo para seleccionar imagen del telefono
                chooseImage();

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Metodo para subir la foto a la BD
                uploadImage();

            }
        });


        alertDialog.setView(add_menu);
        FontDrawable houseAdd = new FontDrawable(getActivity(),R.string.fa_home_solid, true, false);
        alertDialog.setIcon(houseAdd);
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(newHouse!=null){
                    DatabaseReference pushRef = house.push();
                    String pushId = pushRef.getKey();
                    newHouse.setPushId(pushId);
                    pushRef.setValue(newHouse);
                    Snackbar.make(getView(),"Nueva casa "+newHouse.getTitle()+" fue agregada",Snackbar.LENGTH_SHORT).show();
                }

            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }*/

    private void uploadImage() {
        if(saveUri!=null){
            final ProgressDialog mDialog = new ProgressDialog(getContext());
            mDialog.setMessage("Subiendo");
            mDialog.show();

            String imageName = UUID.randomUUID().toString();
            final StorageReference imageFolder = storageReference.child("images/"+imageName);
            imageFolder.putFile(saveUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDialog.dismiss();
                            Toast.makeText(getContext(),"Subido",Toast.LENGTH_SHORT).show();
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //Aqui esta los datos que se subiran a la BD
                                    newHouse = new House(edtName.getText().toString(),
                                            houseAddress=address.getAddress().toString(),
                                            String.format(("%s,%s"),address.getLatLng().latitude,address.getLatLng().longitude),
                                            uri.toString(),
                                            edtDescription.getText().toString(),
                                            edtPrice.getText().toString(),
                                            edtCurrency.getSelectedItem().toString(),
                                            edtPhone.getText().toString(),
                                            Common.currentuser.getEmail(),
                                            getcurrentTimeStamp().toString(),
                                            edtBed.getText().toString(),
                                            edtBath.getText().toString(),
                                            edtSize.getText().toString(),
                                            Common.currentuser.getPhone()                                            );
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mDialog.dismiss();
                            Toast.makeText(getContext(),""+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress =(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            mDialog.setMessage("Subido"+progress+"%");
                        }
                    });
        }
    }

    private Long getcurrentTimeStamp() {
        Long timestamp = System.currentTimeMillis()/1000;
        return timestamp;
    }

    /*Cuando se seleccione la imagen y regrese a la actividad pasada
    *
    * y si sale bien el boton cambiada de texto
    * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK
                && data!=null &&data.getData()!=null){
            saveUri = data.getData();
            btnSelect.setText("Imagen seleccionada");
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Seleccionar imagen"),PICK_IMAGE_REQUEST);
    }

    private void loadMenu() {
        adapter = new FirebaseRecyclerAdapter<House, HouseViewHolder>(House.class,R.layout.house_item,HouseViewHolder.class,house) {
            @Override
            //Aqui se pone lo que se cargara en el Cardiew
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

               if(localDB.isFavorites(adapter.getRef(position).getKey()))
                    viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_black_24dp);

                viewHolder.fav_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Favorites favorites = new Favorites();
                        favorites.setHouseId(adapter.getRef(position).getKey());
                        favorites.setTitle(model.getTitle());
                        favorites.setAddress(model.getAddress());
                        favorites.setLatLng(model.getLatLng());
                        favorites.setImage(model.getImage());
                        favorites.setDescription(model.getDescription());
                        favorites.setPrice(model.getPrice());
                        favorites.setCurrency(model.getCurrency());
                        favorites.setPhone(Common.currentuser.getPhone());
                        favorites.setDate(model.getDate());
                        favorites.setBedroom(model.getBedroom());
                        favorites.setBathroom(model.getBathroom());
                        favorites.setSize(model.getSize());
                        favorites.setUserID(model.getUserID());


                        if(!localDB.isFavorites(adapter.getRef(position).getKey())){
                            localDB.addToFavorites(favorites);
                            viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_black_24dp);
                            viewHolder.fav_image.setColorFilter(getContext().getResources().getColor(R.color.colorAccent));
                            Toast.makeText(getContext(), ""+model.getTitle()+" fue agregado a favoritos", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            localDB.removeFromFavorites(adapter.getRef(position).getKey());
                            viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                            Toast.makeText(getContext(), ""+model.getTitle()+" fue removido de favoritos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

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

    private String ConvertToDate(String date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTimeInMillis(Integer.parseInt(date));
        date = DateFormat.format("yyyy-mm-dd",cal).toString();
        return date;
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
