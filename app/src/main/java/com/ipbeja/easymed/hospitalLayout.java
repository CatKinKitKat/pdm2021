package com.ipbeja.easymed;

import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ipbeja.easymed.FireStore.Hospitals;

/**
 * The type Hospital layout.
 */
public class hospitalLayout extends AppCompatActivity {

    /**
     * The M firebase firestore.
     */
    FirebaseFirestore mFirebaseFirestore;
    /**
     * The Map.
     */
    GoogleMap map;
    /**
     * The Location manager.
     */
    LocationManager locationManager;
    /**
     * The Map fragment.
     */
    SupportMapFragment mapFragment;
    /**
     * The Directions btn.
     */
    Button directionsBtn;
    /**
     * The Task.
     */
    private Task<QuerySnapshot> task;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.hospitals));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.mFirebaseFirestore = FirebaseFirestore.getInstance();

        this.mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView));
        if (mapFragment != null) {
            mapFragment.getMapAsync(this::loadMap);
        }

        this.directionsBtn = findViewById(R.id.goBtn);

        this.directionsBtn.setOnClickListener(v -> {
            createMarkersFromDB();
        });
    }

    private void createMarkersFromDB() {

        this.mFirebaseFirestore.collection("hospitals").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Hospitals u = document.toObject(Hospitals.class);
                    u.setFireStoreID(document.getId());
                    this.createMarker(u.getLatitude(), u.getLongitude(), u.getName());
                }
            }
        });

    }

    /**
     * On options item selected boolean.
     *
     * @param item the item
     * @return the boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Load map.
     *
     * @param googleMap the google map
     */
    private void loadMap(GoogleMap googleMap) {
        this.map = googleMap;
        this.centerMap();
    }

    private void centerMap() {
        this.map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(38.015545f, -7.874886f)));
        this.map.animateCamera(CameraUpdateFactory.zoomTo(10));
    }

    private void createMarker(float lat, float lnt, String name) {
        this.map.addMarker(new MarkerOptions().position(new LatLng(lat, lnt)).title(name));
    }

    private void clearMap() {
        this.map.clear();
    }

}