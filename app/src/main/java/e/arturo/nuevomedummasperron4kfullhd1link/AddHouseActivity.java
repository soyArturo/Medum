package e.arturo.nuevomedummasperron4kfullhd1link;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import com.google.android.libraries.places.api.model.Place;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import e.arturo.nuevomedummasperron4kfullhd1link.Common.Common;
import e.arturo.nuevomedummasperron4kfullhd1link.Database.Database;
import e.arturo.nuevomedummasperron4kfullhd1link.Model.House;
import e.arturo.nuevomedummasperron4kfullhd1link.Model.User;
import e.arturo.nuevomedummasperron4kfullhd1link.ViewHolder.HouseViewHolder;

public class AddHouseActivity extends AppCompatActivity {

    private TextView toolBar_Title;

    //Variables de Firebase
    FirebaseDatabase database;
    DatabaseReference house;
    FirebaseStorage storage;
    StorageReference storageReference;

    MaterialEditText edtName,edtPrice,edtDescription,edtPhone,edtBed,edtBath,edtSize;
    AutocompleteSupportFragment edtAddress;
    String houseAddress;
    Spinner edtCurrency;
    Button btnUpload,btnSelect,btnSave;

    House newHouse;
    Uri saveUri;
    User user;
    Place address;
    private final int PICK_IMAGE_REQUEST=71;

    Database localDB;

    RelativeLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolBar_Title = findViewById(R.id.toolbar_title);
        toolBar_Title.setText("Agregar propiedad");
        toolBar_Title.setTextSize(20);
        toolBar_Title.setTextColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        database=FirebaseDatabase.getInstance();
        house=database.getReference("House");
        storage=FirebaseStorage.getInstance();
        storageReference =storage.getReference();

        // Initialize Places.
        Places.initialize(getApplicationContext(), "AIzaSyBbjEl9A0av9cpDEa7fNYiniH655SNa-qc");
        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.



        localDB = new Database(this);

        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtCurrency = findViewById(R.id.edtCurrency);
        edtDescription = findViewById(R.id.edtDescription);
        edtAddress = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        edtAddress.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG,Place.Field.NAME));
        edtAddress.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                address = place;
                Log.i("Si funciono","Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.e("Error",status.getStatusMessage());
            }
        });
        edtPhone = findViewById(R.id.edtPhone);
        edtBed = findViewById(R.id.edtBedroom);
        edtBath = findViewById(R.id.edtBathroom);
        edtSize = findViewById(R.id.edtSize);
        btnSelect = findViewById(R.id.select);
        btnUpload = findViewById(R.id.upload);
        btnSave = findViewById(R.id.saveHouse);

        List<String> currency = new ArrayList<String>();
        currency.add("mx");
        currency.add("usd");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,currency);
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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newHouse!=null){
                    DatabaseReference pushRef = house.push();
                    String pushId = pushRef.getKey();
                    newHouse.setPushId(pushId);
                    pushRef.setValue(newHouse);
                    edtName.setText("");
                    edtPrice.setText("");
                    edtPhone.setText("");
                    edtAddress.setText("");
                    edtBed.setText("");
                    edtBath.setText("");
                    edtSize.setText("");
                    edtDescription.setText("");
                    Toast.makeText(AddHouseActivity.this, newHouse.getTitle()+ " fue agregada.", Toast.LENGTH_SHORT).show();
                    Intent restlist = new Intent(AddHouseActivity.this,Home.class);
                    startActivity(restlist);
                    //Snackbar.make(coordinatorLayout,"Nueva casa "+newHouse.getTitle()+" fue agregada",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void uploadImage() {
        if(saveUri!=null){
            final ProgressDialog mDialog = new ProgressDialog(this);
            mDialog.setMessage("Subiendo");
            mDialog.show();

            String imageName = UUID.randomUUID().toString();
            final StorageReference imageFolder = storageReference.child("images/"+imageName);
            imageFolder.putFile(saveUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDialog.dismiss();
                            Toast.makeText(AddHouseActivity.this,"Subido",Toast.LENGTH_SHORT).show();
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //Aqui esta los datos que se subiran a la BD
                                    newHouse = new House(edtName.getText().toString(),
                                            houseAddress=address.getName(),
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
                                            Common.currentuser.getPhone());

                                    Log.i("Si funciono","Si se subio: " + edtName.getText());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mDialog.dismiss();
                            Toast.makeText(AddHouseActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();
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


}
