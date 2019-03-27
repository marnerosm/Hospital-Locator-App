package com1032.cw2.mm01632.mm01632cw2com1032;

        import android.Manifest;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.database.Cursor;
        import android.graphics.Movie;
        import android.os.Parcelable;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.Toast;
        import com.google.android.gms.maps.model.LatLng;

        import java.io.Serializable;
        import java.util.ArrayList;
        import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements Serializable{
    CheckBox privateCheckBox;
    CheckBox hospitalCheckBox;
    CheckBox clinicCheckBox;
    EditText city;
    ListView listView;
    Button mGoToMap;
    Button mSearch;
    CustomAdapter adapter;
    HospitalDatabase myDb;
    ArrayList<Hospital> hospitals = new ArrayList<>();
    HashMap<String,LatLng> markers = new HashMap<>();
    private final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new CustomAdapter(this, R.layout.hospitals, hospitals);
        myDb = new HospitalDatabase(this);
        privateCheckBox = (CheckBox) findViewById(R.id.private_checkBox);
        hospitalCheckBox = (CheckBox) findViewById(R.id.hospital_checkBox);
        clinicCheckBox = (CheckBox) findViewById(R.id.clinic_checkBox);
        listView = (ListView) findViewById(R.id.hospital_List);
        city = (EditText)findViewById(R.id.editText);
        mGoToMap = (Button) findViewById(R.id.btn_go);
        mSearch = (Button) findViewById(R.id.search);
        listView.setAdapter(adapter);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        } else {
            startService(new Intent(this, LocationService.class));
        }
        goToMap();
        getHospitals();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            startService(new Intent(this, LocationService.class));
        }
    }

    /**
     * OnClickListener to go to Map Activity.
     * This button also creates two ArrayLists to save the titles and coordinates of the Markers.
     * Then these two ArrayLists are combined into a HashMap and send to the map activity
     * using intent.putExtra.
     * The if statements check which checkboxes are checked
     *
     */
    public void goToMap() {
        mGoToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                markers = new HashMap<>();


                String privateHospital = "";
                String clinic = "";
                String hospital = "";
                if (privateCheckBox.isChecked()) {
                    privateHospital = "Private";
                }
                if (clinicCheckBox.isChecked()) {
                    clinic = "Clinic";
                }
                if (hospitalCheckBox.isChecked()) {
                    hospital = "Hospital";
                }
                Cursor cursor = myDb.getHospitals(city.getText().toString() , clinic , hospital , privateHospital);
                while(cursor.moveToNext()){
                    LatLng coordinates = new LatLng(cursor.getDouble(1), cursor.getDouble(2));
                    String titles= new String(cursor.getString(0));
                    markers.put(titles,coordinates);
                }

                intent.putExtra("markers", markers);

                startActivity(intent);

            }
        });
    }

    /**
     * Once the Search button is pressed a new ArrayList is created containing the details of each
     * hospital which are shown in the List View.
     */
    public void getHospitals() {

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Toast.makeText(MainActivity.this, "Currently available in the following cities,(Woking,Guildford)",
                            Toast.LENGTH_SHORT).show();
                    hospitals.clear();
                    String privateHospital = "";
                    String clinic = "";
                    String hospital = "";
                    if (privateCheckBox.isChecked()) {
                        privateHospital = "Private";
                    }
                    if (clinicCheckBox.isChecked()) {
                        clinic = "Clinic";
                    }
                    if (hospitalCheckBox.isChecked()) {
                        hospital = "Hospital";
                    }
                    String theCity = city.getText().toString().trim();
                    Cursor cursor = myDb.getHospitals(theCity, clinic, hospital, privateHospital);
                    while (cursor.moveToNext()) {
                        Hospital hospital1 = new Hospital(cursor.getString(0), cursor.getString(3), cursor.getString(4));
                        hospitals.add(hospital1);

                    }

                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       outState.putParcelableArrayList("hospitals", ((ArrayList) hospitals));

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        hospitals = (ArrayList) savedInstanceState.getParcelableArrayList("hospitals");

    }
}

