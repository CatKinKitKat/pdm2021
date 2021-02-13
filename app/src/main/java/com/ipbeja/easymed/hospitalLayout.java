package com.ipbeja.easymed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * The type Hospital layout.
 */
public class hospitalLayout extends AppCompatActivity {

    /**
     * The Map.
     */
    GoogleMap map;

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

        MapFragment mapFragment = new MapFragment();
        this.map = mapFragment.getMap();
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
     * The type INNER Class Map fragment.
     */
    private class MapFragment extends Fragment implements OnMapReadyCallback {

        /**
         * The Map.
         */
        private GoogleMap map;

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
    }
}