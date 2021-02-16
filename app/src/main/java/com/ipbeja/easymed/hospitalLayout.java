package com.ipbeja.easymed;

import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The type Hospital layout.
 */
public class hospitalLayout extends AppCompatActivity {

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
    private float mediumDistance;
    private Marker mClosestMarker;

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

        this.mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView));
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    loadMap(map);
                }
            });
        }

        this.directionsBtn = findViewById(R.id.goBtn);

        this.directionsBtn.setOnClickListener(v -> {

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
    protected void loadMap(GoogleMap googleMap) {
        this.map = googleMap;
        this.map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(38.015545f, -7.874886f)));
        this.map.animateCamera(CameraUpdateFactory.zoomTo(10));
    }

    /**
     * Create markers from hospitals.json; TODO: GET HOSPITALS FROM FIREBASE!
     *
     * @param json the json
     * @throws JSONException the json exception
     */
    public void createMarkersFromJson(String json) throws JSONException {

        // Create markers from hospitals.json and set the closest
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObj = jsonArray.getJSONObject(i);
            double lat = jsonObj.getJSONArray("latlng").getDouble(0);
            double lon = jsonObj.getJSONArray("latlng").getDouble(1);

            Marker currentMarker = map.addMarker(

                    new MarkerOptions()
                            .title(jsonObj.getString("name"))
                            .position(new LatLng(lat, lon)
                            )
            );

            float[] distance = new float[1];
            Location.distanceBetween(currentMarker.getPosition().latitude,
                    currentMarker.getPosition().longitude, lat, lon, distance);
            if (i == 0) {
                this.mediumDistance = distance[0];
            } else if (mediumDistance > distance[0]) {
                this.mediumDistance = distance[0];
                this.mClosestMarker = currentMarker;
            }
        }

        // TODO FIX HARDCODED STRING
        Toast.makeText(hospitalLayout.this,
                "HOSPITAL MAIS PROXIMO" + mClosestMarker.getTitle() + " " + mediumDistance,
                Toast.LENGTH_LONG).show();
    }

}