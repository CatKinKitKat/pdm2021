package com.ipbeja.easymed;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
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
     * The Current latitude.
     */
    private float currentLatitude;

    /**
     * The Current longitude.
     */
    private float currentLongitude;

    /**
     * The Location manager.
     */
    private LocationManager locationManager;

    /**
     * The Medium distance.
     */
    private float mediumDistance;

    /**
     * The M closest marker.
     */
    private Marker closestMarker;
    /**
     * The Progress dialog.
     */
    private ProgressDialog progressDialog;

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

        SupportMapFragment mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView));
        if (mapFragment != null) {
            mapFragment.getMapAsync(this::loadMap);
        }

        Button directionsBtn = findViewById(R.id.goBtn);
        Button clearBtn = findViewById(R.id.clearBtn);
        Button centerBtn = findViewById(R.id.centerBtn);

        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            this.OnGPS();
        }

        this.getLocation();

        this.createMarkersFromDB();

        directionsBtn.setOnClickListener(v -> {
            this.getDirections();
            //this.focusOnClosestMarker();
        });

        clearBtn.setOnClickListener(v -> {
            this.clearMap();
        });

        centerBtn.setOnClickListener(v -> {
            this.centerMap();
        });
    }

    /**
     * On gps.
     */
    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes",
                (dialog, which) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        ).setNegativeButton("No",
                (dialog, which) -> dialog.cancel()
        );
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Gets location.
     */
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                this.currentLatitude = (float) locationGPS.getLatitude();
                this.currentLongitude = (float) locationGPS.getLongitude();

            }
        }
    }

    /**
     * Gets directions.
     */
    private void getDirections() {
    }

    /**
     * Create markers from db.
     */
    private void createMarkersFromDB() {

        int i = 0;
        this.mFirebaseFirestore.collection("hospitals").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Hospitals u = document.toObject(Hospitals.class);
                    u.setFireStoreID(document.getId());
                    this.createMarkerWithDistance(u.getLatitude(), u.getLongitude(), u.getName(), i);
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

    /**
     * Center map.
     */
    private void centerMap() {
        this.map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(38.015545f, -7.874886f)));
        this.map.animateCamera(CameraUpdateFactory.zoomTo(10));
    }

    /**
     * Create marker.
     *
     * @param lat  the lat
     * @param lng  the lng
     * @param name the name
     */
    private void createMarker(float lat, float lng, String name) {
        this.map.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(name));
    }

    /**
     * Create marker with distance.
     *
     * @param lat  the lat
     * @param lng  the lng
     * @param name the name
     * @param i    the
     */
    private void createMarkerWithDistance(float lat, float lng, String name, int i) {
        Marker currentMarker = this.map.addMarker(
                new MarkerOptions().position(new LatLng(lat, lng)).title(name)
        );
        float[] distance = new float[1];
        Location.distanceBetween(this.currentLatitude, this.currentLongitude, lat, lng, distance);

        if (i == 0) {
            this.mediumDistance = distance[0];
        } else if (mediumDistance > distance[0]) {
            this.mediumDistance = distance[0];
            this.closestMarker = currentMarker;
        }
    }

    /**
     * Focus on closest marker.
     */
    private void focusOnClosestMarker() {
        float lat = (float) this.closestMarker.getPosition().latitude;
        float lng = (float) this.closestMarker.getPosition().longitude;

        this.map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
        this.map.animateCamera(CameraUpdateFactory.zoomTo(25));
    }

    /**
     * Clear map.
     */
    private void clearMap() {
        this.map.clear();
    }
}