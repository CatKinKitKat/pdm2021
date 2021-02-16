package com.ipbeja.easymed;

import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

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
    MapFragment mapFragment;
    /**
     * The Directions btn.
     */
    Button directionsBtn;

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

        this.mapFragment = new MapFragment();
        this.map = mapFragment.getMap();

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
     * TODO: NEEDS URGENT FIX! CANNOT UPDATE MAP OR CRASHES IF IT DOES.
     * <p>
     * The type INNER Class Map fragment.
     */
    private class MapFragment extends Fragment implements OnMapReadyCallback {

        /**
         * The Map.
         */
        private GoogleMap map;
        /**
         * The Current location.
         */
        private LatLng currentLocation;
        /**
         * The M closest marker.
         */
        private Marker mClosestMarker;
        /**
         * The Medium distance.
         */
        private float mediumDistance;

        /**
         * On create view view.
         *
         * @param inflater           the inflater
         * @param container          the container
         * @param savedInstanceState the saved instance state
         * @return the view
         */
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.mapView);
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }

            return super.onCreateView(inflater, container, savedInstanceState);
        }

        /**
         * On map ready.
         *
         * @param map the map
         */
        @Override
        public void onMapReady(GoogleMap map) {

            map = this.map;
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        }

        /**
         * Center map.
         */
        public void centerMap() {
            this.map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(38.015545f, -7.874886f)));
            this.map.animateCamera(CameraUpdateFactory.zoomTo(10));
        }

        /**
         * Add marker.
         *
         * @param lat   the lat
         * @param lng   the lng
         * @param title the title
         */
        public void addMarker(float lat, float lng, String title) {
            this.map.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(title));
        }

        /**
         * Clear map.
         */
        public void clearMap() {
            this.map.clear();
        }

        /**
         * Gets map.
         *
         * @return the map
         */
        public GoogleMap getMap() {
            return this.map;
        }

        /**
         * Sets map.
         *
         * @param map the map
         */
        public void setMap(GoogleMap map) {
            this.map = map;
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
                    mediumDistance = distance[0];
                } else if (mediumDistance > distance[0]) {
                    mediumDistance = distance[0];
                    mClosestMarker = currentMarker;
                }
            }

            // TODO FIX HARDCODED STRING
            Toast.makeText(hospitalLayout.this,
                    "HOSPITAL MAIS PROXIMO" + mClosestMarker.getTitle() + " " + mediumDistance,
                    Toast.LENGTH_LONG).show();
        }
    }
}