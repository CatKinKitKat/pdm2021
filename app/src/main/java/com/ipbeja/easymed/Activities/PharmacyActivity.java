package com.ipbeja.easymed.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.ipbeja.easymed.Adapters.PharmacyRecyclerViewAdapter;
import com.ipbeja.easymed.FireStore.Pharmacy;
import com.ipbeja.easymed.R;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Pharmacy activity.
 */
public class PharmacyActivity extends AppCompatActivity {

    /**
     * The Pharmacy data.
     */
    List<Pharmacy> pharmacyData;
    /**
     * The Recycler view.
     */
    RecyclerView recyclerView;
    /**
     * The Pharmacy recycler view adapter.
     */
    PharmacyRecyclerViewAdapter pharmacyRecyclerViewAdapter;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_layout);

        this.recyclerView = findViewById(R.id.recyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.pharmacyData = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("pharmacies").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Pharmacy u = document.toObject(Pharmacy.class);
                            u.setFireStoreId(document.getId());
                            this.pharmacyData.add(u);
                        }
                        this.pharmacyRecyclerViewAdapter = new PharmacyRecyclerViewAdapter(this.pharmacyData);
                        this.recyclerView.setAdapter(this.pharmacyRecyclerViewAdapter);
                    }
                }

        );

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.easy_med_order));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}