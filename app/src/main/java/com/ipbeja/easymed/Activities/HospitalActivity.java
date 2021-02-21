package com.ipbeja.easymed.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.ipbeja.easymed.FireStore.Hospitals;
import com.ipbeja.easymed.R;

/**
 * The type Hospital layout.
 */
public class HospitalActivity extends AppCompatActivity {

    /**
     * The M firebase firestore.
     */
    FirebaseFirestore firebaseFirestore;

    /**
     * The Map.
     */
    GoogleMap map;

    /**
     * The Ui settings.
     */
    UiSettings uiSettings;

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
     * Default to: Null Island 0.0N 0.0E
     */
    private float closestMarkerDistance = (float) 0.0;

    /**
     * The Closest marker latitude.
     * Default to: Null Island 0.0N 0.0E
     */
    private float closestMarkerLatitude = (float) 0.0;

    /**
     * The Closest marker longitude.
     * Default to: Null Island 0.0N 0.0E
     */
    private float closestMarkerLongitude = (float) 0.0;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.hospitals));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.firebaseFirestore = FirebaseFirestore.getInstance();

        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            this.OnGPS();
        }

        this.getLocation();

        SupportMapFragment mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView));
        if (mapFragment != null) {
            mapFragment.getMapAsync(this::loadMap);
        }
    }

    /**
     * On gps.
     */
    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.enable_gps)).setCancelable(false).setPositiveButton(getString(R.string.yes),
                (dialog, which) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        ).setNegativeButton(getString(R.string.no),
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
     * Create markers from db.
     */
    private void createMarkersFromDB() {

        this.firebaseFirestore.collection("hospitals").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Hospitals u = document.toObject(Hospitals.class);
                    u.setFireStoreID(document.getId());
                    this.createMarkerWithDistance(u.getLatitude(), u.getLongitude(), u.getName());
                }
            }
        });
    }

    /**
     * On create options menu boolean.
     *
     * @param menu the menu
     * @return the boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_hospitals, menu);
        return true;
    }

    /**
     * On options item selected boolean.
     *
     * @param item the item
     * @return the boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.nearest) {
            this.focusOnClosestMarker();
        } else if (id == R.id.gps) {
            this.centerMap();
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
        this.uiSettings = this.map.getUiSettings();

        this.uiSettings.setAllGesturesEnabled(true);
        this.uiSettings.setCompassEnabled(true);
        this.uiSettings.setIndoorLevelPickerEnabled(true);
        this.uiSettings.setMapToolbarEnabled(true);
        this.uiSettings.setMyLocationButtonEnabled(true);
        this.uiSettings.setRotateGesturesEnabled(true);
        this.uiSettings.setScrollGesturesEnabled(true);
        this.uiSettings.setScrollGesturesEnabledDuringRotateOrZoom(true);
        this.uiSettings.setTiltGesturesEnabled(true);
        this.uiSettings.setZoomControlsEnabled(true);
        this.uiSettings.setZoomGesturesEnabled(true);

        this.centerMap();
        this.createMarkersFromDB();
    }

    /**
     * Center map.
     */
    private void centerMap() {

        this.map.moveCamera(CameraUpdateFactory.newLatLng(
                new LatLng(
                        this.currentLatitude,
                        this.currentLongitude
                )
        ));
        this.map.animateCamera(CameraUpdateFactory.zoomTo((float) 17.5));
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
     */
    private void createMarkerWithDistance(float lat, float lng, String name) {
        this.createMarker(lat, lng, name);
        float[] distance = new float[1];
        Location.distanceBetween(
                this.currentLatitude,
                this.currentLongitude,
                lat, lng, distance
        );

        if (this.closestMarkerLatitude == 0.0
                && this.closestMarkerLongitude == 0.0
                && this.closestMarkerDistance == 0.0) {

            this.setClosest(lat, lng, distance[0]);
        } else if (this.closestMarkerDistance > distance[0]) {

            this.setClosest(lat, lng, distance[0]);
        }
    }

    /**
     * Sets closest.
     *
     * @param lat  the lat
     * @param lng  the lng
     * @param dist the dist
     */
    private void setClosest(float lat, float lng, float dist) {

        this.closestMarkerLatitude = lat;
        this.closestMarkerLongitude = lng;
        this.closestMarkerDistance = dist;
    }

    /**
     * Focus on closest marker.
     */
    private void focusOnClosestMarker() {
        float lat = this.closestMarkerLatitude;
        float lng = this.closestMarkerLongitude;

        this.map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
        this.map.animateCamera(CameraUpdateFactory.zoomTo((float) 17.5));
    }

    /**
     * Clear map.
     */
    private void clearMap() {
        this.map.clear();
    }
}